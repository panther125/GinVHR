package com.panther.vhr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panther.vhr.model.entity.Menu;
import com.panther.vhr.model.job.RespMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select * from menu WHERE parentId = #{pid}")
    List<Menu> QueryAllChildMenu(int pid);

    @Select("select * from menu WHERE parentId is not null and url='/'")
    List<RespMenu> QueryParent();

    @Select("select mid from menu_role where rid = #{rid}")
    List<Integer> getMenuByid(@Param("rid") int id);
}
