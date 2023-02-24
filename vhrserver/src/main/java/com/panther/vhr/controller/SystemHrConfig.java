package com.panther.vhr.controller;

import com.panther.vhr.model.entity.Hr;
import com.panther.vhr.model.entity.Role;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.HrService;
import com.panther.vhr.serviec.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 11:25
 **/
@RestController
@RequestMapping("/system/hr")
public class SystemHrConfig {

    @Resource
    RoleService roleService;

    @Resource
    private HrService hrService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords) {
        return hrService.getAllHrs(keywords);
    }

    @PutMapping("/")
    public Result updateHr(@RequestBody Hr hr) {
        if (hrService.updateById(hr)) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @DeleteMapping("/{id}")
    public Result deleteHrById(@PathVariable Integer id) {
        if (hrService.removeById(id)) {
            return Result.ok("删除成功!");
        }
        return Result.error("删除失败!");
    }
}
