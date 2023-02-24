package com.panther.vhr.controller;

import com.panther.vhr.model.entity.Hr;
import com.panther.vhr.model.entity.Role;
import com.panther.vhr.model.job.RespHr;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.HrService;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.panther.vhr.serviec.RoleService;
import com.panther.vhr.utils.ThreadData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 16:07
 **/
@RestController
@Slf4j
@RequestMapping("/hr")
public class HrController {

    @Value("${file.path}")
    private String basePath;

    @Resource
    private HrService hrService;

    @Resource
    private RoleService roleService;

    @GetMapping("/info")
    public RespHr getCurrentHr(){
        RespHr respHr = new RespHr();
        Hr currentHrid = hrService.queryCurrentHr(((Hr)ThreadData.getThreadData("currentHrid")).getId());
        BeanUtils.copyProperties(currentHrid,respHr);
        respHr.setRoles(roleService.QueryRoleByHid(currentHrid.getId()));
        //log.info(respHr.toString());
        return respHr;
    }
    @PutMapping("/info")
    public Result updateHr(@RequestBody Hr hr) {
        if (hrService.updateHrByUserName(hr) > 0) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }
    @PostMapping("/userface")
    public Result updateHrUserface(@RequestParam("file") MultipartFile file) {
        try {
            String fname = UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
            file.transferTo(new File(basePath + fname));
            if(hrService.updataImgurl("/img/"+fname,(int)ThreadData.getThreadData("currentHrid")) > 0){
                return Result.ok("更新成功");
            }else{
                return Result.error("更新失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("数据错误，请不要上传大文件!");
        }
    }

    @PutMapping("/pass")
    public Result updateHrPasswd(@RequestBody Map<String, Object> info) {
        String oldpass = (String) info.get("oldpass");
        String pass = (String) info.get("pass");
        Integer hrid = (Integer) info.get("hrid");
        if (hrService.updateHrPasswd(oldpass, pass, hrid)) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }
}
