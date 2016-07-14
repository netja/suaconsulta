package com.agendaconsulta.model;

import java.sql.Time;

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
@Table(name = "ATEHORARIO")
public class AteHorario {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HOR_CODIGO")
	private long hor_codigo;
	@ManyToOne
	@JoinColumn(name="ATE_CODIGO")
	private Atendente  ate_codigo;
	@ManyToOne
	@JoinColumn(name="CON_CODIGO")
	private Consultorio con_codigo;
	@Column(name = "HOR_DIASEMANA", length=1)
	private int hor_diasemana;
	
	@Column(name = "HOR_DURACAO")
	private Time hor_duracao;
	@Column(name = "HOR_INICIO")
	private Time hor_inicio;
	@Column(name = "HOR_FIM")
	private Time hor_fim;
	public long getHor_codigo() {
		return hor_codigo;
	}
	public void setHor_codigo(long hor_codigo) {
		this.hor_codigo = hor_codigo;
	}
	public Atendente getAte_codigo() {
		return ate_codigo;
	}
	public void setAte_codigo(Atendente ate_codigo) {
		this.ate_codigo = ate_codigo;
	}
	public Consultorio getCon_codigo() {
		return con_codigo;
	}
	public void setCon_codigo(Consultorio con_codigo) {
		this.con_codigo = con_codigo;
	}
	public int getHor_semana() {
		return hor_diasemana;
	}
	public void setHor_diasemana(int hor_diasemana) {
		this.hor_diasemana = hor_diasemana;
	}
	public Time getHor_duracao() {
		return hor_duracao;
	}
	public void setHor_duracao(Time hor_duracao) {
		this.hor_duracao = hor_duracao;
	}
	public Time getHor_inicio() {
		return hor_inicio;
	}
	public void setHor_inicio(Time hor_inicio) {
		this.hor_inicio = hor_inicio;
	}
	public Time getHor_fim() {
		return hor_fim;
	}
	public void setHor_fim(Time hor_fim) {
		this.hor_fim = hor_fim;
	}
	
	
	

}
