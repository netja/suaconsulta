package com.agendaconsulta.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.AteHorario;
import com.agendaconsulta.model.Atendente;
import com.agendaconsulta.model.Consultorio;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
public class AtendenteDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(Atendente a) {
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
	
	public void saveHorario(AteHorario ah) {
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			session.getTransaction().begin();
			session.saveOrUpdate(ah);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Atendente> listAll() {
		System.out.println("LISTANDO");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			return session.createCriteria(Atendente.class, "p").list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Atendente> listPorNome(String strPesquisar, String strTat_codigo) {
		System.out.println("LISTANDO Por Nome");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Atendente.class);
			cr.add(Restrictions.eq ("ate_nome", strPesquisar));
			cr.add(Restrictions.eq("tat_codigo", Long.valueOf(strTat_codigo)));
			
			System.out.println("Vai Abrir por nome ");
			return cr.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Atendente> listPorCodigo(String strPesquisar, String strTat_codigo) {
		System.out.println("LISTANDO Por Codigo");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			//return session.createCriteria(Atendente.class, "p").list();
			Criteria cr = session.createCriteria(Atendente.class);
			cr.add(Restrictions.eq("ate_numero", strPesquisar));
			cr.add(Restrictions.eq("tat_codigo", Long.valueOf(strTat_codigo)));
			
			System.out.println("Vai Abrir por codigo");
			return cr.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public List<Atendente> listPorConsultorio(Consultorio consultorioSelecionado) {
		System.out.println("LISTANDO Por con_codigo");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			//return session.createCriteria(Atendente.class, "p").list();
			Criteria cr = session.createCriteria(Atendente.class);
			cr.add(Restrictions.eq("con_codigo", consultorioSelecionado));
			System.out.println("Vai Abrir por consultorioSelecionado");
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
	
	public List<Consultorio> listPorCep(String strPesquisar, String strTat_codigo) {
		System.out.println("LISTANDO Por Cep : " + strTat_codigo);
		session = HibernateUtil.getSessionfactory().openSession();
		
		if (strPesquisar.length() > 4){
			strPesquisar = strPesquisar.substring(0,4) + "%";
		}
			

		try {
			//return session.createCriteria(Atendente.class, "p").list();
			/*Criteria cr = session.createCriteria(Consultorio.class);
			cr.add(Restrictions.like("con_cep", strPesquisar));
			cr.add(Restrictions.eq("tat_codigo", Long.valueOf(strTat_codigo)));
			System.out.println("Vai Abrir por Cep");
			return cr.list();*/
			
			
			long itat_codigo = Long.parseLong(String.valueOf(strTat_codigo));
			
			//Query  query  = session.createQuery("from Atendente a WHERE a.con_codigo.con_cep=:con_cep AND a.tat_codigo=:tat_codigo)");
			Query  query  = session.createQuery("from Consultorio c WHERE c.con_codigo IN (SELECT a.con_codigo FROM Atendente a WHERE a.con_codigo.con_cep LIKE :con_cep AND a.tat_codigo.tat_codigo = :tat_codigo)");//
			query.setParameter("con_cep", strPesquisar);
			query.setParameter("tat_codigo", itat_codigo);
			System.out.println("Vai Abrir por Cep");
			//List<Atendente> lstAtendente =   query.list();
			
			/*List<Atendente> lstAtendente =  session.createCriteria(Atendente.class)
				    .add( Restrictions.eq("tat_codigo", Long.valueOf(strTat_codigo)))
				   /* .createCriteria("Consultorio")
				        .add( Restrictions.like("con_cep", strPesquisar))*/
				    //.list();
			  return   query.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	public boolean abrirPorLocaliza(String strLat, String strLong) {
		System.out.println("LISTANDO Por Localizacao : " + strLat);
		session = HibernateUtil.getSessionfactory().openSession();
		boolean retorno = false;
		try {
			//return session.createCriteria(Atendente.class, "p").list();
			Criteria cr = session.createCriteria(Consultorio.class);
			cr.add(Restrictions.eq("con_latitude", strLat));
			cr.add(Restrictions.eq("con_longitude", strLong));
			
			System.out.println("Vai Abrir por Localizacao");
			if (cr.list().size() > 0)
				retorno = true;
			
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return retorno;
	}
	
	
	public List<AteHorario> listHorarioPorAtendent(Atendente atendente, int intDiaSemana) {
		System.out.println("LISTANDO Horario por atendente :" + atendente.getAte_nome() + " Dia :" + intDiaSemana);
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			//return session.createCriteria(Atendente.class, "p").list();
			Criteria cr = session.createCriteria(AteHorario.class);
			cr.add(Restrictions.eq("ate_codigo", atendente));
			cr.add(Restrictions.eq("hor_diasemana", intDiaSemana));
			System.out.println("Vai Abrir Horario por atendente");
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
	
	public static void main(String [] ags){
		
	}
	
}