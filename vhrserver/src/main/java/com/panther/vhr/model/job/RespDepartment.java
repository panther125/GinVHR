package com.panther.vhr.model.job;

import com.panther.vhr.model.entity.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 23:10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RespDepartment extends Department {

    private List<Department> children = new ArrayList<>();
    private Integer result;

}
