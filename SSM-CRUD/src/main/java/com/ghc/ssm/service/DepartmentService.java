package com.ghc.ssm.service;

import com.ghc.ssm.bean.Dept;
import com.ghc.ssm.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/5/6 - 10:27
 */
@Service
public class DepartmentService {
    @Autowired
    DeptMapper deptMapper;

    public List<Dept> getDept() {
        return deptMapper.selectByExample(null);
    }
}
