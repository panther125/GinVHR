package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 22:56
 **/
@Data
public class Nation {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    public Nation(String name) {

        this.name = name;
    }
}
