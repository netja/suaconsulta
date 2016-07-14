package com.agendaconsulta.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agendaconsulta.model.Foto;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
public class FotoDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(Foto f) {
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			session.getTransaction().begin();
			session.save(f);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Foto> listByProdutos(long ID) {
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			return session.createCriteria(Foto.class, "f")
					.createAlias("produto", "p")
					.add(Restrictions.eq("p.id", ID)).list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
}