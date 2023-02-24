package com.panther.vhr.serviec;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panther.vhr.model.DTO.EmployeeDTO;
import com.panther.vhr.model.entity.Employee;
import com.panther.vhr.model.job.RespEmployee;
import com.panther.vhr.model.job.RespPageBean;

import java.util.Date;
import java.util.List;

public interface EmployeeService extends IService<Employee> {

    List<RespEmployee> getEmployeeByPage(Integer page, Integer size,EmployeeDTO employee, Date[] beginDateScope);

    Long getTotal(EmployeeDTO employee,Date[] beginDateScope);

    Integer maxWorkID();

    RespPageBean getEmployeeByPageWithSalary(Integer page,Integer size);

    Integer updateEmployeeSalaryById(Integer eid, Integer sid);

    int updateEmp(RespEmployee employee);

}
