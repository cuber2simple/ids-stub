package org.cuber.stub.conf;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;
import org.cuber.stub.session.SSOResource;
import org.cuber.stub.session.SSORole;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;


@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/login.htm").anonymous().and()
                .authorizeRequests().antMatchers("/error/**").anonymous().and()
                .authorizeRequests().antMatchers("/").anonymous().and()
                .authorizeRequests().antMatchers("/js/**").anonymous().and()
                .authorizeRequests().antMatchers("/plugins/**").anonymous().and()
                .authorizeRequests().antMatchers("/dist/**").anonymous().and()
                .authorizeRequests().antMatchers("/img/**").anonymous().and()
                .authorizeRequests().antMatchers("/css/**").anonymous().and()
                .authorizeRequests().antMatchers("/register.htm").anonymous().and()
                .authorizeRequests().antMatchers("/verifyCaptcha").anonymous().and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")  //very import add
                .failureUrl("/login")
                .successForwardUrl("/main");

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
            ConfigAttribute conf = new SecurityConfig(StubConstant.WAIT_4_DECIDED_ROLE);
            return Arrays.asList(conf);
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
}
