package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.NationMapper;
import com.panther.vhr.model.entity.Nation;
import com.panther.vhr.serviec.NationService;
import org.springframework.stereotype.Service;

/**
 * @author Gin 琴酒
 * @data 2023/02/23 22:29
 **/
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {
}
