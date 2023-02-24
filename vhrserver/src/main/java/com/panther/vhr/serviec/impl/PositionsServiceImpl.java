package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.PositionMapper;
import com.panther.vhr.model.entity.Position;
import com.panther.vhr.serviec.PositionsService;
import org.springframework.stereotype.Service;

/**
 * @author Gin 琴酒
 * @data 2023/02/23 22:39
 **/
@Service
public class PositionsServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionsService {
}
