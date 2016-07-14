package com.agendaconsulta.model.cep;

import java.io.Serializable;

import javax.persistence.*;
/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "Cidade")

public class Cidade implements Serializable   {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CID_CODIGO")
	private long cid_codigo;
	
	@ManyToOne
	@JoinColumn(name = "UF_CODIGO")
	private UF uf_codigo;	
	@Column(name = "CID_DESCRICAO", length=70)
	private String cid_descricao;
	@Column(name = "CID_CEP", length=8)
	private String cid_cep;

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public long getCid_codigo() {
		return cid_codigo;
	}


	public void setCid_codigo(long cid_codigo) {
		this.cid_codigo = cid_codigo;
	}


	public UF getUf_codigo() {
		return uf_codigo;
	}


	public void setUf_codigo(UF uf_codigo) {
		this.uf_codigo = uf_codigo;
	}


	public String getCid_descricao() {
		return cid_descricao;
	}


	public void setCid_descricao(String cid_descricao) {
		this.cid_descricao = cid_descricao;
	}


	public String getCid_cep() {
		return cid_cep;
	}


	public void setCid_cep(String cid_cep) {
		this.cid_cep = cid_cep;
	}

	
	
	
	
}


