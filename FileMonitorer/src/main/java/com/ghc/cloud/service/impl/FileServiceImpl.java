package com.ghc.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghc.cloud.Mapper.FileMapper;
import com.ghc.cloud.entity.UniqueFile;
import com.ghc.cloud.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @Date 2022/12/28 /18:46
 * @Author guohc
 * @Description
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,UniqueFile> implements FileService {
}
