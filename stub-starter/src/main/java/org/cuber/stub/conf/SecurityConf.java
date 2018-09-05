package org.cuber.stub.conf;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;
import org.cuber.stub.session.SSOResource;
import org.cuber.stub.session.SSORole;
import org.cuber.stub.session.SSOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
    private List<String> anonymousString = Arrays.asList("/login.htm", "/error/**", "/captcha", "/j_spring_security_check",
            "/js/**", "/plugins/**", "/dist/**", "/img/**", "/css/**", "/register.htm", "/verifyCaptcha", "/");

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
        http.authorizeRequests().and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/j_spring_security_check")
                .failureHandler(new LoginFailureHandler())
                .successForwardUrl("/main")
                .and()
                .logout().logoutSuccessHandler(new LogoutHandler())
                .and()
                .rememberMe();


        http.authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(new LighterSecurityMetadataSourceService());
                object.setAccessDecisionManager(new SSOAccessDecisionManager());
                return object;
            }
        });
    }

    class LighterSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

        @Override
        public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
            String url = ((FilterInvocation) object).getRequestUrl();
            String grantEntity = StringUtils.substringBeforeLast(url, "?");
            if (!anonymousString.stream().anyMatch(str -> new AntPathMatcher().match(str, grantEntity))) {
                ConfigAttribute conf = new SecurityConfig(StubConstant.WAIT_4_DECIDED_ROLE);
                return Arrays.asList(conf);
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

    class SSOAccessDecisionManager implements AccessDecisionManager {
        @Override
        public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
            String url = ((FilterInvocation) object).getRequestUrl();
            String grantEntity = StringUtils.substringBeforeLast(url, "?");
            boolean deny = true;
            if (CollectionUtils.isNotEmpty(authentication.getAuthorities())) {
                deny = !authentication.getAuthorities().stream().anyMatch(role -> isGrant(role, grantEntity));
            }
            if (deny) {
                throw new AccessDeniedException("don't have match role for [" + grantEntity + "]");
            }
        }

        private boolean isGrant(GrantedAuthority grantedAuthority, String grantEntity) {
            if (grantedAuthority instanceof SSORole) {
                SSORole ssoRole = (SSORole) grantedAuthority;
                Set<SSOResource> resources = ssoRole.getResources();
                if (CollectionUtils.isNotEmpty(resources)) {
                    return resources.stream().anyMatch(ssoResource ->
                            ssoResource.getMenuUrl().equals(grantEntity)
                    );
                }
            }
            return false;
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

    @Component
    class LoginFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
            httpServletRequest.setAttribute("msg", e.getLocalizedMessage());
            httpServletRequest.setAttribute("type", StubConstant.AUTH_FAILED);
            httpServletRequest.getRequestDispatcher("/login.htm").forward(httpServletRequest, httpServletResponse);
        }
    }

    class LogoutHandler implements LogoutSuccessHandler{

        @Override
        public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
            String logoutMsg = "[%s] 已成功登出";
            Object principal = authentication.getPrincipal();
            if(principal instanceof SSOUser){
                SSOUser user = (SSOUser) principal;
                logoutMsg = String.format(logoutMsg, user.getUserName());
            }else{
                logoutMsg = String.format(logoutMsg, authentication.getName());
            }
            httpServletRequest.setAttribute("msg", logoutMsg);
            httpServletRequest.setAttribute("type", StubConstant.LOGOUT);
            httpServletRequest.getRequestDispatcher("/login.htm").forward(httpServletRequest, httpServletResponse);
        }
    }
}
