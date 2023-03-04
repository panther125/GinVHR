package com.panther.vhr.controller;

import com.panther.vhr.model.job.RespMenu;
import com.panther.vhr.serviec.MenuService;
import com.panther.vhr.utils.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 14:14
 **/
@RestController
@RequestMapping("/system/config")
public class SystemController {

    @Resource
    private MenuService menuService;

//    @Resource
//    private RedisTemplate<String,List<RespMenu>> redisTemplate;

    @GetMapping("/menu")
    public List<RespMenu> getMenu(){
//        List<RespMenu> respMenus = null;
//
//        // 判断缓存是否存在值
//        respMenus = redisTemplate.opsForValue().get("menus");
//        if(respMenus != null){
//            return respMenus;
//        }
//
//        // 保存在redis中
//        respMenus = menuService.QueryMenu();
//        redisTemplate.opsForValue().set("menus",respMenus);

        return menuService.QueryMenu();
    }
}
