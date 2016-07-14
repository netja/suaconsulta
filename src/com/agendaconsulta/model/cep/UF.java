package com.agendaconsulta.model.cep;

import java.io.Serializable;

import javax.persistence.*;
/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "UF")

public class UF implements Serializable   {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UF_CODIGO")
	private long uf_codigo;
	@Column(name = "UF_SIGLA", length=2)
	private String uf_sigla;
	@Column(name = "UF_DESCRICAO", length=30)
	private String uf_descricao;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getUf_codigo() {
		return uf_codigo;
	}

	public void setUf_codigo(long uf_codigo) {
		this.uf_codigo = uf_codigo;
	}

	public String getUf_sigla() {
		return uf_sigla;
	}

	public void setUf_sigla(String uf_sigla) {
		this.uf_sigla = uf_sigla;
	}

	public String getUf_descricao() {
		return uf_descricao;
	}

	public void setUf_descricao(String uf_descricao) {
		this.uf_descricao = uf_descricao;
	}
	
	
	
}


