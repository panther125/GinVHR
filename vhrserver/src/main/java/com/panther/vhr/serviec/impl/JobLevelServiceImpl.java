package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.JobLevelMapper;
import com.panther.vhr.model.entity.JobLevel;
import com.panther.vhr.serviec.JobLevelService;
import org.springframework.stereotype.Service;

/**
 * @author Gin 琴酒
 * @data 2023/02/23 22:36
 **/
@Service
public class JobLevelServiceImpl extends ServiceImpl<JobLevelMapper, JobLevel> implements JobLevelService {
}
