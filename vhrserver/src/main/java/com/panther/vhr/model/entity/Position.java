package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 22:58
 **/
@Data
public class Position {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createDate;

    private Boolean enabled;

    public Position(String name){
        this.name = name;
    }


}
