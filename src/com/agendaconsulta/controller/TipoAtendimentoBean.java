package com.agendaconsulta.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


import org.primefaces.event.FileUploadEvent;

import com.agendaconsulta.dao.TipoAtendimentoDAO;
import com.agendaconsulta.model.TipoAtendimento;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@ManagedBean(name = "mbTipoAtendimento")
@SessionScoped
public class TipoAtendimentoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<TipoAtendimento> tipoAtendimentos;
	private TipoAtendimento tipoAtendimento = new TipoAtendimento();
	
	private TipoAtendimentoDAO tipoAtendimentoDAO;
		
	

	public TipoAtendimentoBean() {
		
		tipoAtendimentoDAO = new TipoAtendimentoDAO();
		tipoAtendimentos = tipoAtendimentoDAO.listAll();
    }

	public void salvaTipoAtendimento_bkp() {
		try {
			System.out.println("teste");
			tipoAtendimentoDAO.save(tipoAtendimento);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			tipoAtendimento = new TipoAtendimento();
			tipoAtendimentos = tipoAtendimentoDAO.listAll();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"TipoAtendimento adicionado", "TipoAtendimento adicionado"));
		}
	}
	
	
	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public List<TipoAtendimento> getTipoAtendimentos() {
		return tipoAtendimentos;
	}

	
       
    
    
	
	
	 
	
}