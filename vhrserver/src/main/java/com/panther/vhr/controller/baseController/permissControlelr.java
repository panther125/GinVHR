package com.panther.vhr.controller.baseController;

import com.panther.vhr.model.entity.Menu;
import com.panther.vhr.model.entity.Role;
import com.panther.vhr.serviec.MenuService;
import com.panther.vhr.serviec.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 17:33
 **/
@RestController
@RequestMapping("/system/basic/permiss")
public class permissControlelr {

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.list();
    }
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.list();
    }

    @GetMapping("/mids/{id}")
    public List<Integer> getMenuByMid(@PathVariable("id") int id){
        return menuService.getMenuByMid(id);
    }
}
