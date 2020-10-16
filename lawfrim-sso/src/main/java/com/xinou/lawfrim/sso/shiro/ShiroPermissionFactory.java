package com.xinou.lawfrim.sso.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.sso.entity.Permission;
import com.xinou.lawfrim.sso.mapper.PermissionSSOMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/19.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
public class ShiroPermissionFactory extends ShiroFilterFactoryBean {

    public static final String PERMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private PermissionSSOMapper mapper;

    /**
     * 记录配置中的过滤链
     */
    public static String filterChainDefinitions = "";//这个要和配置文件中的名称要一样

    /**
     * 初始化设置过滤链
     */
    @Override
    public void setFilterChainDefinitions(String definitions) {
        filterChainDefinitions = definitions;// 记录配置的静态过滤链
        Map<String, String> otherChains = new HashMap<>();
        //获取所有Permission
        List<Permission> list = mapper.selectList(new QueryWrapper<Permission>().eq("is_delete", 0));
        for (Permission permission : list) {
            //如果不为空值添加到section中
            if (StringUtils.isNotBlank(permission.getUrl()) && StringUtils.isNotBlank(permission.getName())) {
                otherChains.put(permission.getUrl(), MessageFormat.format(PERMISSION_STRING, permission.getName()));
            }

        }
        otherChains.put("/admin/**", "authc");
//        otherChains.put("/app/**", "authc");
        // 加载配置默认的过滤链
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        // 加上数据库中过滤链
        section.putAll(otherChains);
        setFilterChainDefinitionMap(section);

    }
}
