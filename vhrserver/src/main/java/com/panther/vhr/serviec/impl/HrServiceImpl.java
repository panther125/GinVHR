package com.panther.vhr.serviec.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panther.vhr.mapper.HrMapper;
import com.panther.vhr.mapper.HrRoleMapper;
import com.panther.vhr.mapper.RoleMapper;
import com.panther.vhr.model.entity.Hr;
import com.panther.vhr.model.job.RespHr;
import com.panther.vhr.serviec.HrService;
import com.panther.vhr.utils.MD5;
import com.panther.vhr.utils.ThreadData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 22:22
 **/
@Service
@Transactional
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr> implements HrService {

    @Resource
    private HrRoleMapper hrRoleMapper;

    @Resource
    private RoleMapper roleMapper;
    /**
     * 验证hr身份
     * @param name 用户输入的名称
     * @return 返回数据库的HR
     */
    @Override
    public RespHr queryHr(String name) {

        /*
            select h.id,name,phone,telephone,address,enabled,username,password,userface,remark,r.rid
            from hr h
            right join hr_role r on h.id = r.hrid
            where username = 'admin'
         */
        // 查询对应的hrid
        LambdaQueryWrapper<Hr> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Hr::getUsername,name);

        Hr hr = baseMapper.selectOne(lqw);
        ThreadData.setThreadData("currentHrid",hr);
        // 分析关系集合填充到respHr中
        RespHr respHr = new RespHr();
        BeanUtils.copyProperties(hr,respHr);

        respHr.setRoles(roleMapper.QueryRoleByUserName(hr.getUsername()));

        return respHr;
    }

    @Override
    public Hr queryCurrentHr(int hid) {
        return baseMapper.currentHr(hid);
    }

    @Override
    public int updataImgurl(String furl,int hid) {
        return baseMapper.updataImgurl(furl,hid);
    }

    @Override
    public int updateHrByUserName(Hr hr) {
        LambdaQueryWrapper<Hr> lqw = new LambdaQueryWrapper<>();
        lqw.eq(hr != null,Hr::getUsername,hr.getUsername());
        return baseMapper.update(hr,lqw);
    }

    @Override
    public boolean updateHrPasswd(String oldpass, String pass, Integer hrid) {

        // 判断旧密码是否正确
        if(oldpass == null || oldpass.length() == 0){
            return false;
        }
        String encrypt_oldpass = MD5.HEX(oldpass);
        LambdaQueryWrapper<Hr> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Hr::getPassword,encrypt_oldpass);
        lqw.eq(Hr::getId,hrid);
        if(baseMapper.selectCount(lqw) > 0){
            // 密码加密保存更新
            String encrypt_pass = MD5.HEX(pass);
            return baseMapper.updataPasswordByhrid(encrypt_pass,hrid) > 0;
        }
        return false;
    }

    @Override
    public List<Hr> getAllHrs(String keywords) {
        LambdaQueryWrapper<Hr> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(keywords),Hr::getUsername,keywords)
                .or()
                .like(StringUtils.hasText(keywords),Hr::getName,keywords);
        return baseMapper.selectList(lqw);
    }
}
