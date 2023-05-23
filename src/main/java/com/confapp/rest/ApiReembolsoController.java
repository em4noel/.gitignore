package com.confapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.confapp.entity.Usuario;
import com.confapp.rest.dto.CredenciaisDTO;
import com.confapp.rest.dto.TokenDTO;
import com.confapp.security.jwt.JwtService;
import com.confapp.service.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/confapi")
@RequiredArgsConstructor
public class ApiReembolsoController {

	//@Autowired
	//UsuarioRepository usuarioRepo;
	@Autowired
	 UsuarioServiceImpl usuarioService;
	@Autowired
	JwtService jwtService;


	  public ApiReembolsoController(UsuarioServiceImpl usuarioService) {
		  this.usuarioService = usuarioService;
	  }


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/check/{user}")
	public List<Usuario> checkUsuario(@PathVariable("user") String login) {
		List<Usuario> usr= null;

		System.out.println("Chamou user: "+login);
		/* List<Usuario> usr= null;
		 for (Iterator iterator = usr.iterator(); iterator.hasNext();) {
			 Usuario usuario = (Usuario)
		  iterator.next(); System.out.println("FOOOI "+usuario.getLogin() + " - "+
		  usuario.getSenha()); }*/

		 return usr;
	}

	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {

            System.err.println("Teste:");

		 try{
			Usuario usuario = new Usuario();
			usuario.setLogin(credenciais.getLogin());
			usuario.setSenha(credenciais.getSenha());


			UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
			 String token = jwtService.gerarToken(usuario);

			TokenDTO tokenDTO = new TokenDTO();
			tokenDTO.setLogin(usuario.getLogin());
			tokenDTO.setToken(token);

		return tokenDTO;
	        } catch (UsernameNotFoundException e){
	            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
	        }
	}

}
