package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.DepartmentMapper;
import com.panther.vhr.model.entity.Department;
import com.panther.vhr.serviec.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * @author Gin 琴酒
 * @data 2023/02/23 23:05
 **/
@Service
public class DepartMentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}
