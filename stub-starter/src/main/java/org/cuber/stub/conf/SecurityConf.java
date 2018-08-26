package org.cuber.stub.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login.htm").anonymous().and()
                .authorizeRequests().antMatchers("/error/**").anonymous().and()
                .authorizeRequests().antMatchers("/static/**").anonymous().and()
                .authorizeRequests().antMatchers("/register.htm/**").anonymous().and()
                .formLogin()
                .loginPage("/login.htm")
                .loginProcessingUrl("/login")  //very import add
                .failureUrl("/.htm");
    }
}