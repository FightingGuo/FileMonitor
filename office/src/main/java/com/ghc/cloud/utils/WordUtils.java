package com.ghc.cloud.utils;

import java.io.*;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
/**
 * @Date 2022/11/30 /15:07
 * @Author guohc
 * @Description
 */

/**
 * 问题1：有的需要分段的内容和标题的缩进不同不能统一使用首行缩进   解决：做一个约定在设置占位符时传入约定单词或字符区分分段内容还是标题  扫描到占位符时进行判断标题就直接首行缩进  分段内容使用段落对象的setIndentationLeft方法调整距离
 * 问题2：段落包含文本和占位符时，直接替换会把原有文本覆盖        解决：在执行替换方法之前把占位符前后的文本先保存下来 在插入模板之前追加一下
 * 问题3：小标题含有1.   2.   替换占位符时重复追加字符串	    解决：添加一个控制替换字符串的变量  需要在循环到最后一个run对象的时候再做字符串的替换
 * 问题4：表格内容换行(卡了一天    试过先插入数据再扫一边表格内容有换行符在做换行,发现往表格插入数据后poi会解析成xml文件里面内容不知道咋取。然后试了在插入数据的时候处理换行 出现重复插入数据)
 *       解决：换行的逻辑都一样，之前卡在处理传入的数据  开始取到整个数据集再取传到单元格的内容就要多包一层循环导致重复插入数据   后来发现list<String[]>可以直接看成一个二位数组通过(i)[j]直接取到数组类型集合的里面的字符串内容
 * 问题5：表格中不同列的样式  1 2 列居中  3 4 列 左对齐
 */
@Slf4j
public class WordUtils {

    /**
     * 合并多个word文档
     * @param outputUrl 合并后的文档输出路径
     * @param inputUrl  需要合并的文档地址数组
     * @return
     */
    public static boolean mergerWord(String[] inputUrl,String outputUrl){
        //默认文档合并成功
        boolean mergeFlag = true;
        File newFile = new File(outputUrl);
        List<File> srcfile = new ArrayList<>();

        //遍历数组取出需要合并的文档地址
        for (String file:inputUrl){
            File mergeFile = new File(file);
            srcfile.add(mergeFile);
            mergeMethod(newFile,srcfile);
        }
        return mergeFlag;
    }

    public static boolean mergerWord(File[] files,String outputUrl){
        //默认文档合并成功
        boolean mergeFlag = true;
        File newFile = new File(outputUrl);
        List<File> srcfile = new ArrayList<>();

        //遍历数组取出需要合并的文档地址
        for (File file:files){

            srcfile.add(file);
            mergeMethod(newFile,srcfile);
        }
        return mergeFlag;
    }


    /**
     * 合并方法实现
     */
    public static void mergeMethod(File newFile,List<File> srcfile){

        try {
            OutputStream dest = new FileOutputStream(newFile);
            ArrayList<XWPFDocument> documentList = new ArrayList<>();
            XWPFDocument doc = null;
            for (int i = 0; i < srcfile.size(); i++) {
                try (FileInputStream in = new FileInputStream(srcfile.get(i).getPath())){
                    OPCPackage open = OPCPackage.open(in);
                    XWPFDocument document = new XWPFDocument(open);
                    documentList.add(document);
                } catch (IOException e) {
                    log.error("错误信息：{}",e.getMessage());
                }
            }
            for (int i = 0; i < documentList.size(); i++) {
                doc = documentList.get(0);
                if(i == 0){//首页直接分页，不再插入首页文档内容
                    documentList.get(i).createParagraph().createRun().addBreak(BreakType.PAGE);
//                    appendBody(doc,documentList.get(i));
                }else if(i == documentList.size()-1){//尾页不再分页，直接插入最后文档内容
                    appendBody(doc,documentList.get(i));
                }else{
                    documentList.get(i).createParagraph().createRun().addBreak(BreakType.PAGE);
                    appendBody(doc,documentList.get(i));
                }
            }
            doc.write(dest);
            dest.close();

            //Runtime.getRuntime().exec("cmd /c start winword C:/Users/gouwe/合并.docx");//直接调用cmd打开合成文档
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void appendBody(XWPFDocument src, XWPFDocument append) throws Exception {
        CTBody src1Body = src.getDocument().getBody();
        CTBody src2Body = append.getDocument().getBody();

        //处理图片
        List<XWPFPictureData> allPictures = append.getAllPictures();
        // 记录图片合并前及合并后的ID
        Map<String,String> map = new HashMap<>();
        for (XWPFPictureData picture : allPictures) {
            String before = append.getRelationId(picture);
            //将原文档中的图片加入到目标文档中
            String after = src.addPictureData(picture.getData(), Document.PICTURE_TYPE_PNG);
            map.put(before, after);
        }
        appendBody(src1Body, src2Body,map);
    }

    private static void appendBody(CTBody src, CTBody append, Map<String,String> map) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);

        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        if (map != null && !map.isEmpty()) {
            //对xml字符串中图片ID进行替换
            for (Map.Entry<String, String> set : map.entrySet()) {
                addPart = addPart.replace(set.getKey(), set.getValue());
            }
        }
        //将两个文档的xml内容进行拼接
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
        src.set(makeBody);
    }



