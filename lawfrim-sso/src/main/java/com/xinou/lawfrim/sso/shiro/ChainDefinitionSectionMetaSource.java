package com.xinou.lawfrim.sso.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.sso.entity.Permission;
import com.xinou.lawfrim.sso.mapper.PermissionSSOMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/1 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 * 为shiro 提供动态url权限
 * spring-shiro.xml中配置
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {

    @Autowired
    private PermissionSSOMapper mapper;

    private String filterChainDefinitions;

    /**
     * 默认premission字符串
     */
    public static final String PERMISSION_STRING = "perms[\"{0}\"]";

    @Override
    public Ini.Section getObject() throws BeansException {

        //获取所有Permission
        List<Permission> list = mapper.selectList(new QueryWrapper<Permission>().eq("is_delete",0));

        Ini ini = new Ini();
        //加载默认的url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //循环Permission的url,逐个添加到section中。section就是filterChainDefinitionMap,
        //里面的键就是链接URL,值就是存在什么条件才能访问该链接
        for (Permission permission : list) {
            //如果不为空值添加到section中
            if (StringUtils.isNotEmpty(permission.getUrl()) && StringUtils.isNotEmpty(permission.getName())) {
                section.put(permission.getUrl(), MessageFormat.format(PERMISSION_STRING, permission.getName()));
            }

        }

        return section;
    }

    /**
     * 通过filterChainDefinitions对默认的url过滤定义
     *
     * @param filterChainDefinitions 默认的url过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
