package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.sso.entity.App;
import com.xinou.lawfrim.sso.mapper.AppSSOMapper;
import com.xinou.lawfrim.sso.service.AppSSOService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by zhangbo on 2017/10/18.
 * admin后台管理员相关
 */
@Service("AppSSOServiceImpl")
@Primary
public class AppSSOServiceImpl extends ServiceImpl<AppSSOMapper, App> implements AppSSOService {


    public Page<App> selectPage(Page<App> page) {
        page.setRecords(baseMapper.selectList(page));
        return page;
    }


}
