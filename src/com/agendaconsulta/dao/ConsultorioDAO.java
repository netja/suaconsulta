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
public class ConsultorioDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(Consultorio c) {
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			session.getTransaction().begin();
			session.saveOrUpdate(c);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Consultorio consultarPorCNPJ(String strCNPJ){
		System.out.println("LISTANDO Por CNPJ");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Consultorio.class);
			cr.add(Restrictions.eq("con_cnpjcpf", strCNPJ));
		   // cr.addOrder(Order.asc("usu_nome"));
			
			System.out.println("Vai Abrir por CNPJ ");
			//List<Consultorio> consultorios = cr.list();
			//Consultorio consultorio = consultorios.get(0);
			Consultorio consultorio = (Consultorio) cr.uniqueResult();
			return consultorio;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	
	
}