package com.agendaconsulta.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.agendaconsulta.model.Consultorio;
import com.agendaconsulta.model.TipoAtendimento;
import com.agendaconsulta.model.Usuario;
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
	
	@SuppressWarnings("unchecked")
	public List<TipoAtendimento> listPorTipo(int tat_tipo) {
		System.out.println("listano Tipo :" + tat_tipo);
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(TipoAtendimento.class);
			cr.add(Restrictions.eq("tat_tipo", tat_tipo));
		    //cr.addOrder(Order.asc("tat_tipo"));
			
			System.out.println("Vai Abrir por Tipo ");
			return cr.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public TipoAtendimento BuscarPorCodigo(Long tat_codigo){
		session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria cr = session.createCriteria(TipoAtendimento.class);
			cr.add(Restrictions.eq("tat_codigo", tat_codigo));
			System.out.println("Vai Abrir TipoAtendimento por Codigo ");
			TipoAtendimento tipoAtendimento = (TipoAtendimento) cr.uniqueResult();
			return tipoAtendimento;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	

}
