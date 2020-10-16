package com.xinou.lawfrim.sso.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.sso.entity.ICCardSSO;
import com.xinou.lawfrim.sso.entity.MemberSSO;
import com.xinou.lawfrim.sso.entity.SYSUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xiao_XX on 2017/10/16.
 * admin后台相关
 */
public interface AdminSSOMapper extends BaseMapper<SYSUser> {

    /**
     * 获取个人角色列表
     */
    List<String> findRoleByUserId(@Param("userId") int userId);

    /**
     * 获取个人权限url列表
     */
    List<String> findPermissionUrlByUserId(@Param("userId") int userId);


    /**
     * 登录
     * @param account 账号
     * @param ip 客户端ip
     */
    void login(@Param("account") String account, @Param("ip") String ip);

    @Select("SELECT su.*,ifnull(bt.id,0) teacherId FROM sys_user su left join bus_teacher bt on bt.user_id = su.id\n" +
            "where su.id = #{id}")
    SYSUser teacherUser(@Param("id") Integer id);


    // 查找ic卡是否存在
    @Select("SELECT * FROM bus_ic_card where is_delete = 0 and ic_no = #{icNo}")
    ICCardSSO icInfo(@Param("icNo") String icNo);

    @Select("SELECT * FROM bus_member where is_delete = 0 and id = #{memberId}")
    MemberSSO memberInfo(@Param("memberId") Integer memberId);


    @Select("SELECT * FROM bus_member where is_delete = 0 and user_id = #{userId}")
    MemberSSO memberInfoById(@Param("userId") Integer userId);

}
