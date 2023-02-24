package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.RoleMapper;
import com.panther.vhr.model.entity.Role;
import com.panther.vhr.serviec.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 22:16
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService {
    @Override
    public List<Role> QueryRoleByHid(int hid) {
        return baseMapper.QueryRoleByHid(hid);
    }
}
