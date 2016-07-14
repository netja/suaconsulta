package com.agendaconsulta.model.cep;


import java.io.Serializable;

import javax.persistence.*;
/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "Bairro")

public class Bairro implements Serializable   {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BAR_CODIGO")
	private long bar_codigo;
	
	@ManyToOne
	@JoinColumn(name = "UF_CODIGO")
	private UF uf_codigo;
	
	@ManyToOne
	@JoinColumn(name = "CID_CODIGO")
	private Cidade cid_codigo;
	
	@Column(name = "BAR_DESCRICAO", length=70)
	private String bar_descricao;

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public long getBar_codigo() {
		return bar_codigo;
	}


	public void setBar_codigo(long bar_codigo) {
		this.bar_codigo = bar_codigo;
	}


	public UF getUf_codigo() {
		return uf_codigo;
	}


	public void setUf_codigo(UF uf_codigo) {
		this.uf_codigo = uf_codigo;
	}


	public Cidade getCid_codigo() {
		return cid_codigo;
	}


	public void setCid_codigo(Cidade cid_codigo) {
		this.cid_codigo = cid_codigo;
	}


	public String getBar_descricao() {
		return bar_descricao;
	}


	public void setBar_descricao(String bar_descricao) {
		this.bar_descricao = bar_descricao;
	}

	
	
	
	
}


