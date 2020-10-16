package com.xinou.lawfrim.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.sso.entity.ReSYSUserApp;
import com.xinou.lawfrim.sso.entity.ResReSYSUserApp;

import java.util.List;

/**
 * Created by zhangbo on 2017/10/24.
 *
 */
public interface ReUserAppSSOService extends IService<ReSYSUserApp> {

    /**
     * 批量获取用户系统
     */
    List<ResReSYSUserApp> getAppByUserId(List<Integer> userIds);

}
