package com.imooc.demo.config.securityConfig.filter;
import com.imooc.demo.utils.jwt.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by echisan on 2018/6/23
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
           //如果没有token,或者token 开头不正确，重定向到指定页面
            response.sendRedirect( "/h5/version3/login.html");
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenHeader);

        System.out.println("_____________________"+authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.doFilterInternal(request, response, chain);
    //    response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String username = JwtTokenUtils.getUsername(token);
        List codeList = JwtTokenUtils.getUserRole(token);
        boolean expiration = JwtTokenUtils.isExpiration(token);
        if (username != null && !expiration){
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Object o : codeList) {
                GrantedAuthority permissionCode = new SimpleGrantedAuthority((String) o);
                grantedAuthorities.add(permissionCode);
            }
            return new UsernamePasswordAuthenticationToken(username, null,
                    grantedAuthorities
            );
        }
        return null;
    }
}
