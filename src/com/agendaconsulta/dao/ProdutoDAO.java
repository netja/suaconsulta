package com.agendaconsulta.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.agendaconsulta.model.Produto;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - Felipe
 * @since - 08/08/2012
 */
public class ProdutoDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(Produto p) {
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
	public List<Produto> listAll() {
		System.out.println("LISTANDO");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			return session.createCriteria(Produto.class, "p").list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
}