package com.agendaconsulta.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.engine.RowSelection;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;












import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.agendaconsulta.dao.AgendaDAO;
import com.agendaconsulta.dao.AtendenteDAO;
import com.agendaconsulta.dao.UsuarioDAO;
import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.Atendente;
import com.agendaconsulta.model.Consultorio;
import com.agendaconsulta.model.Pessoa;
import com.agendaconsulta.model.Usuario;
import com.agendaconsulta.util.CareFunctions;

/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@ManagedBean(name = "mbAgenda")
@SessionScoped
//@ViewScoped
//@RequestScoped
public class AgendaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Atendente> lstAtendente;
	private List<Agenda> lstAgenda;
	private List<Agenda> lstAgendadodia;
	private Agenda agenda = new Agenda();
	private Agenda agendaDataselecionada = new Agenda();
	private Agenda agendaDatahoraselecionada = new Agenda();
	private Atendente atendenteselecionado = new Atendente();
	private AgendaDAO agendaDAO;
	private AtendenteDAO atendenteDAO = new AtendenteDAO();
	boolean booSelecionouDataHora = false;
	boolean booIdentifiquese = false;
	boolean booSelecionouUsuario = false;
	boolean booCadastrandose = false;
	boolean booSucesso = false;
	private String strCelular;
	private Usuario usuario;
	private Pessoa pessoa;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private boolean booEscolherAtendente;
	
	private Consultorio consultorioSelecionado;
	
	
	public AgendaBean() {
		agendaDAO = new AgendaDAO();
	}
	
	public void abrirAtendente(){
		System.out.println("Vai abrir a Atendente"); 
		lstAtendente = atendenteDAO.listPorConsultorio(consultorioSelecionado);
		if (lstAgenda != null)
			lstAgenda.clear();
		if (lstAgendadodia != null)
			lstAgendadodia.clear();
				
        System.out.println("Abriu "+ lstAtendente.size() );//
		
		FacesMessage msg = new FacesMessage("Abrir Agenda ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void viewAgenda(Consultorio consultorioSelecionado) {
		this.consultorioSelecionado = consultorioSelecionado;
		System.out.println(consultorioSelecionado.getCon_nome());
	 	
		setBooEscolherAtendente(true);
		setBooSelecionouDataHora(false);
		setBooCadastrandose(false);
		setBooSelecionouUsuario(false);
		setStrCelular("");
		abrirAtendente();
				 
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 600); 
        RequestContext.getCurrentInstance().openDialog("agenda", options, null);
        
	}
	
	public void marcaAgenda(Agenda agendaSelecionada) {
		viewAgenda(agendaSelecionada.getAte_codigo().getCon_codigo());
		
		/*
		setConsultorioSelecionado(agendaSelecionada.getAte_codigo().getCon_codigo());
		atendenteselecionado = agendaSelecionada.getAte_codigo();
		agendaDataselecionada = agendaSelecionada;
		agendaDatahoraselecionada = agendaSelecionada;
		setBooEscolherAtendente(false);
		
		setBooSelecionouDataHora(true);	 	
		setBooIdentifiquese(true);  
		
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 620); 
        RequestContext.getCurrentInstance().openDialog("agenda", options, null);
        */
	}
	
	public void closeAgenda() {	 	
		setBooSelecionouDataHora (false);
		setBooSelecionouUsuario(false);
		setBooIdentifiquese(false);
		setBooSucesso(false);
		RequestContext.getCurrentInstance().closeDialog(0);
	}
	
	public void selecionarHorario() {
		setBooSelecionouDataHora(!isBooSelecionouDataHora());
		setBooIdentifiquese(isBooSelecionouDataHora());
	}
	
	public void salvarAgenda(boolean booCadastarUsuario){
		try{
			if (booCadastarUsuario){
				usuario.setUsu_celular(CareFunctions.limpaStrFone(usuario.getUsu_celular()));
				usuarioDAO.save(usuario);
			}
			
			if (usuario != null){
				agendaDatahoraselecionada.setUsu_codigo(usuario);
				agendaDatahoraselecionada.setAge_status(1);
				agendaDAO.save(agendaDatahoraselecionada);
				//RequestContext.getCurrentInstance().closeDialog(0);
				setBooSucesso(true);
				setBooSelecionouUsuario(false);
				listarHorario();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Agendamento Concluído coom Sucesso!", "Agendamento Concluído"));
		}
	}
	
	public void fecharAgenda(){		
		//usuario = null;
		//agendaDatahoraselecionada = null;
		setBooSelecionouDataHora (false);
		setBooSelecionouUsuario(false);
		setBooIdentifiquese(false);
		setBooSucesso(false);
		setStrCelular("");
		RequestContext.getCurrentInstance().closeDialog(0);
		this.refresh();
	}
	
	public void refresh() {
		System.out.println("Chamou Refresh");
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
	}
	public void abrirAgenda(){
		Date dtIni = new Date();
		Date dtFim = new Date();
		dtIni.setDate(-5000);
		dtFim.setDate(5000);
		System.out.println("Vai abrir a Agenda "); 
		lstAgenda = agendaDAO.listPorAtendente(atendenteselecionado, dtIni, dtFim);
		if (lstAgendadodia != null)
			lstAgendadodia.clear();
				
        System.out.println("Abriu "+ lstAgenda.size() );//
		
		FacesMessage msg = new FacesMessage("Abrir Agenda ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	
	
	public void setSelected(Agenda agenda) {
        FacesMessage msg = new FacesMessage("Data selecionada!", "Data : "+ agenda.getAge_data());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	  
	public Agenda getagendaDataselecionada() {
		 System.out.println("get Agenda Sele99 ");
		return agendaDataselecionada;
	}
	
	public void setagendaDataselecionada(Agenda agendaDataselecionada) {
		System.out.println("set Agenda Sele1 " + agendaDataselecionada.getAge_data1());
	}
	
	
	
	public Atendente getAtendenteselecionado() {
		return atendenteselecionado;
	}

	public void setAtendenteselecionado(Atendente atendenteselecionado) {
		this.atendenteselecionado = atendenteselecionado;
	}

	public void onRowSelectData(SelectEvent event) {
		  System.out.println("SelectEvent Data : " + agendaDataselecionada.getAge_data1());
		  listarHorario();
	}
	
	public void onRowSelectAtendente(SelectEvent event) {
		  System.out.println("SelectEvent Atendente : " + agendaDataselecionada.getAge_data1());
		  abrirAgenda();
	}
	
	public void listarHorario() {
		System.out.println("Vai Listar Horario "); 
		lstAgendadodia = agendaDAO.listPordia(atendenteselecionado, agendaDataselecionada.getAge_data1(), true);
				
        System.out.println("Abriu do Dia "+ lstAgendadodia.size() );//
		
		FacesMessage msg = new FacesMessage("Abrir Agenda do Dia ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}
	
	public void buscarPessoa(){
		/*strCelular = strCelular.toString().replaceAll("[- /.]", "");  
		strCelular = strCelular.toString().replaceAll("[-()]", "");*/
		strCelular = CareFunctions.limpaStrFone(strCelular);
		 System.out.println("Preparar  " + strCelular);//
		 
		if (strCelular.trim().length() > 0){
			System.out.println("Buscar  " + strCelular);//
			List<Usuario> lstusuario =  usuarioDAO.listPorcelular(strCelular);
			System.out.println("Buscou  " + lstusuario.size());
			setBooIdentifiquese(false);
			if (lstusuario.size() > 0){
				usuario = lstusuario.get(0);
				setBooSelecionouUsuario(true);
			}else{
				setBooSelecionouUsuario(false);				
				setBooCadastrandose(true);
				usuario = new Usuario();
				usuario.setUsu_celular(strCelular);
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
	 

	public List<Agenda> getLstAgenda() {
		return lstAgenda;
	}


	public void setLstAgenda(List<Agenda> lstAgenda) {
		this.lstAgenda = lstAgenda;
	}


	public Agenda getAgenda() {
		return agenda;
	}


	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}


	public AgendaDAO getAgendaDAO() {
		return agendaDAO;
	}


	public void setAgendaDAO(AgendaDAO agendaDAO) {
		this.agendaDAO = agendaDAO;
	}



	public List<Agenda> getLstAgendadodia() {
		return lstAgendadodia;
	}

	public void setLstAgendadodia(List<Agenda> lstAgendadodia) {
		this.lstAgendadodia = lstAgendadodia;
	}
	
	

	public Agenda getAgendaDataselecionada() {
		return agendaDataselecionada;
	}

	public void setAgendaDataselecionada(Agenda agendaDataselecionada) {
		this.agendaDataselecionada = agendaDataselecionada;
		System.out.println("2Agenda Sel : D:" + agendaDataselecionada.getAge_data1() +"H:" + agendaDataselecionada.getAge_datahora());
	}

	public Agenda getAgendaDatahoraselecionada() {
		return agendaDatahoraselecionada;
	}

	public void setAgendaDatahoraselecionada(Agenda agendaDatahoraselecionada) {
		this.agendaDatahoraselecionada = agendaDatahoraselecionada;
		System.out.println("1Agenda Sel : D:" + agendaDatahoraselecionada.getAge_data() +"H:" + agendaDatahoraselecionada.getAge_datahora());
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isBooSelecionouDataHora() {
		return booSelecionouDataHora;
	}

	public void setBooSelecionouDataHora(boolean booSelecionouDataHora) {
		this.booSelecionouDataHora = booSelecionouDataHora;
	}

	public String getStrCelular() {
		return strCelular;
	}

	public void setStrCelular(String strCelular) {
		this.strCelular = strCelular;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isBooSelecionouUsuario() {
		return booSelecionouUsuario;
	}

	public void setBooSelecionouUsuario(boolean booSelecionouUsuario) {
		this.booSelecionouUsuario = booSelecionouUsuario;
	}

	public boolean isBooCadastrandose() {
		return booCadastrandose;
	}

	public void setBooCadastrandose(boolean booCadastrandose) {
		this.booCadastrandose = booCadastrandose;
	}

	public boolean isBooIdentifiquese() {
		return booIdentifiquese;
	}

	public void setBooIdentifiquese(boolean booIdentifiquese) {
		this.booIdentifiquese = booIdentifiquese;
	}

	public Consultorio getConsultorioSelecionado() {
		return consultorioSelecionado;
	}

	public void setConsultorioSelecionado(Consultorio consultorioSelecionado) {
		this.consultorioSelecionado = consultorioSelecionado;
	}

	public List<Atendente> getLstAtendente() {
		return lstAtendente;
	}

	public void setLstAtendente(List<Atendente> lstAtendente) {
		this.lstAtendente = lstAtendente;
	}

	public boolean isBooSucesso() {
		return booSucesso;
	}

	public void setBooSucesso(boolean booSucesso) {
		this.booSucesso = booSucesso;
	}

	public boolean isBooEscolherAtendente() {
		return booEscolherAtendente;
	}

	public void setBooEscolherAtendente(boolean booEscolherAtendente) {
		this.booEscolherAtendente = booEscolherAtendente;
	}

	

	
	
	
	
		
	

	
}