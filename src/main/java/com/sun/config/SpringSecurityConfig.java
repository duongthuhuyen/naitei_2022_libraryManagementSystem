package com.sun.config;

import com.sun.config.handlers.LoginSuccessHandler;
import com.sun.config.handlers.LogoutHandler;
import com.sun.entity.User;
import com.sun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.sun.repository",
        "com.sun.service"
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private  AuthenticationSuccessHandler loginSuccessHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutHandler() {
        return new LogoutHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/user/login")
                .usernameParameter("email")
                .passwordParameter("password");

        http.formLogin().defaultSuccessUrl("/home")
                .failureUrl("/user/login?error");

        http.formLogin().successHandler(this.loginSuccessHandler);

//        http.logout().logoutSuccessUrl("/user/login");
        http.logout().logoutUrl("/user/logout");
        http.logout().logoutSuccessHandler(this.logoutSuccessHandler);

        http.exceptionHandling().accessDeniedPage("/user/login?accessDenied");

        http.authorizeHttpRequests().antMatchers("/home").authenticated()
                .antMatchers("/admin/**").hasAuthority("ADMIN");}
}
