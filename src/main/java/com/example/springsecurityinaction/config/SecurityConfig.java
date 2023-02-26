package com.example.springsecurityinaction.config;

import com.example.springsecurityinaction.domain.UserAuthority;
import com.example.springsecurityinaction.repository.UserTokenRepository;
import com.example.springsecurityinaction.security.auth.InMemoryUserDetailsService;
import com.example.springsecurityinaction.security.csrf.CsrfTokenRepositoryImpl;
import com.example.springsecurityinaction.security.encoder.Sha512PasswordEncoder;
import com.example.springsecurityinaction.security.filter.CsrfTokenLoggingFilter;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;

//    private final CsrfTokenRepository csrfTokenRepository;

    private final UserTokenRepository userTokenRepository;

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new CsrfTokenRepositoryImpl(userTokenRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();

//        http.addFilterAfter(new CsrfTokenLoggingFilter(), CsrfFilter.class);
//            .addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
//            .addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class)
//            .addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class);

//        http
//            .authorizeHttpRequests().anyRequest().authenticated();
//        http.httpBasic(withDefaults());
//        http.httpBasic(c -> {
//            c.realmName("OTHER");
//            c.authenticationEntryPoint(new AuthenticationEntryPointImpl());
//        });
        http.httpBasic();

        http.csrf(c -> {
            c.csrfTokenRepository(csrfTokenRepository());

            // 1. AntRequestMatcher
            c.ignoringAntMatchers("/ciao");

            // 2. MvcRequestMatcher, HandlerMappingIntrospector
//            HandlerMappingIntrospector handlerMappingIntrospector = new HandlerMappingIntrospector();
//            MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector,
//                "/ciao");
//            c.ignoringRequestMatchers(mvcRequestMatcher);

            // 3. RegexRequestMatcher
            String pattern = ".*[0-9].*";
            RegexRequestMatcher regexRequestMatcher = new RegexRequestMatcher(pattern,
                HttpMethod.POST.name());
            c.ignoringRequestMatchers(regexRequestMatcher);
        });

        http.addFilterAfter(new CsrfTokenLoggingFilter(), CsrfFilter.class);

        http.authorizeHttpRequests().anyRequest().authenticated();

        http.formLogin()
            .defaultSuccessUrl("/main", true);
//            .successHandler(new AuthenticationSuccessHandler())
//            .failureHandler(new AuthenticationFailureHandler());

        // 1. hasAuthority("READ"), hasAnyAuthority(...)
//        http.authorizeHttpRequests()
//                .mvcMatchers(HttpMethod.GET, "/health").permitAll()
//                .anyRequest().hasAuthority("READ")
//                .anyRequest().hasAnyAuthority("READ", "WRITE");

        // 2. access(spEL) - authority
//        http.authorizeRequests()
//            .mvcMatchers(HttpMethod.GET, "/health").permitAll()
//            .mvcMatchers(HttpMethod.GET, "/hello").access("hasAuthority('READ') and !hasAuthority('WRITE')")
//            .anyRequest().authenticated();

        // 3. hasRole("MANAGER"), hasAnyRole(...), access(spEL) - role
//        http.authorizeRequests()
//            .mvcMatchers(HttpMethod.GET, "/health").permitAll()
////            .mvcMatchers(HttpMethod.GET, "/hello").hasRole("MANAGER")
////            .mvcMatchers(HttpMethod.GET, "/hello").hasAnyRole("MANAGER", "ADMIN")
//            .mvcMatchers(HttpMethod.GET, "/hello").access("hasRole('MANAGER') and !hasAuthority('ADMIN')")
//            .anyRequest().authenticated();

//        http.authorizeHttpRequests()
//            .mvcMatchers(HttpMethod.GET, "/a").authenticated()
//            .mvcMatchers(HttpMethod.POST, "/a").permitAll()
//            .mvcMatchers("/products/{code:^[0-9]*$}").permitAll()
//            .mvcMatchers(HttpMethod.GET, "/email/{email:^.+.*@.+\\.com$}").permitAll()
////            .regexMatchers("/email/.*(.+@.+\\.com)").permitAll()
//            .regexMatchers(".*/(us|uk|ca)+/(en|fr).*").authenticated()
//            .mvcMatchers("/main", "/products").permitAll()
//            .anyRequest().permitAll();

        return http.build();
    }

//    @Bean
    @SuppressWarnings("unused")
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

//     @Bean
    @SuppressWarnings("unused")
    public UserDetailsService userDetailsServiceByJdbcUserDetailsManager(DataSource dataSource) {
        String usersByUsernameQuery = "select username, password, enabled from users where username = ?";
        String authsByUsernameQuery = "select username, authority from authorities where username = ?";

        var userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authsByUsernameQuery);

        return new JdbcUserDetailsManager(dataSource);
    }

    // @Bean
    @SuppressWarnings("unused")
    public UserDetailsService userDetailsServiceByCustom() {
        UserDetails userDetails = User
            .withUsername("rolroralra")
            .password("password")
            .authorities(UserAuthority.allGrantedAuthorities())
            .build();

        return new InMemoryUserDetailsService(Map.of(userDetails.getUsername(), userDetails));
    }

    // @Bean
    @SuppressWarnings("unused")
    public UserDetailsService userDetailsServiceByInMemoryUserDetailsManager() {
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("rolroralra")
            .password("password")
            .authorities("read", "write", "delete")
            .build();

        userDetailsService.createUser(user);

        return userDetailsService;
    }

//    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();

    }

//    @Bean
    @SuppressWarnings("unused")
    public PasswordEncoder passwordEncoder2() {
        String idForEncode = "bcrypt";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    // @Bean
    @SuppressWarnings({"deprecation", "unused"})
    public PasswordEncoder passwordEncoder3() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
        encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256",
            new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
        encoders.put(Sha512PasswordEncoder.ENCODING_ALGORITHM, new Sha512PasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

//     @Bean
    @SuppressWarnings("unused")
    public PasswordEncoder passwordEncoder4() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder() {
        return new SCryptPasswordEncoder();
    }

}

