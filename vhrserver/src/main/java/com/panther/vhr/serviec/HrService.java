package com.panther.vhr.serviec;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panther.vhr.model.entity.Hr;
import com.panther.vhr.model.job.RespHr;

import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 22:22
 **/
public interface HrService extends IService<Hr> {

    RespHr queryHr(String name);

    Hr queryCurrentHr(int hid);

    int updataImgurl(String furl,int hid);

    int updateHrByUserName(Hr hr);

    boolean updateHrPasswd(String oldpass, String pass, Integer hrid);

    List<Hr> getAllHrs(String keywords);
}
