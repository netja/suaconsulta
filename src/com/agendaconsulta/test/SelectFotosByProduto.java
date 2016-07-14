package com.agendaconsulta.test;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.agendaconsulta.model.Foto;
import com.agendaconsulta.util.HibernateUtil;

/**
 * @author - 
 * @since - 
 */
public class SelectFotosByProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new SelectFotosByProduto().run();
	}

	@SuppressWarnings("unchecked")
	private void run() {
		Session session = HibernateUtil.getSessionfactory().openSession();

		try {
			List<Foto> fotos = session.createCriteria(Foto.class, "f")
					.createAlias("produto", "p")
					.add(Restrictions.eq("p.id", 1L)).list();

			for (Foto f : fotos) {
				System.out.println(f.getId());
				System.out.println(f.getDescricao());
				System.out.println(f.getImagem());
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
}
