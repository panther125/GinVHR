package com.panther.vhr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panther.vhr.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 23:24
 **/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from role\n" +
            "WHERE id in(\n" +
            "\tselect rid from hr_role\n" +
            "\tleft JOIN hr on hr.id = hr_role.hrid\n" +
            "\twhere hr.username = #{name}\n" +
            ")\n")
    List<Role> QueryRoleByUserName(String name);

    @Select("select * from role\n" +
            "WHERE id in(\n" +
            "\tselect rid from hr_role\n" +
            "\tleft JOIN hr on hr.id = hr_role.hrid\n" +
            "\twhere hr.id = #{hid}\n" +
            ")\n")
    List<Role> QueryRoleByHid(int hid);
}
