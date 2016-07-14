
package com.agendaconsulta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.agendaconsulta.dao.AgendaDAO;
import com.agendaconsulta.dao.UsuarioDAO;
import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.Usuario;
import com.agendaconsulta.util.CareFunctions;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@ManagedBean(name = "mbUsuario")
@SessionScoped
//@ViewScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;
	private String strCelular;
	private String strEmail;
	private AgendaDAO agendaDAO;
	
	private List<Agenda> lstAgendaMarcada;
	private List<Agenda> lstAgendaEfetuadasPorAtendente;
	private List<Agenda> lstAgendaEfetuadas;
	
	private Agenda agendaSelecionada;

	public UsuarioBean() {
		usuarioDAO = new UsuarioDAO();
		agendaDAO = new AgendaDAO();
	}
	

	
	public void editarUsuario(){		
		
		listarAgendasUsuario(usuario);
		
		 Map<String,Object> options = new HashMap<String, Object>();
	     options.put("modal", true);
	     options.put("draggable", false);
	     options.put("resizable", false);
	     options.put("contentHeight", 600); 
	     options.put("contentWidth", 900);
	     RequestContext.getCurrentInstance().openDialog("paciente", options, null);
	}
	
	public void buscarEditarUsuario(){
		strCelular = CareFunctions.limpaStrFone(strCelular);
		System.out.println("Preparar  " + strCelular);//
		 
		if (strCelular.trim().length() > 0){
			System.out.println("Buscar  " + strCelular);//
			List<Usuario> lstusuario =  usuarioDAO.listPorcelular(strCelular);
			System.out.println("Buscou  " + lstusuario.size());
			
			if (lstusuario.size() > 0){
				usuario = lstusuario.get(0);
				
				editarUsuario();
			}else{
				usuario = new Usuario();
			}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Ahoe", "Bem Vindo"));	
		}else{
			System.out.println("Buscar  " + strCelular);//
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Você deve informar o Celular", "Não foi possível pesquisar"));			
		}
		
	}
	
	private void listarAgendasUsuario(Usuario usuario){
		System.out.println("Vai abrir a Agenda "); 
		lstAgendaMarcada = agendaDAO.listPorUsuario(usuario, true);
		
		lstAgendaEfetuadasPorAtendente = agendaDAO.listPorUsuario(usuario, false);
	
		
		FacesMessage msg = new FacesMessage("Abrir Agenda ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}
	
	public void cancelarAgenda(){
		System.out.println("cancelar" + agendaSelecionada.getAge_datahora() );
		agendaSelecionada.setUsu_codigo(null);
		agendaSelecionada.setAge_status(0);
		agendaDAO.save(agendaSelecionada);
		lstAgendaEfetuadasPorAtendente.remove(agendaSelecionada);
	}


	public void onRowSelectAgendaRealizasa(SelectEvent event) {
		  System.out.println("SelectEvent Data : " + agendaSelecionada.getAte_codigo());
		  lstAgendaEfetuadas = agendaDAO.listPorUsuarioXAtendente(usuario, agendaSelecionada.getAte_codigo());
	}

	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}



	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getStrCelular() {
		return strCelular;
	}



	public void setStrCelular(String strCelular) {
		this.strCelular = strCelular;
	}



	public String getStrEmail() {
		return strEmail;
	}



	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}



	public List<Agenda> getLstAgendaMarcada() {
		return lstAgendaMarcada;
	}



	public void setLstAgendaMarcada(List<Agenda> lstAgendaMarcada) {
		this.lstAgendaMarcada = lstAgendaMarcada;
	}



	



	public Agenda getAgendaSelecionada() {
		return agendaSelecionada;
	}



	public void setAgendaSelecionada(Agenda agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
	}



	public List<Agenda> getLstAgendaEfetuadasPorAtendente() {
		return lstAgendaEfetuadasPorAtendente;
	}



	public void setLstAgendaEfetuadasPorAtendente(
			List<Agenda> lstAgendaEfetuadasPorAtendente) {
		this.lstAgendaEfetuadasPorAtendente = lstAgendaEfetuadasPorAtendente;
	}



	public List<Agenda> getLstAgendaEfetuadas() {
		return lstAgendaEfetuadas;
	}



	public void setLstAgendaEfetuadas(List<Agenda> lstAgendaEfetuadas) {
		this.lstAgendaEfetuadas = lstAgendaEfetuadas;
	}
	

	
		
	
	
	
}