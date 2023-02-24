package com.panther.vhr.model.job;

import com.panther.vhr.model.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 22:55
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RespEmployee extends Employee {

    private Nation nation;
    private Politicsstatus politicsstatus;
    private Department department;
    private JobLevel jobLevel;
    private Position position;
    private Salary salary;
}
