package com.online.pollingsystem;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
                requests -> requests.requestMatchers("/register", "/login").permitAll().requestMatchers("/admin/**")
                        .hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login").invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID").permitAll());

		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			Optional<User> user = userService.findByUsername(username);
			if (user.isEmpty())
				throw new UsernameNotFoundException("User not found");
			return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
					user.get().getPassword(), AuthorityUtils.createAuthorityList(user.get().getRole()));
		};
	}

}