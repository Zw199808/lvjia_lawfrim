package com.xinou.lawfrim.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.sso.entity.ReRolePermission;
import com.xinou.lawfrim.sso.entity.ResReRolePermission;

import java.util.List;

/**
 * Created by zhangbo on 2017/10/25.
 *
 */
public interface ReRolePermissionSSOService extends IService<ReRolePermission> {

    List<ResReRolePermission> getPermissionByRoleId(List<Integer> roleIds);

}