    /**
     * 根据模板生成新word文档
     * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
     * @param inputUrl  模板存放地址
     * @param outputUrl 新文档存放地址
     * @param textMap   需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     * @return          成功返回true,失败返回false
     */
    public static boolean changWord(String inputUrl, String outputUrl,
                                    Map<String, String> textMap, List<String[]> tableList) {

        //模板转换默认成功
        boolean changeFlag = true;
        try {
            //获取docx解析对象
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //解析替换文本段落对象
            WordUtils.changeText(document, textMap);
            //解析替换表格对象
            WordUtils.changeTable(document, textMap, tableList);

            //生成新的word
            File file = new File(outputUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            changeFlag = false;
        }

        return changeFlag;

    }

    /**
     * 替换段落文本
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, String> textMap){
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            //每个段落换行

            //判断此段落是否需要进行替换  (替换会替换整个段落，所以先把占位符之前和之后的字符串保存起来塞进去之前作拼接)
            String text = paragraph.getText();

            if(checkText(text)){

                List<XWPFRun> runs = paragraph.getRuns();

                String replaceValue = "";

//                对需要分段的内容不进行首行缩进，在设置占位符时传入fragment 做区分
                if (text.contains("_fragment")){
//                  整段缩进(右移)指定应为从左到右段，该段的内容的左边的缘和这一段文字左边的距和右边文本边距和左段权中的那段文本的右边缘之间的缩进
                    paragraph.setIndentationLeft(1000);
                    //设置段落左对齐
                    paragraph.setAlignment(ParagraphAlignment.LEFT);
                }else {
                    //对需要替换的内容进行缩进
                    paragraph.setIndentationFirstLine(400);
                }

                //控制替换字符串的变量  需要在循环到最后一个run对象的时候再做字符串的替换 否则会重复追加字符串
                int count=0;

                for (XWPFRun run : runs) {
                    count++;
                    //取出替换模板的内容
                    String value = changeValue(run.toString(), textMap);

                    //分段显示的情况(在需要换行的地方传入\r\n)
                    String[] values = value.split("\n");
                    if(values.length > 1) {
                        run.setText(values[0],0);
                        for (int i = 1; i < values.length; i++) {
                            //存在分段则新建一个run
                            XWPFRun newrun = paragraph.insertNewRun(i);
                            //copy样式
                            newrun.getCTR().setRPr(run.getCTR().getRPr());
                            //换行
                            newrun.addBreak();
                            //文本追加进模板
                            newrun.setText(values[i]);
                        }
                        break;
                    }else {
                        //截取位符$之前的string
                        String start = text.substring(text.indexOf(".")+1,text.indexOf("$"));
                        //截取占位符之后的字符串
                        String end = text.substring(text.indexOf("}")+1);

                        if (count == runs.size()){
                            replaceValue = start + value + end;
                            run.setText(replaceValue, 0);
                        }
                    }
                }
            }
        }
    }




    /**
     * 匹配传入信息集合与模板
     * @param value 模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(String value, Map<String, String> textMap){
        Set<Map.Entry<String, String>> textSets = textMap.entrySet();
        for (Map.Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${"+textSet.getKey()+"}";
            if(value.indexOf(key)!= -1){
                //匹配到占位符后 把传入内容替换占位符
                value = textSet.getValue();
                break;
            }
        }
        //模板未匹配到区域替换为空
        if(checkText(value)){
            value = "";
        }
        return value;
    }


    /**
     * 替换表格对象方法
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     */
    public static void changeTable(XWPFDocument document, Map<String, String> textMap,
                                   List<String[]> tableList){
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            //只处理行数大于等于2的表格，且不循环表头
            XWPFTable table = tables.get(i);
            if(table.getRows().size()>1){
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if(checkText(table.getText())){
                    List<XWPFTableRow> rows = table.getRows();
                    //遍历表格,并替换模板
                    eachTable(rows, textMap);
                }else{
                    //数据插入表格 并对表格内需要换行的内容换行
                    insertTable(table, tableList,document);
                }
            }
        }
    }


