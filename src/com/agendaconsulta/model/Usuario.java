package com.agendaconsulta.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USU_CODIGO")
	private long usu_codigo;
	
	@Column(name = "USU_CELULAR", length=11)
	private String usu_celular;
	
	@Column(name = "USU_NOME", length=50)
	private String usu_nome;

	public long getUsu_codigo() {
		return usu_codigo;
	}

	public void setUsu_codigo(long usu_codigo) {
		this.usu_codigo = usu_codigo;
	}

	public String getUsu_celular() {
		return usu_celular;
	}

	public void setUsu_celular(String usu_celular) {
		this.usu_celular = usu_celular;
	}

	public String getUsu_nome() {
		return usu_nome;
	}

	public void setUsu_nome(String usu_nome) {
		this.usu_nome = usu_nome;
	}
	
	
	
}