package com.ghc.cloud.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghc.cloud.constant.FileConstant;
import com.ghc.cloud.entity.FileSystemUploadDTO;
import com.ghc.cloud.entity.FileSystemVerifyDTO;
import com.ghc.cloud.entity.Result;
import com.ghc.cloud.entity.UniqueFile;
import com.ghc.cloud.service.impl.FileServiceImpl;
import com.ghc.cloud.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @Date 2022/12/28 /18:54
 * @Author guohc
 * @Description  监听器
 */
@Slf4j
public class FileListener implements FileAlterationListener {

    private static FileServiceImpl fileService;

    public static FileServiceImpl getFileService() {
        return fileService;
    }

    public static void setFileService(FileServiceImpl fileService) {
        FileListener.fileService = fileService;
    }

    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {

    }

    @Override
    public void onDirectoryCreate(File file) {

    }

    @Override
    public void onDirectoryChange(File file) {

    }

    @Override
    public void onDirectoryDelete(File file) {

    }

    @Override
    public void onFileCreate(File file) {
        String absolutePath = file.getAbsolutePath();
        //取到监控目录中，被改变文件的路径和文件名
        String pathStr = absolutePath.substring(FileConstant.PATH.length() + 1);

        if (!absolutePath.contains("$")){
            log.info("可能要新增的文件：{}",absolutePath);
            try(FileInputStream fileInputStream = new FileInputStream(absolutePath)) {
                //获取文件的md5
                String md5Str = DigestUtils.md5Hex(fileInputStream);

                String replaceStr = pathStr.replaceAll("\\\\","_");

                String uniqueId = replaceStr + md5Str;

                LambdaQueryWrapper<UniqueFile> lsqWrapper = new LambdaQueryWrapper<>();
                lsqWrapper.eq(UniqueFile::getUniqueId,uniqueId);

                UniqueFile isUniqueFile = fileService.getOne(lsqWrapper);

                if (null == isUniqueFile){
                    log.info("数据库中没有唯一id为："+uniqueId+"的数据");

                    MyFileUtils.login();

                    FileSystemVerifyDTO.In inVerify = new FileSystemVerifyDTO.In();

                    inVerify.setExtract(Boolean.FALSE);
                    inVerify.setName(file.getName());
                    inVerify.setMd5(md5Str);

                    ArrayList<FileSystemVerifyDTO.In> list = new ArrayList<>();
                    list.add(inVerify);

                    FileSystemVerifyDTO.In.WrapList wrapList = new FileSystemVerifyDTO.In.WrapList();
                    wrapList.setParams(list);

                    Result verifyResult = MyFileUtils.verify(wrapList);

                    //调用filesystem 验证文件是否需要上传
                    if (verifyResult.getResult()){
                        FileSystemUploadDTO.In inUpload = new FileSystemUploadDTO.In();
                        inUpload.setExtract(Boolean.FALSE);
                        inUpload.setName(file.getName());
                        inUpload.setMd5(md5Str);

                        //上传文件
                        Result uploadResult = MyFileUtils.upload(file, inUpload);
                        //成功上传到filesystem的文件入库
                        if (uploadResult.getResult()){
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            Calendar cal = Calendar.getInstance();
                            String data = dateFormat.format(cal.getTime());
                            UniqueFile uniqueFile= new UniqueFile();
                            uniqueFile.setUniqueId(uniqueId);
                            uniqueFile.setCreateTime(data);

                            //上传过的文件把唯一id导入数据库
                            fileService.save(uniqueFile);
                            log.info("数据入库成功");
                        }else {
                            log.error("======上传文件至filesystem失败======：{}",uploadResult.getMessage());
                        }
                    }else {
                        log.error("======filesystem已验证过该文件=======：{}",verifyResult.getMessage());
                        return;
                    }

                }else {
                    log.error("========数据库中已上传过该文件========");
                    return;
                }
            } catch (Exception e) {
                log.error("错误信息：{}",e.getMessage());
            }
        }
    }

    @Override
    public void onFileChange(File file) {

    }

    @Override
    public void onFileDelete(File file) {
        String absolutePath = file.getAbsolutePath();
        log.info("监听目录中删除的文件：{}",absolutePath);
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {

    }
}
