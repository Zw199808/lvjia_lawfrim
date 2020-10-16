package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.sso.entity.ReRolePermission;
import com.xinou.lawfrim.sso.entity.ResReRolePermission;
import com.xinou.lawfrim.sso.mapper.ReRolePermissionSSOMapper;
import com.xinou.lawfrim.sso.service.ReRolePermissionSSOService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangbo on 2017/10/25.
 * 角色  权限  关系
 */
@Service("ReRolePermissionSSOServiceImpl")
@Primary
public class ReRolePermissionSSOServiceImpl extends ServiceImpl<ReRolePermissionSSOMapper, ReRolePermission> implements ReRolePermissionSSOService {


    public Page<ReRolePermission> selectPage(Page<ReRolePermission> page) {
        page.setRecords(baseMapper.selectList(page));
        return page;
    }

    @Override
    public List<ResReRolePermission> getPermissionByRoleId(List<Integer> roleIds) {
        return baseMapper.getPermissionByRoleId(roleIds);
    }
}
