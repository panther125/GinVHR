package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 14:17
 **/
@Data
@TableName("menu")
public class Menu {
    @TableId
    private Integer id;

    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;

    private int keepAlive;
    private int requireAuth;
    private int parentId;
    private int enabled;

}
