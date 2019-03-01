package com.imooc.demo.config.securityConfig;
import com.imooc.demo.dao.SysMenuDao;
import com.imooc.demo.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * @author  zhaChengwei
 * @date  2018-10-16 10:32d
 * @description 权限资源关联器
 */
@Service
public class MyInvocationSecurityMetadataSourceService  implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SysMenuDao sysMenuDao;
    public HashMap<String, Collection<ConfigAttribute>> map =null;
    /**
     * 加载菜单表所有code
     *
     */
    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<SysMenu> sysMenuEntities  =sysMenuDao.findAll();
        for(SysMenu permission : sysMenuEntities) {
            if (permission.getUrl() == null || permission.getCode() == null) continue;
            array=new ArrayList<>();
            cfg=new SecurityConfig(permission.getCode());
            array.add(cfg);
            //此处url 作key ,code 作value
            map.put(permission.getUrl(),array);
        }

    }

    /**
     *
     * @param object :url
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
       if(map == null ) loadResourceDefine();
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            //遍历库中的url， 如果库中的url 与此访问的url 一致的话 返回此url 的code 值
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
