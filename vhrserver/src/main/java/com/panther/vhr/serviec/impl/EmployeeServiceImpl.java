package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.EmployeeMapper;
import com.panther.vhr.model.DTO.EmployeeDTO;
import com.panther.vhr.model.entity.Employee;
import com.panther.vhr.model.job.RespEmployee;
import com.panther.vhr.model.job.RespPageBean;
import com.panther.vhr.serviec.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 23:15
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public List<RespEmployee> getEmployeeByPage(Integer page, Integer size,EmployeeDTO employee, Date[] beginDateScope) {
        return baseMapper.getEmployeeByPage(page, size,employee,beginDateScope);
    }

    @Override
    public Long getTotal(EmployeeDTO employee,Date[] beginDateScope) {
        return baseMapper.getTotal(employee,beginDateScope);
    }

    @Override
    public Integer maxWorkID() {
        return baseMapper.maxWorkID();
    }

    @Override
    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<RespEmployee> list = baseMapper.getEmployeeByPageWithSalary(page, size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(baseMapper.getTotal(null, null));
        return respPageBean;
    }

    @Override
    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {
        return baseMapper.updateEmployeeSalaryById(eid, sid);
    }

    @Override
    public int updateEmp(RespEmployee employee) {
        return baseMapper.updateByPrimaryKeySelective(employee);
    }
}
