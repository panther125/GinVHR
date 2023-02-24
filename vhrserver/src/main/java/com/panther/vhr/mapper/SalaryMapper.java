package com.panther.vhr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panther.vhr.model.entity.Salary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {

    int updateByPrimaryKeySelective(Salary record);

    int updateByPrimaryKey(Salary record);

    List<Salary> getAllSalaries();
}
