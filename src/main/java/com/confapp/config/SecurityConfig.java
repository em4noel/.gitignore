package com.confapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.confapp.security.jwt.JwtAuthFilter;
import com.confapp.security.jwt.JwtService;
import com.confapp.service.UsuarioServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
    private JwtService jwtService;

	  @Bean
	    public OncePerRequestFilter jwtFilter(){
	        return new JwtAuthFilter(jwtService, usuarioService);
	    }


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub

		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
	}

	@Override
	 protected void configure( HttpSecurity http ) throws Exception {

        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/confapi/check/**")
                    .hasAnyRole("USER", "ADMIN")
               // .antMatchers("/api/pedidos/**")
              //      .hasAnyRole("USER", "ADMIN")
             //   .antMatchers("/api/produtos/**")
              //      .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/confapi/auth")
                    .permitAll()
                .anyRequest().authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
            //.addFilter(jwtFilter());
                //.addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class);

    }

	/*protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		http.csrf().disable().authorizeRequests().antMatchers("/api/usuarios/**").hasRole("USER")
				.antMatchers("/api/usuarios/**").hasAnyRole("USER", "ADMIN").and().httpBasic();

		// .hasAnyRole("USER").;

	}*/

}
