package com.xinou.lawfrim.sso.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.sso.entity.Permission;

import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/18.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 * 权限表
 */
public interface PermissionSSOMapper extends BaseMapper<Permission> {

    /**
     * 查询 系统列表 分页显示
     * @param page  翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     */
    List<Permission> selectList(Page<Permission> page);

}
