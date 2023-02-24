package com.panther.vhr.model.DTO;

import com.panther.vhr.model.entity.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Gin 琴酒
 * @data 2023/02/23 17:52
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDTO extends Employee {

    private Integer pid;
    private String pname;

    private Integer nid;
    private String nname;

    private Integer did;
    private String dname;

    private Integer jid;
    private String jname;

    private Integer poid;
    private String poname;

}
