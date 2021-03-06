package org.cuber.stub.conf;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;
import org.cuber.stub.sso.SSOResource;
import org.cuber.stub.sso.SSORole;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;

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
                .loginProcessingUrl("/login")
                .failureUrl("/login.htm")
                .successForwardUrl("/main")
                .and()
                .logout().logoutSuccessUrl("/login.htm")
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
                            ssoResource.getResourceUrl().equals(grantEntity)
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
}
