package com.panther.vhr.controller;

import com.panther.vhr.model.entity.Hr;
import com.panther.vhr.model.job.ChatMsg;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 11:48
 **/
@Controller
public class WSController {

    @Resource
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(HttpSession session, ChatMsg chatMsg) {
        Hr hr = (Hr) session.getAttribute("LoginHR");
        chatMsg.setFrom(hr.getUsername());
        chatMsg.setFromNickname(hr.getName());
        chatMsg.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }

}
