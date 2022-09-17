package com.ghc.ssm.test;

import com.ghc.ssm.bean.Dept;
import com.ghc.ssm.bean.Emp;
import com.ghc.ssm.mapper.DeptMapper;
import com.ghc.ssm.mapper.EmpMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/4/28 - 20:19
 */
/**
 * 测试dao层
 * 推荐使用Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 * 1.导入SpringTest测试模块
 * 2.用@ContextConfiguration指定Spring的配置文件位置
 * 3.直接autoWired 要使用的组件即可
 * @RunWith:指定用哪个单元测试模块运行
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:Spring.xml"} )
public class MapperTest {

    @Autowired
    DeptMapper deptMapper;

    @Autowired
    EmpMapper empMapper;

    @Autowired
    SqlSession sqlSession;

    /**
     * 测试DepartmentMapper
     *
     */
    @Test
    public void test(){
//        创建SpringIoc容器
//        ApplicationContext context=new ClassPathXmlApplicationContext("Spring.xml");
//        从Ioc容器中获取mapper
//        DeptMapper Mapper = context.getBean(DeptMapper.class);

//        System.out.println(deptMapper);

        //插入几个部门
//        deptMapper.insertSelective(new Dept(null,"开发部"));
//        deptMapper.insertSelective(new Dept(null,"测试部"));
//        deptMapper.insertSelective(new Dept(null,"研发部"));

        //2.生成员工数据，测试员工插入
//        empMapper.insertSelective(new Emp(null,"张三","M","Zhang@qq.com",1));
        //3.批量插入员工
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        for (int i=0;i<1000;i++){
        String uname = UUID.randomUUID().toString().substring(0, 3)+i;
        mapper.insertSelective(new Emp(null,uname,"M",uname+"@qq.com",1));
        }
    }
}
