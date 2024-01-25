package pe.gob.pj.pide.api.app.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pe.gob.pj.pide.service.SeguridadService;
import pe.gob.pj.pide.service.impl.CustomUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration  {

	@Autowired
	private SeguridadService seguridadService;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.cors().and().csrf().disable()
        	.authorizeRequests()
	        .antMatchers("/").permitAll()
        	.antMatchers("/healthcheck").permitAll()
	        .antMatchers("/css/**").permitAll()
	        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/configuration/ui","/swagger-resources/**",
	        		"/configuration/security","/swagger-ui.html/**","/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and()
			.addFilter(new JwtAuthenticationFilter(authenticationManager(), seguridadService))	
	        .addFilter(new JwtAuthorizationFilter(authenticationManager(), seguridadService))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
        		.antMatchers("/v3/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
        				"/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui",
        				"/docs/**", "/swagger-resources/configuration/security");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	CustomUserDetailsService userDetailsService = new CustomUserDetailsService(seguridadService, passwordEncoder());
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(authenticationProvider());
	}
}