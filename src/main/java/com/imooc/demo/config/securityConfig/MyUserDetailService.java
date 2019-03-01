package com.imooc.demo.config.securityConfig;
import com.imooc.demo.dao.ManagerDao;
import com.imooc.demo.dao.SysMenuDao;
import com.imooc.demo.entity.Manager;
import com.imooc.demo.entity.SysMenu;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaChengwei
 * @Date: 2018-10-16 10:32
 * @Description :实现UserDetailsService 当前用户的userName ，
 * pwd,以及所拥有的权限。
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager user = managerDao.findByUserName(username);
        if (user != null) {
            List<SysMenu> permissions = sysMenuDao.findByAdminUserId(user.getId());
            //通过userId 查询用户所属的角色
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (SysMenu permission : permissions) {
                if (permission != null && StringUtils.isNotEmpty(permission.getCode())) {
                    GrantedAuthority permissionCode = new SimpleGrantedAuthority(permission.getCode());
                    grantedAuthorities.add(permissionCode);
                }
            }
            User userDetail = new User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
          return userDetail;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
