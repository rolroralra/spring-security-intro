package com.example.springsecurityinaction.config;

import com.example.springsecurityinaction.domain.UserAuthority;
import com.example.springsecurityinaction.security.service.InMemoryUserDetailsService;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests().anyRequest().authenticated();
//        http.httpBasic(withDefaults());
        http.authorizeHttpRequests((authRequestMatcher) ->
                authRequestMatcher
                    .mvcMatchers(HttpMethod.GET, "/health").permitAll()
                    .anyRequest().authenticated())
            .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsServiceByLdapUserDetailsManager() {
        var contextSource = new DefaultSpringSecurityContextSource(
            "ldap://127.0.0.1:33389/dc=springframework,dc=org"
        );
        contextSource.afterPropertiesSet();

        LdapUserDetailsManager manager = new LdapUserDetailsManager(contextSource);
        manager.setUsernameMapper(
            new DefaultLdapUsernameToDnMapper("ou=groups", "uid"));
        manager.setGroupSearchBase("ou=groups");

        return manager;
    }

//    @Bean
    public UserDetailsService userDetailsServiceByJdbcUserDetailsManager(DataSource dataSource) {
        String usersByUsernameQuery = "select username, password, enabled from users where username = ?";
        String authsByUsernameQuery = "select username, authority from authorities where username = ?";

        var userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authsByUsernameQuery);

        return new JdbcUserDetailsManager(dataSource);
    }

    // @Bean
    public UserDetailsService userDetailsServiceByCustom() {
        UserDetails userDetails = User
            .withUsername("rolroralra")
            .password("password")
            .authorities(UserAuthority.allGrantedAuthorities())
            .build();

        return new InMemoryUserDetailsService(Map.of(userDetails.getUsername(), userDetails));
    }

    // @Bean
    public UserDetailsService userDetailsServiceByInMemoryUserDetailsManager() {
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("rolroralra")
            .password("password")
            .authorities("read", "write", "delete")
            .build();

        userDetailsService.createUser(user);

        return userDetailsService;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        String idForEncode = "bcrypt";
//        Map<String,PasswordEncoder> encoders = new HashMap<>();
//        encoders.put(idForEncode, new BCryptPasswordEncoder());
//        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//        encoders.put("scrypt", new SCryptPasswordEncoder());

//        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}
