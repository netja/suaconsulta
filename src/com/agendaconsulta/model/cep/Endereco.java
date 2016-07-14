package com.agendaconsulta.model.cep;

import java.io.Serializable;

import javax.persistence.*;
/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "Endereco")

public class Endereco implements Serializable   {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "END_CODIGO")
	private long end_codigo;
	
	@ManyToOne
	@JoinColumn(name = "UF_CODIGO")
	private UF uf_codigo;
	
	@ManyToOne
	@JoinColumn(name = "CID_CODIGO")
	private Cidade cid_codigo;
	
	@ManyToOne
	@JoinColumn(name = "BAR_CODIGO")
	private Bairro bar_codigo;
	
	
	@Column(name = "END_DESCRICAO", length=100)
	private String end_descricao;
	@Column(name = "END_COMPLEMENTO", length=72)
	private String end_complemento;

	@Column(name = "END_CEP", length=8)
	private String end_cep;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public long getEnd_codigo() {
		return end_codigo;
	}


	public void setEnd_codigo(long end_codigo) {
		this.end_codigo = end_codigo;
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


	public Bairro getBar_codigo() {
		return bar_codigo;
	}


	public void setBar_codigo(Bairro bar_codigo) {
		this.bar_codigo = bar_codigo;
	}


	public String getEnd_descricao() {
		return end_descricao;
	}


	public void setEnd_descricao(String end_descricao) {
		this.end_descricao = end_descricao;
	}


	public String getEnd_complemento() {
		return end_complemento;
	}


	public void setEnd_complemento(String end_complemento) {
		this.end_complemento = end_complemento;
	}

	
	
	
	
}


