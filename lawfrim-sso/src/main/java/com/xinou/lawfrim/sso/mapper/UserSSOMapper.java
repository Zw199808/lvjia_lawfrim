package com.xinou.lawfrim.sso.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinou.lawfrim.sso.entity.SYSUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/24.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 * 系统用户
 */

public interface UserSSOMapper extends BaseMapper<SYSUser> {

    /**
     * 获取数据列表-分页
     * @param sysUser
     * @return
     */
    List<SYSUser> listSysUser(@Param("condition") SYSUser sysUser);

    /**
     * 获取数据总数
     * @param sysUser
     * @return
     */
    Integer countSysUser(@Param("condition") SYSUser sysUser);


    /**
     * 通过角色获取用户列表
     * @param sysUser
     * @return
     */
    List<SYSUser> listByRole(@Param("condition") SYSUser sysUser);

}
