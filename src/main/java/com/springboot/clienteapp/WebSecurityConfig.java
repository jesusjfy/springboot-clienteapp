package com.springboot.clienteapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.clienteapp.util.LoginSuccessMessage;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passEnconder;
	
	@Autowired
	private LoginSuccessMessage successMessage;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/index", "/home", "/", "/css/**", "/js/**", "/images/**").permitAll()
		.antMatchers("/views/clientes").hasAnyRole("USER")
		.antMatchers("/views/clientes/create").hasAnyRole("ADMIN")
		.antMatchers("/views/clientes/save").hasAnyRole("ADMIN")
		.antMatchers("/views/clientes/edit/**").hasAnyRole("ADMIN")
		.antMatchers("/views/clientes/delete/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successMessage)
		.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll();
	}



	@Autowired
	public void configureSecurityGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder
		.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passEnconder)
		.usersByUsernameQuery("select username,password,enabled from users where username = ?")
		.authoritiesByUsernameQuery("select u.username,r.rol from roles r inner join users u on r.user_id = u.id where u.username = ?");
	}
}
