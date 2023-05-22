package com.confapp.service;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.confapp.ConfappApplication;
import com.confapp.entity.Usuario;
import com.confapp.error.SenhaInvalidaException;
import com.confapp.repo.UsuarioRepository;
import org.springframework.security.core.userdetails.User;

@Service
public class UsuarioServiceImpl implements UserDetailsService {


	private PasswordEncoder passwordEncoder;

	private UsuarioRepository usuarioRepository;


	public UsuarioServiceImpl(UsuarioRepository usuarioRepository,  PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Usuario: "+username);
		Usuario user = usuarioRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
		// REGRAS DE ROLES - INICIO
		// String [] roles
		// REGRAS DE ROLES - FIM
		System.out.println("Usuario BD: "+user.getLogin());
		System.out.println("Usuario BD: "+user.getSenha());
		System.out.println("Usuario BD: "+user.getRole());

		System.out.println("Usuario BD: "+passwordEncoder.encode(user.getSenha()));
		return User.builder().username(user.getLogin()).password(passwordEncoder.encode(user.getSenha()))
				.roles(user.getRole()).build();
	}

	 public UserDetails autenticar( Usuario usuario ){
	        UserDetails user = loadUserByUsername(usuario.getLogin());
	        boolean senhasBatem = passwordEncoder.matches( usuario.getSenha(), user.getPassword() );

	        if(senhasBatem){
	            return user;
	        }

	        throw new SenhaInvalidaException();
	    }

	public static void main(String[] args) {

		// SpringApplication.run(ConfappApplication.class, args);
		ConfigurableApplicationContext contexto = SpringApplication.run(ConfappApplication.class, args);

		UsuarioServiceImpl service = contexto.getBean(UsuarioServiceImpl.class);
		Usuario usuario = new Usuario();
		usuario.setLogin("cris");
		//UsuarioServiceImpl usuarioServiceImpl = new UsuarioServiceImpl();
	//	usuarioServiceImpl.loadUserByUsername("cris");
	//	service.findALL();
		service.loadUserByUsername("cris");

		//System.out.println("Token: " + tokenString);

	}

}
