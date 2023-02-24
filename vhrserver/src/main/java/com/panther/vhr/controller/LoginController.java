package com.panther.vhr.controller;

import com.panther.vhr.exception.SystemException;
import com.panther.vhr.model.DTO.LoginDTO;
import com.panther.vhr.model.job.RespHr;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.HrService;
import com.panther.vhr.utils.MD5;
import com.panther.vhr.utils.OnlineHrUtils;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 20:54
 **/
@RestController
@Slf4j
public class LoginController {

    @Resource
    private HrService hrService;

    @GetMapping("/login")
    public Result login() {
        return Result.error("尚未登录，请登录!");
    }

    @GetMapping("/verifyCode")
    public void VerifyCode(HttpServletRequest req, HttpServletResponse resp){
        try {
            // 获取运算的结果
            ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40);
            captcha.setLen(2);
            String code = captcha.text();

            req.getSession().setAttribute("Vcode",code);

            captcha.out(resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/doLogin")
    public Result Dologin(HttpServletRequest req, @RequestBody LoginDTO loginDTO){
        //log.info(loginDTO.getUsername());
        // 校验数据
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        String code = loginDTO.getCode();
        if(username == null || username.length() < 5 ){
            return Result.error("用户名小于5位");
        }
        if(password == null || password.length() < 5 ){
            return Result.error("密码小于5位");
        }
        if(code == null || code.length() == 0){
            return Result.error("请输入验证码");
        }
        //校验验证码
        if(!req.getSession().getAttribute("Vcode").equals(code)){
            return Result.error("验证码错误");
        }
        req.getSession().removeAttribute("Vcode");
        //校验密码
        String encrptyPwd = MD5.HEX(password);
        RespHr hr = hrService.queryHr(username);
        req.getSession().setAttribute("LoginHR",hr);
        //log.info(hr.toString());
        OnlineHrUtils.addHr(hr.getId());
        if(!encrptyPwd.equals(hr.getPassword())){
            return Result.error("密码错误");
        }
        return Result.ok("登录成功",hr);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) {

        try {
            // 清除session
            request.getSession().removeAttribute("LoginHR");
            // 重定向到登录页
            //request.getRequestDispatcher("/").forward(request,null);
            response.sendRedirect("/");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("系统异常");
        }
    }

}
