package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 23:08
 **/
@Data
public class Politicsstatus {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    public Politicsstatus(String name){
        this.name = name;
    }

}
