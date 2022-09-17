package com.ghc.ssm.service;

import com.ghc.ssm.bean.Emp;
import com.ghc.ssm.bean.EmpExample;
import com.ghc.ssm.bean.Message;
import com.ghc.ssm.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/4/29 - 11:41
 */
@Service
public class EmployeeService {

    @Autowired
    EmpMapper empMapper;

    /**
     * 查询所有员工信息
     * @return
     */
    public List<Emp> getAllEmps() {
        return empMapper.selectByExampleWithDept(null);
    }


    public void insertEmp(Emp emp) {
        empMapper.insertSelective(emp);
    }

    /**
     * 检查用户名是否可用
     * @param empName
     * @return //如果等于0 返回true 表示可用 如果不等于0 说明有数据 返回false 不可用
     */
    public boolean isDistinct(String empName){
        //创建example对象 添加查询条件
        EmpExample example=new EmpExample();
        EmpExample.Criteria criteria = example.createCriteria();
        //加入条件
        criteria.andEmpNameEqualTo(empName);
        //如果有和前端传进来的姓名一样的个数+1
        int i = empMapper.countByExample(example);
        return i==0;
    }


    public void updateEmp(Emp emp) {
        empMapper.updateByPrimaryKeySelective(emp);
    }

    /**
     * 通过员工id查找员工
     */
    public Emp getEmpById(Integer id) {
        return empMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过员工id删除员工信息
     * @param id
     */
    public void deleteById(Integer id) {
         empMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids) {
        EmpExample example=new EmpExample();
        EmpExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id id(1,2,3)
        criteria.andEmpIdIn(ids);
        empMapper.deleteByExample(example);
    }
}
