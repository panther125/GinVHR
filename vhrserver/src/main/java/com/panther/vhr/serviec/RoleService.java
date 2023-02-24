package com.panther.vhr.serviec;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panther.vhr.model.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> QueryRoleByHid(int hid);

}
