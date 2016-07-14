package com.agendaconsulta.model;

import java.io.Serializable;
import java.sql.Time;



import javax.persistence.*;
/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "ATENDENTE")

public class Atendente implements Serializable  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ATE_CODIGO")
	private long ate_codigo;
	@ManyToOne
	@JoinColumn(name="CON_CODIGO")
	private Consultorio con_codigo;
	@ManyToOne
	@JoinColumn(name="TAT_CODIGO")
	private TipoAtendimento tat_codigo;
	@Column(name = "ATE_NUMERO", length=8)
	private String ate_numero;
	@Column(name = "ATE_TITULO", length=30)
	private String ate_titulo;
	@Column(name = "ATE_NOME", length=40)
	private String ate_nome;
	@Column(name = "ATE_DURACAO")
	private Time ate_duracao;
	@Column(name = "ATE_ALMOCO")
	private Time ate_almoco;
	@Column(name = "ATE_SEGUNDA_INI")
	private Time ate_segunda_ini;
	@Column(name = "ATE_TERCA_INI")
	private Time ate_terca_ini;
	@Column(name = "ATE_QUARTA_INI")
	private Time ate_quarta_ini;
	@Column(name = "ATE_QUINTA_INI")
	private Time ate_quinta_ini;
	@Column(name = "ATE_SEXTA_INI")
	private Time ate_sexta_ini;
	@Column(name = "ATE_SABADO_INI")
	private Time ate_sabado_ini;
	@Column(name = "ATE_DOMINGO_INI")
	private Time ate_domingo_ini;
	
	@Column(name = "ATE_ALMOCO_FIM")
	private Time ate_almoco_fim;
	@Column(name = "ATE_SEGUNDA_FIM")
	private Time ate_segunda_fim;
	@Column(name = "ATE_TERCA_FIM")
	private Time ate_terca_fim;
	@Column(name = "ATE_QUARTA_FIM")
	private Time ate_quarta_fim;
	@Column(name = "ATE_QUINTA_FIM")
	private Time ate_quinta_fim;
	@Column(name = "ATE_SEXTA_FIM")
	private Time ate_sexta_fim;
	@Column(name = "ATE_SABADO_FIM")
	private Time ate_sabado_fim;
	@Column(name = "ATE_DOMINGO_FIM")
	private Time ate_domingo_fim;
	
	
	public long getAte_codigo() {
		return ate_codigo;
	}
	public void setAte_codigo(long ate_codigo) {
		this.ate_codigo = ate_codigo;
	}
	public Consultorio getCon_codigo() {
		return con_codigo;
	}
	public void setCon_codigo(Consultorio con_codigo) {
		this.con_codigo = con_codigo;
	}
	public TipoAtendimento getTat_codigo() {
		return tat_codigo;
	}
	public void setTat_codigo(TipoAtendimento tat_codigo) {
		this.tat_codigo = tat_codigo;
	}
	public String getAte_numero() {
		return ate_numero;
	}
	public void setAte_numero(String ate_numero) {
		this.ate_numero = ate_numero;
	}
	public String getAte_titulo() {
		return ate_titulo;
	}
	public void setAte_titulo(String ate_titulo) {
		this.ate_titulo = ate_titulo;
	}
	public String getAte_nome() {
		return ate_nome;
	}
	public void setAte_nome(String ate_nome) {
		this.ate_nome = ate_nome;
	}
	public Time getAte_duracao() {
		return ate_duracao;
	}
	public void setAte_duracao(Time ate_duracao) {
		this.ate_duracao = ate_duracao;
	}
	public Time getAte_almoco() {
		return ate_almoco;
	}
	public void setAte_almoco(Time ate_almoco) {
		this.ate_almoco = ate_almoco;
	}
	public Time getAte_segunda_ini() {
		return ate_segunda_ini;
	}
	public void setAte_segunda_ini(Time ate_segunda_ini) {
		this.ate_segunda_ini = ate_segunda_ini;
	}
	public Time getAte_terca_ini() {
		return ate_terca_ini;
	}
	public void setAte_terca_ini(Time ate_terca_ini) {
		this.ate_terca_ini = ate_terca_ini;
	}
	public Time getAte_quarta_ini() {
		return ate_quarta_ini;
	}
	public void setAte_quarta_ini(Time ate_quarta_ini) {
		this.ate_quarta_ini = ate_quarta_ini;
	}
	public Time getAte_quinta_ini() {
		return ate_quinta_ini;
	}
	public void setAte_quinta_ini(Time ate_quinta_ini) {
		this.ate_quinta_ini = ate_quinta_ini;
	}
	public Time getAte_sexta_ini() {
		return ate_sexta_ini;
	}
	public void setAte_sexta_ini(Time ate_sexta_ini) {
		this.ate_sexta_ini = ate_sexta_ini;
	}
	public Time getAte_sabado_ini() {
		return ate_sabado_ini;
	}
	public void setAte_sabado_ini(Time ate_sabado_ini) {
		this.ate_sabado_ini = ate_sabado_ini;
	}
	public Time getAte_domingo_ini() {
		return ate_domingo_ini;
	}
	public void setAte_domingo_ini(Time ate_domingo_ini) {
		this.ate_domingo_ini = ate_domingo_ini;
	}
	
	
	
	
	public Time getAte_almoco_fim() {
		return ate_almoco_fim;
	}
	public void setAte_almoco_fim(Time ate_almoco_fim) {
		this.ate_almoco_fim = ate_almoco_fim;
	}
	public Time getAte_segunda_fim() {
		return ate_segunda_fim;
	}
	public void setAte_segunda_fim(Time ate_segunda_fim) {
		this.ate_segunda_fim = ate_segunda_fim;
	}
	public Time getAte_terca_fim() {
		return ate_terca_fim;
	}
	public void setAte_terca_fim(Time ate_terca_fim) {
		this.ate_terca_fim = ate_terca_fim;
	}
	public Time getAte_quarta_fim() {
		return ate_quarta_fim;
	}
	public void setAte_quarta_fim(Time ate_quarta_fim) {
		this.ate_quarta_fim = ate_quarta_fim;
	}
	public Time getAte_quinta_fim() {
		return ate_quinta_fim;
	}
	public void setAte_quinta_fim(Time ate_quinta_fim) {
		this.ate_quinta_fim = ate_quinta_fim;
	}
	public Time getAte_sexta_fim() {
		return ate_sexta_fim;
	}
	public void setAte_sexta_fim(Time ate_sexta_fim) {
		this.ate_sexta_fim = ate_sexta_fim;
	}
	public Time getAte_sabado_fim() {
		return ate_sabado_fim;
	}
	public void setAte_sabado_fim(Time ate_sabado_fim) {
		this.ate_sabado_fim = ate_sabado_fim;
	}
	public Time getAte_domingo_fim() {
		return ate_domingo_fim;
	}
	public void setAte_domingo_fim(Time ate_domingo_fim) {
		this.ate_domingo_fim = ate_domingo_fim;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
	
	
}
