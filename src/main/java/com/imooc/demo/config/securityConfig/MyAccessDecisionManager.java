package com.imooc.demo.config.securityConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author  zhaChengwei
 * @date  2018-10-16 10:30
 * description 访问决策器
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    /**
     * @param  decide 在方法请求时调用
     * @param configAttributes 状态清求url允许的code 这里是从MyInvocationSecurityMetadataSource的loadResourceDefine获取的
     * @param object url
     * @param  authentication  装载了从数据库查出的权限数据，这里是从MyUserDetailService里的loadUserByUsername方法里的grantedAuths对象的值传过来给 authentication 对象,
     * 简单点就是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
     */
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if(null== configAttributes || configAttributes.size() <=0) {
            logger.info("无访问权限");
            throw  new AccessDeniedException("无访问权限");
        }
        ConfigAttribute c;
        String needRole;
        for(Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            needRole = c.getAttribute();
            for(GrantedAuthority ga : authentication.getAuthorities()) {
                if(needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
