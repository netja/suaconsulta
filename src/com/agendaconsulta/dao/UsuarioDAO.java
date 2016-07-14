
package com.agendaconsulta.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.agendaconsulta.model.Consultorio;
import com.agendaconsulta.model.Usuario;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
public class UsuarioDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(Usuario p) {
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
	public List<Usuario> listAll() {
		System.out.println("LISTANDO");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			return session.createCriteria(Usuario.class, "u").list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Usuario> listPorcelular(String strCelular){
		System.out.println("LISTANDO Por Celular");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Usuario.class);
			cr.add(Restrictions.eq("usu_celular", strCelular));
		   // cr.addOrder(Order.asc("usu_nome"));
			
			System.out.println("Vai Abrir por Celular ");
			return cr.list();
			//return (Usuario) cr.uniqueResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Usuario> listPorConsultorio(Consultorio consultorio){
		System.out.println("LISTANDO Por Celular");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Usuario.class);
			//cr.add(Restrictions.eq("usu_celular", strCelular));
		   // cr.addOrder(Order.asc("usu_nome"));
			
			System.out.println("Vai Abrir por Celular ");
			return cr.list();
			//return (Usuario) cr.uniqueResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	
	
	
	
	
}