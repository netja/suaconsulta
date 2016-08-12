package com.agendaconsulta.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.*;
/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@Entity
@Table(name = "TIPOATENDIMENTO")

public class TipoAtendimento implements Serializable  {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "TAT_CODIGO")
		private long tat_codigo;
		@Column(name = "TAT_DESCRICAO", length=80)
		private String tat_descricao;
		@Column(name = "TAT_ATIVO", length=1)
		private String tat_ativo;
		@Column(name = "TAT_TIPO", length=1)
		private int tat_tipo;//0=medicina||1=Odontologia||2=Psicologia||3=Fonoaudiologia||4=Fisioterapia||5=Musicoterapia||6=Nutrição||7=Terapia Ocupacional
		
		public long getTat_codigo() {
			return tat_codigo;
		}
		public void setTat_codigo(long tat_codigo) {
			this.tat_codigo = tat_codigo;
		}
		public String getTat_descricao() {
			return tat_descricao;
		}
		public void setTat_descricao(String tat_descricao) {
			this.tat_descricao = tat_descricao;
		}
		public String getTat_ativo() {
			return tat_ativo;
		}
		public void setTat_ativo(String tat_ativo) {
			this.tat_ativo = tat_ativo;
		}
		public int getTat_tipo() {
			return tat_tipo;
		}
		public void setTat_tipo(int tat_tipo) {
			this.tat_tipo = tat_tipo;
		}
		
		
}
