package edu.pucp.gtics.lab6_gtics_20211.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/user/signIn")
                .loginProcessingUrl("/processLogin")
                .defaultSuccessUrl("/user/signInRedirect", true);

        http.logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        http.authorizeRequests()
                .antMatchers("/plataformas/editarFrm", "/plataformas/lista").hasAuthority("ADMIN")
                .antMatchers("/distribuidoras/editarFrm", "/distribuidoras/lista").hasAuthority("ADMIN")
                .antMatchers("/juegos/editarFrm", "/juegos/lista").hasAnyAuthority("ADMIN","USER")
                .anyRequest().permitAll();
    }

    @Autowired
    DataSource datasource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(datasource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT correo, password, enabled FROM usuarios WHERE correo = ?")
                .authoritiesByUsernameQuery("SELECT u.correo, u.autorizacion FROM usuarios u " +
                        " WHERE u.correo = ? and u.enabled = 1");
    }

}