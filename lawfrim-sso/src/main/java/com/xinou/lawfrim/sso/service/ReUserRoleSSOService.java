package com.xinou.lawfrim.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.sso.entity.ReSYSUserRole;
import com.xinou.lawfrim.sso.entity.ResReSYSUserRole;

import java.util.List;

/**
 * Created by zhangbo on 2017/10/24.
 *
 */
public interface ReUserRoleSSOService extends IService<ReSYSUserRole> {

    /**
     * 批量获取用户角色
     */
    List<ResReSYSUserRole> getRoleByUserId(List<Integer> userIds);

}
