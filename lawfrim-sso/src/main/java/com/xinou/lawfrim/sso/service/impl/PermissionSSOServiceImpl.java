package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.sso.entity.Permission;
import com.xinou.lawfrim.sso.mapper.PermissionSSOMapper;
import com.xinou.lawfrim.sso.service.PermissionSSOService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by zhangbo on 2017/10/25.
 * 权限
 */
@Service("permissionSSOServiceImpl")
@Primary
public class PermissionSSOServiceImpl extends ServiceImpl<PermissionSSOMapper, Permission> implements PermissionSSOService {


    public Page<Permission> selectPage(Page<Permission> page) {
        page.setRecords(baseMapper.selectList(page));
        return page;
    }

}
