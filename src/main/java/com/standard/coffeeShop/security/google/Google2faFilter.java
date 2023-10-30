package com.standard.coffeeShop.security.google;

import com.standard.coffeeShop.repository.entity.security.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class Google2faFilter extends GenericFilterBean {

    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    private final RequestMatcher url2fa = new AntPathRequestMatcher("/authentication/2fa/**");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(url2fa.matches(request)){
            filterChain.doFilter(request,response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && !authenticationTrustResolver.isAnonymous(authentication)){
            log.debug("Processing 2FA Filter");
            if(authentication.getPrincipal() != null && authentication.getPrincipal() instanceof UserEntity){
                UserEntity user = (UserEntity) authentication.getPrincipal();
                if(user.isUseGoogle2fa()){
                    if(user.isUseGoogle2fa() && user.isUserGoogle2FaRequired()){
                        log.debug("2FA required");
                        return;
                    }
                }
             }
        }
        filterChain.doFilter(request, response);
    }

}
