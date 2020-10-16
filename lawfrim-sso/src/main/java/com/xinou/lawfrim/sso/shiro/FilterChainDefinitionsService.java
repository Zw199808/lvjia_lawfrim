package com.xinou.lawfrim.sso.shiro;

import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/19.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
@Service
public class FilterChainDefinitionsService {

    @Resource
    private ShiroPermissionFactory permissionFactory;

    public void reloadFilterChains() {
        synchronized (permissionFactory) {   //强制同步，控制线程安全
            AbstractShiroFilter shiroFilter;

            try {
                shiroFilter = (AbstractShiroFilter) permissionFactory.getObject();

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();

                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                manager.getFilterChains().clear();
                permissionFactory.getFilterChainDefinitionMap().clear();
                // 重新设置权限
                permissionFactory.setFilterChainDefinitions(ShiroPermissionFactory.filterChainDefinitions);//传入配置中的filterChains

                Map<String, String> chains = permissionFactory.getFilterChainDefinitionMap();
                //重新生成过滤链
                if (!CollectionUtils.isEmpty(chains)) {
                    for (Map.Entry<String, String> chain : chains.entrySet()) {
                        manager.createChain(chain.getKey(), chain.getValue().replace(" ", ""));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

