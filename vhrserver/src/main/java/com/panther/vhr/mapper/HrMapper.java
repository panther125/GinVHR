package com.panther.vhr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panther.vhr.model.entity.Hr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 22:23
 **/
@Mapper
public interface HrMapper extends BaseMapper<Hr> {

    @Select("select id,name,phone,telephone,address,username,userface,remark from hr where id = #{hid}")
    Hr currentHr(int hid);

    @Update("update hr set userface = #{fimg} where id = #{hid}")
    int updataImgurl(String fimg,int hid);

    @Update("update hr set password = #{pass} where id = #{hid}")
    int updataPasswordByhrid(String pass,int hid);
}
