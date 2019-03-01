package com.imooc.demo.config.securityConfig;
import com.imooc.demo.config.securityConfig.filter.JWTAuthenticationFilter;
import com.imooc.demo.config.securityConfig.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author : zhaChengwei
 * @date : 2018-10-16 10:32
 * @description:
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private UserDetailsService myUserDetailsService ;
    private static Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
    /**
     * 注册 token 转换拦截器为 bean
     * 如果客户端传来了 token ，那么通过拦截器解析 token 赋予用户权限
     * /**
     * 置user-detail服务
     *
     * 方法描述
     * accountExpired(boolean)                定义账号是否已经过期
     * accountLocked(boolean)                 定义账号是否已经锁定
     * and()                                  用来连接配置
     * authorities(GrantedAuthority...)       授予某个用户一项或多项权限
     * authorities(List)                      授予某个用户一项或多项权限
     * authorities(String...)                 授予某个用户一项或多项权限
     * disabled(boolean)                      定义账号是否已被禁用
     * withUser(String)                       定义用户的用户名
     * password(String)                       定义用户的密码
     * roles(String...)                       授予某个用户一项或多项角色
     *
     *
     * * 配置如何通过拦截器保护请求
     * 指定哪些请求需要认证，哪些请求不需要认证，以及所需要的权限
     * 通过调用authorizeRequests()和anyRequest().authenticated()就会要求所有进入应用的HTTP请求都要进行认证
     *
     * 方法描述
     * anonymous()                                        允许匿名用户访问
     * authenticated()                                    允许经过认证的用户访问
     * denyAll()                                          无条件拒绝所有访问
     * fullyAuthenticated()                如果用户是完整的话（不是通过Remember-me功能认证的），就允许访问
     * hasAnyAuthority(String...)                 如果用户具备给定权限中的某一个的话，就允许访问
     * hasAnyRole(String...)                    如果用户具备给定角色中的某一个的话，就允许访问
     * hasAuthority(String)                     如果用户具备给定权限的话，就允许访问
     * hasIpAddress(String)                    如果请求来自给定IP地址的话，就允许访问
     * hasRole(String)                        如果用户具备给定角色的话，就允许访问
     * not()                               对其他访问方法的结果求反
     * permitAll()                           无条件允许访问
     * rememberMe()                          如果用户是通过Remember-me功能认证的，就允许访问
     *
     * @return
     * @throws Exception
     */
//    @Bean
//    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
//        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
//        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
//        return authenticationTokenFilter;
//    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/h5/**"
                ,"/cms/js/**"
                ,"/cms/css/**"
                ,"/cms/image/**"
                ,"/cms/assets/**"
                ,"/cms/module/**"
                ,"/cms/plugins/**"
                ,"/cms/login.html"
                ,"/swagger-ui.html"
        ,"webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        http.authorizeRequests()
                .antMatchers("/api/v1/login").permitAll()
                .antMatchers("/swagger*/**").permitAll()
                .antMatchers("/v2/api-docs",//swagger api json
                        "/swagger-resources/configuration/ui",//用来获取支持的动作
                        "/swagger-resources",//用来获取api-docs的URL
                        "/swagger-resources/configuration/security",//安全选择
                        "/swagger-ui.html").permitAll()
                .antMatchers("/"
                        , "/cms/login.html"
                        ,"/h5/**"
                        ,"/login/captcha.jpg"
                        ,"/cms/js/**"
                        ,"/cms/css/**"
                        ,"/cms/image/**"
                        ,"/logout"
                        ,"/mmd/**"
                        ,"/menu/**"
                        ,"/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()   // 其他地址的访问均需验证权限
                .and()
                .formLogin()
                .loginProcessingUrl("/login");
//        http.headers().frameOptions().disable()
                http.csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilter(new JWTAuthenticationFilter(authenticationManager()))
                       .addFilter(new JWTAuthorizationFilter(authenticationManager()));
        http.addFilterBefore(myFilterSecurityInterceptor , FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myUserDetailsService).passwordEncoder(new PasswordEncoder() {
            /**
             * MD5加密
             * @param rawPassword
             * @return
             */
          @Override
          public String encode(CharSequence rawPassword) {
              return md5encoder.encodePassword(rawPassword.toString() ,null);
          }
          @Override
          public boolean matches(CharSequence rawPassword, String encodedPassword) {

              return md5encoder.isPasswordValid(encodedPassword,rawPassword.toString(),null);
          }

      });
    }
}
