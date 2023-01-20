package com.iykescode.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/saveMsg")
                    .ignoringAntMatchers("/public/**")
                    .ignoringAntMatchers("/api/**")
                    .ignoringAntMatchers("/data-api/**")
                    .ignoringAntMatchers("/iykescodeschool/actuator/**").and()
                .authorizeRequests()
                    .mvcMatchers("/user/**").authenticated()
                    .mvcMatchers("/api/**").authenticated()
                    .mvcMatchers("/data-api/**").authenticated()
                    .mvcMatchers("/displayProfile").authenticated()
                    .mvcMatchers("/updateProfile").authenticated()
                    .mvcMatchers("/user/displayMessages/**").hasRole("ADMIN")
                    .mvcMatchers("/iykescodeschool/actuator/**").hasRole("ADMIN")
                    .mvcMatchers("/admin/**").hasRole("ADMIN")
                    .mvcMatchers("/student/**").hasRole("STUDENT")
                    .mvcMatchers("/").permitAll()
                    .mvcMatchers("/holidays/**").permitAll()
                    .mvcMatchers("/contact").permitAll()
                    .mvcMatchers("/saveMsg").permitAll()
                    .mvcMatchers("/courses").permitAll()
                    .mvcMatchers("/about").permitAll()
                    .mvcMatchers("/login").permitAll()
                    .mvcMatchers("/public/**").permitAll()
                        .and().formLogin()
                            .loginPage("/login")
                            .defaultSuccessUrl("/user/dashboard")
                            .failureUrl("/login?error=true")
                            .permitAll()
                        .and().logout()
                            .logoutSuccessUrl("/login?logout=true")
                            .invalidateHttpSession(true)
                            .permitAll()
                        .and().httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 }
