package com.agendaconsulta.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.agendaconsulta.model.TipoAtendimento;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
public class TipoAtendimentoDAO {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(TipoAtendimento p) {
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			session.getTransaction().begin();
			session.save(p);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipoAtendimento> listAll() {
		System.out.println("listano Tipo1");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			return session.createCriteria(TipoAtendimento.class, "p").list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	

}
