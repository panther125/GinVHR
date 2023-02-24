package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role")
public class Role {

    private Integer id;

    private String name;

    @TableField("nameZh")
    private String nameZh;
}