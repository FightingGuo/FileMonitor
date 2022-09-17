package com.ghc.ssm.controller;

import com.ghc.ssm.bean.Emp;
import com.ghc.ssm.bean.Message;
import com.ghc.ssm.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/4/29 - 11:32
 */
//处理员工CRUD请求
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

//      不用json数据格式返回 只适用与 浏览器与服务器端
//    @RequestMapping("/emps")
//    public String getEmps(@RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,Model model){
//        //分页查询，引入分页插件
//        //在查询之前，只需要调用 接收从前端传来的当前页码  并 设置每页显示的页数
//        PageHelper.startPage(pageNum,5);
//        List<Emp> allEmps = employeeService.getAllEmps();
//        //在查询之后使用pageInfo包装查询后的结果
//
//        //封装了详细的分页信息,配置分页导航数 navigatePages
//        PageInfo<Emp> pageInfo=new PageInfo(allEmps,5);
//        model.addAttribute("pageInfo",pageInfo);
//
//        return "emp_list";
//    }

    /**
     * 查询员工信息(分页查询)
     * @return
     * ResponseBody需要导入json包 才能转换为json对象
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Message getEmpWithJson(@RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum){
        //分页查询，引入分页插件
        //在查询之前，只需要调用 接收从前端传来的当前页码  并 设置每页显示的页数
        PageHelper.startPage(pageNum,5);
        List<Emp> allEmps = employeeService.getAllEmps();
        //在查询之后使用pageInfo包装查询后的结果

        //封装了详细的分页信息,配置分页导航数 navigatePages
        PageInfo<Emp> pageInfo=new PageInfo(allEmps,5);


        return Message.success().add("pageInfo",pageInfo);
    }

    /**
     * 当保存的信息是重要信息时 需要做到后端校验
     * 保存员工信息
     * 1.支持JSR303校验
     * 2.导入Hibernate-Validator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    //@Valid代表 emp对象里的数据信息需要校验  BindingResult 封装结果
    public Message saveEmp(@Valid Emp emp, BindingResult result){
        if(result.hasErrors()){
            //创建一个map集合封装错误信息
            Map<String,Object> map=new HashMap<>();
            //取出所有的错误信息
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error: errors) {
                //getField 拿到错误的属性 ，getDefaultMessage 拿到错误信息的提示
                map.put(error.getField(),error.getDefaultMessage());
            }
            //在模态框中显示错误的校验信息
            return Message.fail().add("errorField",map);
        }else {
            employeeService.insertEmp(emp);
            return Message.success();
        }

    }

    //检查用户名是否重复
    @ResponseBody
    @RequestMapping(value = "/emp",method = RequestMethod.GET)
    public Message isDistinct(@RequestParam String empName){
        //先判断用户名是否是合法的表达式
        //和前端相同的正则表达式校验
        String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";

        //先判断用户名是否符合规则 才有必要进行后端数据库校验
        //string里的matches()方法可检验empName是否符合regx
        if (!empName.matches(regx)){
            return Message.fail().add("vaMsg","用户名必须式6-16位数字和字母的组合或者2-5位中文");
        }

        //数据库用户名重复校验
        boolean distinct = employeeService.isDistinct(empName);
        //把状态码返回给客服端  100:可用  200:不可用
        if(distinct){
            return Message.success();
        }else {
            return Message.fail().add("vaMsg","用户名已存在");
        }

    }

    /**
     * 根据id查找用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    public Message getEmpById(@PathVariable("id") Integer id){
        Emp emp = employeeService.getEmpById(id);
        return Message.success().add("emp",emp);
    }

    /**
     * 用jquery直接发送put请求产生的问题
     * 请求体中有数据：
     * 但是Employee对象封装不上
     * 因为tomcat拿不到传过来的表单数据 所以empName email gender dId 都为null
     * 拼接的sql语句为 update tbl_emp where emp_id=?
     *
     * 原因：
     * Tomcat:1.将请求体中的数据封装成一个map。
     *        2.request.getParameter("empName")就会从这个map中取值。
     *        3.SpringMVC封装POJO数据时 会拿到每个POJO中的值，request.getParameter("参数名");
     *
     *  ajax发送put请求，请求体中的数据，request.getParameter("参数名")拿不到
     *  Tomcat一看时PUT不会封装请求体中的数据为map，只有POST请求才封装请求体为map
     *  org.apache.catalina.connector.Request;
     *
     *  要能支持jquery直接发送put请求
     *  1、配置上HttpPutFormContentFilter;
     *  2、作用:将请求体中的数据解析包装成一个map。
     *  3、request被重装，request.getParameter()被重写，就会从自己封装的map中取数据
     *
     * 更新员工数据
     * @param emp
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public Message updateEmp(Emp emp){
        employeeService.updateEmp(emp);
        return Message.success();
    }

    /**
     * 删除员工
     * 批量删除：1-2-3
     * 单个删除：1
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public Message deleteEmp(@PathVariable("ids") String ids){
        //批量删除 如果前端传来的数据包含 -
        if (ids.contains("-")){
            List<Integer> del_ids=new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id集合
            for (String string: str_ids) {
                del_ids.add(Integer.parseInt(string));

                employeeService.deleteBatch(del_ids);
            }
            return Message.success();
        }else {
            Integer id=Integer.parseInt(ids);
            employeeService.deleteById(id);
            return Message.success();
        }
    }

}
