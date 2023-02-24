package com.panther.vhr.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 22:59
 **/
@Data
public class Salary {

    private Integer id;

    private Integer basicSalary;

    private Integer bonus;

    private Integer lunchSalary;

    private Integer trafficSalary;

    private Integer allSalary;

    private Integer pensionBase;

    private Float pensionPer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private Integer medicalBase;

    private Float medicalPer;

    private Integer accumulationFundBase;

    private Float accumulationFundPer;

    private String name;

}
