package com.fiapster.backlog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fiapster.backlog.security.JWTAuthenticationFilter;
import com.fiapster.backlog.security.JWTAuthorizationFilter;
import com.fiapster.backlog.security.JWTUtil;
import com.fiapster.backlog.services.EmailService;
import com.fiapster.backlog.services.SmtpEmailService;

/**
 * @author Henrique
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Autowired
	JWTUtil jwtUtil;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/api/cliente/curso/**",
			"/api/user/rank",
			"/api/user/cadastro",
			"/api/config/badge",
			"/api/auth/forgot",
	};
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {	
		http.cors().and().csrf().disable();
        http
            .authorizeHttpRequests()
            .antMatchers(PUBLIC_MATCHERS).permitAll()
    		.anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil, appContext));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(authenticationConfiguration), jwtUtil, userDetailsService));
        http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
	
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PATCH", "DELETE");
				
			}
		};
	}
	

}
