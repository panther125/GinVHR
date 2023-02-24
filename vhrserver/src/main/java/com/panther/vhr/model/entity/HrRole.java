package com.panther.vhr.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 23:22
 **/
@TableName("hr_role")
@Data
public class HrRole {

    private Integer id;
    private int hrid;
    private int rid;

}
