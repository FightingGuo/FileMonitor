package com.ghc.test;

import com.ghc.cloud.utils.WordUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/11/30 /15:19
 * @Author guohc
 * @Description
 */
public class FileTest {


    @Test
    public void test1(){
            //模板文件地址
            String inputUrl = "C:\\Users\\guohc\\Desktop\\aaa.docx";
            //新生产的模板文件
            String outputUrl = "C:\\Users\\guohc\\Desktop\\bbb.docx";

            Map<String, String> testMap = new HashMap<>();
            testMap.put("total_summary", "根据集团2022年度公国计划和内审工作部署，集团公司拟于2022年12月份，派出审计组对投资公司及其项目公司开展内部审计。具体方案如下");
            testMap.put("audit_purpose", "通过对ccc公司进行包括战略规、划投资规划、前期规划、采购规划、工程管理、xxxxxx");
            testMap.put("audit_range_summary", "本次审计项目将按以下审计范围开展审计工作");


            testMap.put("audit_target_fragment", "(1)rr投资发展有限公司；\n(2)dd有限公司；\n(3)jj房地产开发有限公司；");
            testMap.put("operational_action_range", "业务流程全覆盖");
            testMap.put("audit_time", "www投资发展有限公司成立(2020年4月)至2022年10月");
            testMap.put("audit_content_summary", "根据被审计对象的业务发展现状xxxxx");
            testMap.put("audit_method_summary", "xxxxxxxxxxx");
            testMap.put("audit_team_share_work_summary", "xxxxxx");
            testMap.put("audit_time_arrange", "xxxxxx");
            testMap.put("audit_job_execute_steps", "xxxxxx");

            List<String[]> testList = new ArrayList<String[]>();
            testList.add(new String[]{"1","战略规划","xxxx","xxxx"});
            testList.add(new String[]{"2","组织架构","xxxx","xxxx"});
            testList.add(new String[]{"3","制度关系","3BB","xxx"});
            testList.add(new String[]{"4","投资发展","xxxx","xxx"});
            testList.add(new String[]{"5","投资发展","xxxx","xxx"});
            testList.add(new String[]{"6","投资发展","xxxx","xxx"});
            testList.add(new String[]{"7","投资发展","xxxx","xxx"});
            testList.add(new String[]{"8","投资发展","xxxx","xxx"});
            testList.add(new String[]{"9","投资发展","xxxx","xxx"});
            testList.add(new String[]{"10","投资发展","xxxx","xxx"});
            testList.add(new String[]{"11","投资发展","xxxx","xxx"});
            testList.add(new String[]{"12","投资发展","xxxx","xxx"});
            testList.add(new String[]{"13","投资发展","xxxx","xxx"});
            testList.add(new String[]{"14","投资发展","xxxx","xxx"});
            testList.add(new String[]{"15","投资发展","xxxx","xxx"});


            WordUtils.changWord(inputUrl, outputUrl, testMap, testList);
        }

        @Test
        public void test2(){
                //模板文件地址
                String inputUrl = "C:\\Users\\guohc\\Desktop\\lll.docx";
                //新生产的模板文件
                String outputUrl = "C:\\Users\\guohc\\Desktop\\nnn - guohc.docx";

                Map<String, String> testMap = new HashMap<>();
                testMap.put("audit_purpose", "审计目的ABC");
                testMap.put("audit_target_fragment", "(1)对象dssasd1；\n(2)对象sdawqwe2；\n(3)对象diqoiw3；");
                testMap.put("audit_time", "2022.12.05");

                testMap.put("audit_group_leader", "组长c");
                testMap.put("audit_deputy_group_leader", "副组长d");
                testMap.put("project_leader", "负责人a");
                testMap.put("audit_team_members", "审计组成员q");

                List<String[]> testList = new ArrayList<String[]>();
                testList.add(new String[]{"1","信息化建设","信息系统建设与使用、信息系统维护","a)测试信息系统的运行与设置是否符合信息安全性与保密性的要求；\nb)检查信息系统的验收、上线及变更情况；\nc)检查信息系统的完整性和对业务的支撑程度；\nd)……"});
                testList.add(new String[]{"2","组织架构","xxx","xxx"});
                testList.add(new String[]{"3","制度关系","xxx","xxx"});
                testList.add(new String[]{"4","投资发展","xxx","xxx"});
                testList.add(new String[]{"5","投资发展","xxx","xxx"});
                testList.add(new String[]{"6","投资发展","xxx","xxx"});
                testList.add(new String[]{"7","投资发展","xxx","xxx"});
                testList.add(new String[]{"8","投资发展","xxx","xxx"});
                testList.add(new String[]{"9","投资发展","xxx","xxx"});
                testList.add(new String[]{"10","投资发展","xxx","xxx"});
                testList.add(new String[]{"11","投资发展","xxx","xxx"});
                testList.add(new String[]{"12","投资发展","xxx","xxx"});
                testList.add(new String[]{"13","投资发展","xxx","xxx"});
                testList.add(new String[]{"14","投资发展","xxx","xxx"});
                testList.add(new String[]{"15","投资发展","xxx","xxx"});


                WordUtils.changWord(inputUrl, outputUrl, testMap, testList);
        }


        @Test
        public void test3(){
            WordUtils wordUtils = new WordUtils();

                //模板文件地址
                String inputUrl1 = "C:\\Users\\guohc\\Desktop\\hhh.docx";


                String inputUrl2= "C:\\Users\\guohc\\Desktop\\fff.docx";

                String[] input = {inputUrl1,inputUrl2};

                //新生产的模板文件
                String outputUrl = "C:\\Users\\guohc\\Desktop\\合并模板.docx";


            WordUtils.mergerWord(input,outputUrl);
        }
}
