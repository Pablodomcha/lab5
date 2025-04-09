package es.upm.dit.isst.Security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for H2 console
                .headers(headers -> headers.frameOptions().disable()) // Allow frames for H2 console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                        .requestMatchers("/").permitAll() // Allow access to the home page
                        .requestMatchers("/alumnos").permitAll() // Allow access to /alumnos
                        .requestMatchers("/profesores").hasRole("PROF") // Restrict access to /profesores
                        .requestMatchers("/todos").authenticated() // Require authentication for /todos
                        .anyRequest().permitAll() // Allow all other requests
                )
                .anonymous(Customizer.withDefaults()) // Explicitly allow anonymous access
                .formLogin(Customizer.withDefaults()) // Enable form-based login
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))) // Enable logout
                .httpBasic(Customizer.withDefaults()); // Enable basic authentication
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        String usersByUsernameQuery = "SELECT username, password, true FROM users WHERE username = ?";
        String authsByUserQuery = "SELECT username, authority FROM authorities WHERE username = ?";
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery(usersByUsernameQuery);
        users.setAuthoritiesByUsernameQuery(authsByUserQuery);
        return users;
    }
}
