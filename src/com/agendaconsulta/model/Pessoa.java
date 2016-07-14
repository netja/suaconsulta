package com.agendaconsulta.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "PESSOA")
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PES_CODIGO")
	private long pes_codigo;
		
	@Column(name = "PES_NOME", length=50)
	private String pes_nome;

	public long getPes_codigo() {
		return pes_codigo;
	}

	public void setPes_codigo(long pes_codigo) {
		this.pes_codigo = pes_codigo;
	}

	public String getPes_nome() {
		return pes_nome;
	}

	public void setPes_nome(String pes_nome) {
		this.pes_nome = pes_nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
}