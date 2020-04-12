package ru.itis.servlets.security.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    @Qualifier(value = "customUserDetailsService")
    private UserDetailsService userDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/signUp").permitAll()
                .antMatchers("/hello").authenticated()
                .antMatchers("/users").hasAuthority("ADMIN")
                .antMatchers("/addpost").authenticated()
                .antMatchers("/myposts").authenticated()
                .antMatchers("/usersAdm").hasAuthority("ADMIN")
        ;

        http.formLogin()
                .loginPage("/signIn")
                .usernameParameter("email")
                .defaultSuccessUrl("/")
                .failureUrl("/signIn?error")
                .permitAll();
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
