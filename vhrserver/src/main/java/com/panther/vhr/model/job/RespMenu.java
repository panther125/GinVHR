package com.panther.vhr.model.job;

import com.panther.vhr.model.entity.Menu;
import com.panther.vhr.model.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 14:22
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RespMenu extends Menu {

    private List<Menu> children;
    private List<Role> roles;
}
