package com.panther.vhr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panther.vhr.model.DTO.EmployeeDTO;
import com.panther.vhr.model.entity.Employee;
import com.panther.vhr.model.job.RespEmployee;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 23:15
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<RespEmployee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("emp") EmployeeDTO employeeDTO,@Param("beginDateScope") Date[] beginDateScope );

    Long getTotal(@Param("emp") EmployeeDTO employeeDTO,@Param("beginDateScope") Date[] beginDateScope);

    Integer maxWorkID();

    List<RespEmployee> getEmployeeByPageWithSalary(@Param("page") Integer page, @Param("size") Integer size);

    Integer updateEmployeeSalaryById(@Param("eid")Integer eid,@Param("sid") Integer sid);

    Integer updateByPrimaryKeySelective(RespEmployee employee);
}
