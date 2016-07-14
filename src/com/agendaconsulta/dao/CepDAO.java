/** Produtos - com.wordpress.dao */
package com.agendaconsulta.dao;

import java.io.Serializable;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agendaconsulta.model.Usuario;
import com.agendaconsulta.model.cep.Endereco;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
public class CepDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	

	@SuppressWarnings("unchecked")
	public Endereco buscaPorcep(String strCEP){
		System.out.println("LISTANDO Por Cp");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Endereco.class);
			cr.add(Restrictions.eq("end_cep", strCEP));
		     System.out.println("Vai Abrir por CEP ");
			return (Endereco) cr.uniqueResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Endereco> buscaPorEnderecoBairro(String strEnderecoBairro){
		System.out.println("LISTANDO Por Cp");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			//return session.createCriteria(Atendente.class, "p").list();
			Criteria cr = session.createCriteria(Endereco.class);
			cr.add(Restrictions.like("end_descricao", "%" + strEnderecoBairro + "%"));
		     System.out.println("Vai Abrir por CEP ");
			 return cr.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
}