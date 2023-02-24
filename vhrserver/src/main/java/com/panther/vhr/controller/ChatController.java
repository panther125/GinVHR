package com.panther.vhr.controller;

import com.panther.vhr.model.entity.Hr;
import com.panther.vhr.serviec.HrService;
import com.panther.vhr.utils.OnlineHrUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 11:35
 **/
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    HrService hrService;

    @GetMapping("/hrs")
    public List<Hr> getAllHrs() {
        // 获取在线的HR
        List hrs = OnlineHrUtils.getHrs();
        return hrService.listByIds(hrs);
    }
}
