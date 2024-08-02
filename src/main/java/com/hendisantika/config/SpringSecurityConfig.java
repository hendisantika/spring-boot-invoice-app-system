package com.hendisantika.config;

import com.hendisantika.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 11.02
 */
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Method for http authorization
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/list", "/locale", "/api/**",
                                "/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true).logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403")
                .and()
                .rememberMe();
        return http.build();
    }

    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder builder) throws Exception {
        // JPA
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);


//		 // JDBC
//		 // Spring security jdbc auth builder.jdbcAuthentication()
//
//		 .dataSource(dataSource) .passwordEncoder(passwordEncoder)
//		 .usersByUsernameQuery("select username, password, enabled from users where username=?"
//		 )
//		 .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a
//		 .user_id=u.id) where u.username=?"
//		 );
//
//
//
//		 PasswordEncoder encoder = this.passwordEncoder;
//
//		 // UserBuilder users = User.withDefaultPasswordEncoder();
//
//		 // Manera 1 // UserBuilder users = User.builder().passwordEncoder(password ->
//		 encoder.encode(password));
//
//		 // Manera 2 // UserBuilder users =
//		 User.builder().passwordEncoder(encoder::encode);
//
//		 // Manera 3 UserBuilder users = User.builder().passwordEncoder(password -> {
//		 return encoder.encode(password); });
//
//		 builder.inMemoryAuthentication()
//		 .withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
//		 .withUser(users.username("test").password("12345").roles("USER"));


    }

}
