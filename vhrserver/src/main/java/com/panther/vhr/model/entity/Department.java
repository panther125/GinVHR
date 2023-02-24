package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 23:08
 **/
@Data
@TableName("department")
@AllArgsConstructor
public class Department {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer parentId;

    private String depPath;

    private Boolean enabled;

    private Boolean isParent;

    public Department(){
    }

    public Department(String name){
        this.name = name;
    }

}
