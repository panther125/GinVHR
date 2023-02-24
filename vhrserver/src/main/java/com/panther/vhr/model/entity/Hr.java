package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @author Gin 琴酒
 * @data 2023/02/21 21:52
 **/
@Data
@TableName("hr")
public class Hr {

    @TableId
    private Integer id;

    private String name;

    private String phone;

    private String telephone;

    private String address;

    private int enabled;

    private String username;

    private String password;

    private String userface;

    private String remark;

}