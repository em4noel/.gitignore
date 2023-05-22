package com.confapp.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "usuario", schema = "appConf")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codg_usuario;
	@Column
	@NotEmpty(message = "Campo requerido")
	private String login;
	@Column
	@NotEmpty(message = "Campo requerido")
	private String senha;

	@Column
	@NotEmpty(message = "Campo requerido")
	private String role;

	public Long getCodg_usuario() {
		return codg_usuario;
	}

	public void setCodg_usuario(Long codg_usuario) {
		this.codg_usuario = codg_usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	public Usuario(Long codg_usuario, @NotEmpty(message = "Campo requerido") String login,
			@NotEmpty(message = "Campo requerido") String senha, @NotEmpty(message = "Campo requerido") String role) {
		super();
		this.codg_usuario = codg_usuario;
		this.login = login;
		this.senha = senha;
		this.role = role;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}











}
