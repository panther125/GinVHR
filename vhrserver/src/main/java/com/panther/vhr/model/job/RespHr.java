package com.panther.vhr.model.job;

import com.panther.vhr.model.entity.Hr;
import com.panther.vhr.model.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 23:17
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RespHr extends Hr {

    private List<Role> roles;

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
