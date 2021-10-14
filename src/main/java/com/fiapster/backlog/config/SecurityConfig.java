package com.fiapster.backlog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	JWTUtil jwtUtil;
	
	
	private static final String[] PUBLIC_MATCHERS = {
			"/api/cliente/curso/**",
			//"/api/user/**"
			"/api/user/rank",
			"/api/user/cadastro",
			"/api/config/badge",
			"/api/auth/forgot"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil, getApplicationContext()));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	/*
	 * 
	 * Apenas requests padrões são aceitas GET,POST 
	 *
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        //applyPermitDefaultValues());
        return source;
	}*/
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * Basic Auth
		 *
		 PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
				.withUser("userimp").password("{noop}dEKnk$hDXDWLMa0%").roles("SERVICE")
				.and()
				.withUser("admin").password("{noop}*!IcXvI!jm#J$YXW").roles("SERVICE","ADMIN");
		*/
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
				
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
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
				.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE");
				//.allowCredentials(true);
				//registry.addMapping("/**").allowedMethods("*");
				
			}
		};
	}
	

}
