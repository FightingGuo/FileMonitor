package com.ghc.ssm.controller;

import com.ghc.ssm.bean.Dept;
import com.ghc.ssm.bean.Message;
import com.ghc.ssm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/4/29 - 11:34
 */
//处理部门CRUD请求
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService deptService;

    /**
     * 返回所有的部门信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/depts")
    public Message getDeptWithJson(){
        //查出所有部门的信息
        List<Dept> deptList = deptService.getDept();


        return Message.success().add("deptList",deptList);
    }


}
