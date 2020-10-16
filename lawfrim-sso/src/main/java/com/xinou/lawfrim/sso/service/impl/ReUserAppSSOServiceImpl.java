package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.sso.entity.ReSYSUserApp;
import com.xinou.lawfrim.sso.entity.ResReSYSUserApp;
import com.xinou.lawfrim.sso.mapper.ReUserAppSSOMapper;
import com.xinou.lawfrim.sso.service.ReUserAppSSOService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangbo on 2017/10/25.
 * 用户  app  关系
 */
@Service("ReUserAppSSOServiceImpl")
@Primary
public class ReUserAppSSOServiceImpl extends ServiceImpl<ReUserAppSSOMapper, ReSYSUserApp> implements ReUserAppSSOService {


    public Page<ReSYSUserApp> selectPage(Page<ReSYSUserApp> page) {
        page.setRecords(baseMapper.selectList(page));
        return page;
    }

    @Override
    public List<ResReSYSUserApp> getAppByUserId(List<Integer> userIds) {
        return baseMapper.getAppByUserId(userIds);
    }
}
