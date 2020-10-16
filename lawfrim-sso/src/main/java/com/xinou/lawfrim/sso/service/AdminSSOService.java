package com.xinou.lawfrim.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.entity.AdminEntity;
import com.xinou.lawfrim.sso.entity.SYSUser;


/**
 * Created by zhangbo on 2017/10/18.
 * admin相关
 */
public interface AdminSSOService extends IService<SYSUser> {


    void login(String account, String ip);


    APIResponse adminLogin(AdminEntity adminEntity);


}