    /**
     * 遍历表格
     * @param rows 表格行对象
     * @param textMap 需要替换的信息集合
     */
    public static void eachTable(List<XWPFTableRow> rows ,Map<String, String> textMap){
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if(checkText(cell.getText())){
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            run.setText(changeValue(run.toString(), textMap),0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 为表格插入数据，行数不够添加新行  扫描内容如果有\r\n执行换行
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    public static void insertTable(XWPFTable table, List<String[]> tableList, XWPFDocument document) {
        XWPFTable afterTable = table;

        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 1; i < tableList.size(); i++) {
            XWPFTableRow row = afterTable.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = afterTable.getRows();
        for (int i = 1; i < rows.size(); i++) {
            //拿到表格行
            XWPFTableRow newRow = afterTable.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                //拿到表格列(单元格)
                XWPFTableCell cell = cells.get(j);

                //创建段落
                XWPFParagraph paragraph = null;

                //拿到要传入单元格的数据
                String item = tableList.get(i - 1)[j];
                if (item.contains("\n")) {
                    //为每一个单元格创建段落
                    paragraph = document.createParagraph();
                    //需要分段 创建文本对象集合
                    XWPFRun run = paragraph.createRun();
                    String[] values = item.split("\n");
                    //大于1字符串切割成数组了 说明需要分段
                    if (values.length > 1) {
                        run.setText(values[0], 0);
                        for (int k = 1; k < values.length; k++) {
                            //存在分段则新建一个run
                            XWPFRun newrun = paragraph.insertNewRun(k);
                            //copy样式
                            newrun.getCTR().setRPr(run.getCTR().getRPr());
                            //换行
                            newrun.addBreak();
                            newrun.setText(values[k]);
                        }
                    }
                } else {
                    if (j < 2) {  //看模板前两列居中  后两列左对齐
                        // 设置水平居中
                        CTTc cttc = cell.getCTTc();
                        CTTcPr ctPr = cttc.addNewTcPr();
                        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);

                        //不需要换行直接加入单元格(单元格里的文本 默认是左对齐)
                        cell.setText(tableList.get(i - 1)[j]);
                    }else {
                        //不用居中的单元格直接插入
                        cell.setText(tableList.get(i - 1)[j]);
                    }
                }
                if (null != paragraph) {  //如果有内容需要换行 就把换行过的内容加入单元格
                    cell.setParagraph(paragraph);
                }
            }
        }
    }


    /**
     * 抽出换行的方法  但是直接调方法会报并发改问题ConcurrentModifyException
     * @param value     需要换行的内容
     * @param paragraph 当前段落
     * @param run
     */
    private static void setWrap(String value, XWPFParagraph paragraph, XWPFRun run) {

        String[] values = value.split("\n");
        //大于1字符串切割成数组了 说明需要分段
        if(values.length > 1) {
            run.setText(values[0],0);
            for (int i = 1; i < values.length; i++) {
                //存在分段则新建一个run
                XWPFRun newrun = paragraph.insertNewRun(i);
                //copy样式
                newrun.getCTR().setRPr(run.getCTR().getRPr());
                //换行
                newrun.addBreak();

                newrun.setText(values[i]);
            }
        }else {
            //小于等于1 不用分段直接把value丢进去
            run.setText(value, 0);
        }
    }


    /**
     * 匹配单元格内容\n 替换为换行
     * @param cell
     */
    public static void addBreakInCell(XWPFTableCell cell) {
//        判断单元格的内容是否包含换行符
        if(cell.getText() != null && cell.getText().contains("\n")) {
//            包含创建段落
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                //设置段落左对齐
//                paragraph.setAlignment(ParagraphAlignment.LEFT);
                //创建run处理文本内容
                for (XWPFRun run : paragraph.getRuns()) {

                    if(run.getText(0)!= null && run.getText(0).contains("\n")) {
                        String[] lines = run.getText(0).split("\n");
                        if(lines.length > 0) {
                            //把分割后的数组中的第一个元素插入表格
                            run.setText(lines[0], 0);
                            //如果包含\r\n lines>1 加入空格
                            for(int i=1;i<lines.length;i++){
                                // add break and insert new text
                                run.addBreak();
                                run.setText(lines[i]);
                            }
                        }
                    }
                }
            }
        }
    }




    /**
     * 判断文本中时候包含$
     * @param text 文本
     * @return 包含返回true,不包含返回false
     */
    public static boolean checkText(String text){
        boolean check  =  false;
        if(text.indexOf("$")!= -1){
            check = true;
        }
        return check;

    }


    /**
     * 操作文本方法
     * @param run 操作文本的最小对象
     * @param changeLine  是否换行
     * @param content  文本内容
     * @param isUnderline  是否添加下划线
     * @param color 设置颜色传16进制
     */
    public void setForm(XWPFRun run, int changeLine, String content, Boolean isUnderline, String color) {
        // 是否添加下划线
        if (isUnderline) {
            run.setUnderline(UnderlinePatterns.WORDS);
        }
        run.setText(content);// 设置run的文本
        // 是否换行
        if (changeLine != 0) {
            // 换几行
            for (int i = 0; i < changeLine; i++) {
                run.addBreak();
            }
        }
        run.setColor(color);

        run.setTextPosition(5);
        //字体大小
        run.setFontSize(10);
        run.setFontFamily("仿宋");
    }

}
