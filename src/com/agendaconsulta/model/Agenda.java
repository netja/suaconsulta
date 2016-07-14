package com.agendaconsulta.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "AGENDA")
public class Agenda {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AGE_CODIGO")
	private long age_codigo;
	@ManyToOne
	@JoinColumn(name="ATE_CODIGO")
	private Atendente ate_codigo;
	@Column(name = "AGE_DATA")
	private Date age_data;
	@Column(name = "AGE_DATAHORA")
	private Timestamp age_datahora;
	@Column(name = "AGE_DURACAO")
	private Timestamp age_duracao;	
	@Column(name = "AGE_OBSERVACOES", length=200)
	private String age_observacoes;
	@Column(name = "AGE_STATUS")
	private int age_status;  //1=Aberto |2=Fechado |3=Cancelado |4=reagenda |5=faltou
	private Date age_data1;
	@ManyToOne
	@JoinColumn(name="usu_codigo")
	private Usuario usu_codigo;
	
	
	
	
	public long getAge_codigo() {
		return age_codigo;
	}
	public void setAge_codigo(long age_codigo) {
		this.age_codigo = age_codigo;
	}
	public Atendente getAte_codigo() {
		return ate_codigo;
	}
	public void setAte_codigo(Atendente ate_codigo) {
		this.ate_codigo = ate_codigo;
	}
	
	public Date getAge_data() {
		return age_data;
	}
	public void setAge_data(Date age_data) {
		this.age_data = age_data;
	}
	public Timestamp getAge_duracao() {
		return age_duracao;
	}
	public void setAge_duracao(Timestamp age_duracao) {
		this.age_duracao = age_duracao;
	}
	public Usuario getUsu_codigo() {
		return usu_codigo;
	}
	public void setUsu_codigo(Usuario usu_codigo) {
		this.usu_codigo = usu_codigo;
	}
	public String getAge_observacoes() {
		return age_observacoes;
	}
	public void setAge_observacoes(String age_observacoes) {
		this.age_observacoes = age_observacoes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Timestamp getAge_datahora() {
		return age_datahora;
	}
	public void setAge_datahora(Timestamp age_datahora) {
		this.age_datahora = age_datahora;
	}
	public int getAge_status() {
		return age_status;
	}
	public void setAge_status(int age_status) {
		this.age_status = age_status;
	}
	public Date getAge_data1() {
		return age_data1;
	}
	public void setAge_data1(Date age_data1) {
		this.age_data1 = age_data1;
	}
	

	
}
