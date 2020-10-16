package com.xinou.lawfrim.sso.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.sso.entity.ReSYSUserRole;
import com.xinou.lawfrim.sso.entity.ResReSYSUserRole;

import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/24.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 *
 */
public interface ReUserRoleSSOMapper extends BaseMapper<ReSYSUserRole> {


    /**
     * 查询 列表 分页显示
     * @param page  翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     */
    List<ReSYSUserRole> selectList(Page<ReSYSUserRole> page);


    /**
     * 批量获取用户角色
     */
    List<ResReSYSUserRole> getRoleByUserId(List<Integer> userIds);


}
