package com.panther.vhr.serviec;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panther.vhr.model.entity.Menu;
import com.panther.vhr.model.entity.Role;
import com.panther.vhr.model.job.RespMenu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<RespMenu> QueryMenu();

    List<Integer> getMenuByMid(int id);
}
