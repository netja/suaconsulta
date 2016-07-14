package com.agendaconsulta.util;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.AteHorario;
import com.agendaconsulta.model.Atendente;
import com.agendaconsulta.model.Consultorio;
import com.agendaconsulta.model.Foto;
import com.agendaconsulta.model.Pessoa;
import com.agendaconsulta.model.Produto;
import com.agendaconsulta.model.TipoAtendimento;
import com.agendaconsulta.model.Usuario;
import com.agendaconsulta.model.cep.Bairro;
import com.agendaconsulta.model.cep.Cidade;
import com.agendaconsulta.model.cep.Endereco;
import com.agendaconsulta.model.cep.UF;

/**
 * @author - Alexandre
 * @since - 01/01/2015
 */
public class HibernateUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new AnnotationConfiguration().configure()
					.addPackage("com.wordpress.model")
					.addAnnotatedClass(TipoAtendimento.class)
					.addAnnotatedClass(Atendente.class)
					.addAnnotatedClass(Agenda.class)
					.addAnnotatedClass(Usuario.class)
					.addAnnotatedClass(Pessoa.class)
					.addAnnotatedClass(Consultorio.class)
					.addAnnotatedClass(UF.class)
					.addAnnotatedClass(Cidade.class)
					.addAnnotatedClass(Bairro.class)
					.addAnnotatedClass(Endereco.class)
					.addAnnotatedClass(AteHorario.class)
					    .buildSessionFactory();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
}