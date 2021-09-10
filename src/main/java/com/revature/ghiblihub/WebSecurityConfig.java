package com.revature.ghiblihub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    WebUserDetailsService webUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(webUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Ignore displaying any css files
        web.ignoring().antMatchers("/css/**");
    }

    /**
     * Configures which endpoints can be accessed base on the role of the user
     * @param http HttpSecurity object
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/profile").hasAnyRole("ADMIN", "USER")
                .antMatchers("/films/title/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/films").permitAll()
                .antMatchers("/login/newuser").permitAll()
                .antMatchers(HttpMethod.POST, "/login/newuserPage").permitAll()
                .and()
                  .formLogin()
                      .loginPage("/login").permitAll()
                .and()
                  .logout().permitAll();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
