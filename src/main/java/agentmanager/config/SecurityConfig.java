package agentmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import agentmanager.backoffice.rest.AdminSaleExecutiveController;
import agentmanager.common.security.MyAccessDeniedHandler;
import agentmanager.common.security.MyAuthenticationEntryPoint;
import agentmanager.common.service.filter.AuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final AdminSaleExecutiveController adminSaleExecutiveController;

	@Autowired
	private AuthenticationFilter authenticationFilter;

	SecurityConfig(AdminSaleExecutiveController adminSaleExecutiveController) {
		this.adminSaleExecutiveController = adminSaleExecutiveController;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf(CsrfConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeRequests(auth -> auth.antMatchers("/admin/login", "/sale_executive/login", "/health")
						.permitAll().antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest().authenticated())
				.exceptionHandling(exception -> exception.accessDeniedHandler(new MyAccessDeniedHandler())
						.authenticationEntryPoint(new MyAuthenticationEntryPoint()))
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
