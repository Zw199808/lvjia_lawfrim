package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.sso.entity.ReSYSUserRole;
import com.xinou.lawfrim.sso.entity.ResReSYSUserRole;
import com.xinou.lawfrim.sso.mapper.ReUserRoleSSOMapper;
import com.xinou.lawfrim.sso.service.ReUserRoleSSOService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangbo on 2017/10/24.
 * 系统用户
 */
@Service("ReUserRoleSSOServiceImpl")
@Primary
public class ReUserRoleSSOServiceImpl extends ServiceImpl<ReUserRoleSSOMapper, ReSYSUserRole> implements ReUserRoleSSOService {


    public Page<ReSYSUserRole> selectPage(Page<ReSYSUserRole> page) {
        page.setRecords(baseMapper.selectList(page));
        return page;
    }

    @Override
    public List<ResReSYSUserRole> getRoleByUserId(List<Integer> userIds) {
        return baseMapper.getRoleByUserId(userIds);
    }
}
