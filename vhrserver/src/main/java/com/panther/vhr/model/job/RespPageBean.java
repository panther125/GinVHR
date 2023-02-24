package com.panther.vhr.model.job;

import lombok.Data;

import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 22:42
 **/
@Data
public class RespPageBean {

    private Long total;
    private List<?> data;

}
