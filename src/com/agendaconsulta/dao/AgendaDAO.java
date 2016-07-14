/** Produtos - com.wordpress.dao */
package com.agendaconsulta.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.Atendente;
import com.agendaconsulta.model.Usuario;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
public class AgendaDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(Agenda a) {
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			session.getTransaction().begin();
			session.saveOrUpdate(a);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void save(ArrayList<Agenda> agendas) {
		
		for (Agenda a :agendas){
			save(a);
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Agenda> listAll() {
		System.out.println("LISTANDO");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			return session.createCriteria(Agenda.class, "p").list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Agenda> listPorNome(String strPesquisar) {
		System.out.println("LISTANDO Por Nome");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Agenda.class);
			cr.add(Restrictions.eq("ate_nome", strPesquisar));
			
			System.out.println("Vai Abrir por nome ");
			return cr.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Agenda> listPorAtendente(Atendente atendente, Date dtIni, Date dtFim) {
		System.out.println("LISTANDO Por Atendente");
		
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Agenda.class)
					.add(Restrictions.isNull("usu_codigo"))
					.add(Restrictions.eq("ate_codigo", atendente))
					.add(Restrictions.between("age_data", dtIni, dtFim))
					.setProjection(Projections.projectionList()
							//.add( Projections.groupProperty("ate_codigo"), "ate_codigo")
		                    .add( Projections.groupProperty("age_data"), "age_data1")	
		            ).addOrder(Order.asc("age_data"));
			cr.setResultTransformer(Transformers.aliasToBean(Agenda.class));
			
			System.out.println("Vai Abrir por Atendente");
			List teste = cr.list();
			System.out.println("Abriu " + teste.size());
			return teste;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Agenda> listPordia(Atendente atendente, Date Dataselecionada, boolean booApenasAberta) {
		System.out.println("LISTANDO Por Atendente :"+ atendente.getAte_codigo() +" e data : "+ Dataselecionada);
		session = HibernateUtil.getSessionfactory().openSession();
		try {
			System.out.println("Vai Abrir por Atendente");
			Criteria crit = session.createCriteria(Agenda.class);
			  crit.add(Restrictions.eq("ate_codigo", atendente));
			  crit.add(Restrictions.eq("age_data", Dataselecionada));
			  Integer[] iStatus = {0,1,2};
			  crit.add(Restrictions.in("age_status", iStatus));
			  if (booApenasAberta){
				  crit.add(Restrictions.isNull("usu_codigo"));
			  }
			  crit.addOrder(Order.asc("age_datahora"));
			
		    List lstAgenda =  crit.list();
			System.out.println("Abriu " + lstAgenda.size());
			return lstAgenda;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	
	public List<Agenda> listPorUsuario(Usuario usuario, boolean booConsultaAberta) {
		System.out.println("LISTANDO Por Nome");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Agenda.class);
			cr.add(Restrictions.eq("usu_codigo", usuario));
			if (booConsultaAberta)
				cr.add(Restrictions.eq("age_status", 1));//1=Aberta
			else{
				cr.add(Restrictions.eq("age_status", 2));//2=Fechado
				cr.setProjection(Projections.projectionList()
						.add( Projections.groupProperty("ate_codigo"), "ate_codigo"));
				cr.setResultTransformer(Transformers.aliasToBean(Agenda.class));
			}
			
			System.out.println("Vai Abrir por nome ");
			return cr.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Agenda> listPorUsuarioXAtendente(Usuario usuario, Atendente atendente) {
		System.out.println("LISTANDO Por Nome");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Agenda.class);
			cr.add(Restrictions.eq("usu_codigo", usuario));
			cr.add(Restrictions.eq("ate_codigo", atendente));
			cr.add(Restrictions.eq("age_status", 2));//2=Fechado
			
			System.out.println("Vai Abrir por nome ");
			return cr.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	
	
}