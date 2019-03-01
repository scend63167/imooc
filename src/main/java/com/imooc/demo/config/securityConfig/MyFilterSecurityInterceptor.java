package com.imooc.demo.config.securityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhaChengwei
 * @date : 2018-10-16 10:30
 * description :Security过滤器
 */
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    @Value("${base_url}")
    private String base_url;
    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }
    public static List<String> pattenURL = new ArrayList<String>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pattenURL.add("/login");
        pattenURL.add("/forget/Msg");
        pattenURL.add("/Msg");
        pattenURL.add(".jpg");
        pattenURL.add(".css");
        pattenURL.add("/image");
        pattenURL.add(".js");
        pattenURL.add(".png");
        pattenURL.add("/sevenPay/result");
        pattenURL.add("/send/channel/uv");
      pattenURL.add("/h5/juhepay/juhepayResult");
       pattenURL.add("/h5/huichaopay/huichaopayResult");
       pattenURL.add("/h5/alipay/notify/resultalipay");
       pattenURL.add("/h5/customer/channel");
        pattenURL.add("/protocol.html");
        pattenURL.add("/insurance.html");
       pattenURL.add("/h5_banner.html");
        pattenURL.add("/main.html");
        pattenURL.add("/index.html");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        //获取端口号之后的url
        String url = httpRequest.getRequestURI().toString();
        boolean flage = false;
        //在pattenUrl中的全部不拦截  url.indexOf(urlStr) > -1  表示urlStr在url中出现过，出现就不拦截
        for (String urlStr : pattenURL) {
            if(url.indexOf(urlStr) > -1){
                filterChain.doFilter(servletRequest, servletResponse);
                flage = false;
                break;
            }
            flage=true;
        }
        if(flage){
            if(session.getAttribute(null)!= null){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                //会话过期,请重新登录
                if(url != null && !"".equals(url)){
                    boolean result = url.contains("h5/version");
                    if(result){
                        //返回h5登录端
                        logger.info("*************h5请求拦截,用户未登录,url:{},*************",url);
                        if(url.contains("version1")){
                            httpResponse.sendRedirect(base_url + "/h5/version1/login.html");
                            return;
                        }else if(url.contains("version2")){
                            httpResponse.sendRedirect(base_url + "/h5/version2/login.html");
                            return;
                        }else if(url.contains("version3")){
                            httpResponse.sendRedirect(base_url + "/h5/version3/login.html");
                            return;
                        }
                        return;
                    }else {
                        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
                        invoke(fi);
                    }
                }
            }

        }
    }
    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        HttpServletResponse response = fi.getResponse();
        try {
        //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), response);
        }finally {
            super.afterInvocation(token, null);

        }
    }
}
