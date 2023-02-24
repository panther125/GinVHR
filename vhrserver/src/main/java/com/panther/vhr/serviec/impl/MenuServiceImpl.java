package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.MenuMapper;
import com.panther.vhr.model.entity.Menu;
import com.panther.vhr.model.job.RespMenu;
import com.panther.vhr.serviec.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 14:32
 **/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<RespMenu> QueryMenu() {
        List<RespMenu> respMenus = baseMapper.QueryParent();
        int i = 2;
        for(RespMenu temp : respMenus){
            temp.setChildren(baseMapper.QueryAllChildMenu(i));
            i++;
        }
        return respMenus;
    }

    @Override
    public List<Integer> getMenuByMid(int id) {
        return baseMapper.getMenuByid(id);
    }
}
