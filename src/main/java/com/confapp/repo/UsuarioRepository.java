package com.confapp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.confapp.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {



	@Override
	default List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	Optional<Usuario> findByLogin(String login);
}
