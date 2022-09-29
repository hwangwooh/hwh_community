package com.example.hwh_community.config;


import com.example.hwh_community.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final AccountService accountService;
    private final DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().mvcMatchers("/", "/login", "/sign-up","/logout"
                        , "/check-email-token", "/email-login", "/login-by-email","/getList")
                .permitAll().mvcMatchers(HttpMethod.GET, "/profile/*")
                .permitAll().mvcMatchers(HttpMethod.GET, "/getList")
                .permitAll().anyRequest().authenticated();


        http.formLogin().loginPage("/login").permitAll();
        http.logout().logoutSuccessUrl("/");
        http.rememberMe().userDetailsService(accountService).tokenRepository(tokenRepository());

    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;

    }

}
