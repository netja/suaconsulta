package com.agendaconsulta.model;

import java.io.Serializable;

import javax.persistence.*;

import com.agendaconsulta.model.cep.Endereco;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "CONSULTORIO")

public class Consultorio implements Serializable   {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CON_CODIGO")
	private long con_codigo;
	@Column(name = "CON_NUMERO")
	private long con_numero;
	@Column(name = "CON_CNPJCPF", length=18)
	private String con_cnpjcpf;
	@Column(name = "CON_CEP", length=8)
	private String con_cep;
	@Column(name = "CON_ENDNUMERO", length=10)
	private int con_endnumero;
	@Column(name = "CON_ENDCOMPL", length=50)
	private String con_endcompl;
	@Column(name = "CON_NOME", length=70)
	private String con_nome;
	@Column(name = "CON_EMAIL", length=70)
	private String con_email;
	@Column(name = "CON_TELEFONE", length=11)
	private String con_telefone;
	@Column(name = "CON_SENHA", length=11)
	private String con_senha;
	@Column(name = "CON_LATITUDE", length=15)
	private String con_latitude;
	@Column(name = "CON_LONGITUDE", length=15)
	private String con_longitude;
	
	@ManyToOne
	@JoinColumn(name = "END_CODIGO")
	private Endereco end_codigo;
	
	
	public long getCon_codigo() {
		return con_codigo;
	}
	public void setCon_codigo(long con_codigo) {
		this.con_codigo = con_codigo;
	}
	public long getCon_numero() {
		return con_numero;
	}
	public void setCon_numero(long con_numero) {
		this.con_numero = con_numero;
	}
	public String getCon_cnpjcpf() {
		return con_cnpjcpf;
	}
	public void setCon_cnpjcpf(String con_cnpjcpf) {
		this.con_cnpjcpf = con_cnpjcpf;
	}
	public String getCon_cep() {
		return con_cep;
	}
	public void setCon_cep(String con_cep) {
		this.con_cep = con_cep;
	}
	public int getCon_endnumero() {
		return con_endnumero;
	}
	public void setCon_endnumero(int con_endnumero) {
		this.con_endnumero = con_endnumero;
	}
	public String getCon_endcompl() {
		return con_endcompl;
	}
	public void setCon_endcompl(String con_endcompl) {
		this.con_endcompl = con_endcompl;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCon_nome() {
		return con_nome;
	}
	public void setCon_nome(String con_nome) {
		this.con_nome = con_nome;
	}
	public String getCon_email() {
		return con_email;
	}
	public void setCon_email(String con_email) {
		this.con_email = con_email;
	}
	public Endereco getEnd_codigo() {
		return end_codigo;
	}
	public void setEnd_codigo(Endereco end_codigo) {
		this.end_codigo = end_codigo;
	}
	public String getCon_telefone() {
		return con_telefone;
	}
	public void setCon_telefone(String con_telefone) {
		this.con_telefone = con_telefone;
	}
	public String getCon_senha() {
		return con_senha;
	}
	public void setCon_senha(String con_senha) {
		this.con_senha = con_senha;
	}
	public String getCon_latitude() {
		return con_latitude;
	}
	public void setCon_latitude(String con_latitude) {
		this.con_latitude = con_latitude;
	}
	public String getCon_longitude() {
		return con_longitude;
	}
	public void setCon_longitude(String con_longitude) {
		this.con_longitude = con_longitude;
	}
	
	
}
