package com.agendaconsulta.controller;


import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.agendaconsulta.dao.AgendaDAO;
import com.agendaconsulta.dao.AtendenteDAO;
import com.agendaconsulta.dao.CepDAO;
import com.agendaconsulta.dao.ConsultorioDAO;
import com.agendaconsulta.dao.TipoAtendimentoDAO;
import com.agendaconsulta.dao.UsuarioDAO;
import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.AteHorario;
import com.agendaconsulta.model.Atendente;
import com.agendaconsulta.model.Consultorio;
import com.agendaconsulta.model.TipoAtendimento;
import com.agendaconsulta.model.Usuario;
import com.agendaconsulta.model.cep.Endereco;
import com.agendaconsulta.util.CareFunctions;

import java.io.Serializable;























import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;





/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
/**
 * @author JARDIM
 *
 */
@ManagedBean(name = "mbConsultorio")
@SessionScoped
//@ViewScoped
public class ConsultorioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int intAnoSel;
	private String strMesSelecionado;
	private Consultorio consultorio = new Consultorio();
	private ConsultorioDAO consultorioDAO;
	private AtendenteDAO atendenteDAO = new AtendenteDAO();
	private List<Atendente> lstAtendente;
	private Atendente atendenteselecionado;
	private Agenda agendaDataselecionada;
	private Agenda agendaSelecionada;
	private List<Agenda> lstAgenda;
	private List<Agenda> lstAgendadodia;
	private AgendaDAO agendaDAO;
	private String strTat_tipo = "0";
	private Long strTat_codigo;
	private String strAte_Titulo = "Dr.";
	private AteHorario ateHorarioSelecionado;
	
	
	//Listagem de Pacientes
	List<Usuario> lstusuario;
	private Usuario usuario;
	private Usuario usuarioSelecionado;
	private UsuarioDAO usuarioDAO;
	List<String> mesesDoAno; 
	private List<Agenda> lstAgendaMarcada;
	private List<Agenda> lstAgendaEfetuadasPorAtendente;
	private List<Agenda> lstAgendaEfetuadas;
	private boolean mostrar = false;
	
	private List<String> lstDiaSemana;
	
	private ScheduleModel carregarCalendario;
	private TipoAtendimentoDAO tipoAtendimentoDAO;
	private boolean booCadastrandoAtendente;

	private String strMaskcnpjcpf = "99.999.999\\9999-99";
	private String strCNPJ;
	private String strCEP;
	private String strSenha;
	private String strPassoTela = "";
	private List<TipoAtendimento> tipoAtendimentos;
	private TipoAtendimento tipoAtendimentoSelecionado;
	
	private Endereco endereco;
	private CepDAO cepDAO = new CepDAO(); 
	
	public ConsultorioBean() {
		consultorioDAO = new ConsultorioDAO();
		agendaDAO = new AgendaDAO();
		strPassoTela = "1";
		intAnoSel = 2016;
		
		usuarioDAO = new UsuarioDAO();
		
		carregarCalendario = new DefaultScheduleModel();
		
		mesesDoAno = CareFunctions.mesesDoAno();
		lstDiaSemana = CareFunctions.diasDiaSemana();
		
		tipoAtendimentoDAO = new TipoAtendimentoDAO();
		tipoAtendimentos = tipoAtendimentoDAO.listPorTipo(0);
	
		/*
		meses  = new HashMap<String, String>();
		meses.put("Janeiro", "1" );
		meses.put("Fevereiro", "2" );
		meses.put("Março", "3" );
		meses.put("Abril", "4" );
		meses.put("Maio", "5" );
		meses.put("Junho", "6" );
		meses.put("Julho", "7" );
		meses.put("Agosto", "8" );
		meses.put("Setembro", "9" );
		meses.put("Outubro", "10" );
		meses.put("Novembro", "11" );
		meses.put("Dezembro", "12" );
		*/
		
		
		
	}
	
	
	public String NovoCadastro() {
		consultorioDAO = new ConsultorioDAO();
		strPassoTela = "1";
		
		return "consultorio.xhtml";
	}
	
	public String consultorioADM() {
		consultorioDAO = new ConsultorioDAO();
		strPassoTela = "1";
		
		return "consultorioADM.xhtml";
		//return "consultorio.xhtml";
	}
	

	@SuppressWarnings("finally")
	public String cadastrarConsultorio() {
		try {
			System.out.println("Vai salvar um Consultorio");
			if (consultorioDAO.save(consultorio)){
				strPassoTela = "4";
				//consultorio = new Consultorio();
		
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Consultório Adicionado com Sucesso!", "TipoAtendimento adicionado"));
				
				return "consultorioCadastrado";
			}else{
				return "erro";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "erro";
		} 
	}
	
	
	
	public void salvarAtendente(){
		try {
			atendenteselecionado.setAte_nome(atendenteselecionado.getAte_nome().toUpperCase());
			atendenteselecionado.setAte_titulo(getStrAte_Titulo());
			tipoAtendimentoSelecionado = tipoAtendimentoDAO.BuscarPorCodigo(strTat_codigo);
			atendenteselecionado.setTat_codigo(tipoAtendimentoSelecionado);
			System.out.println("Salvar Atendentes");
			atendenteDAO.save(atendenteselecionado);
			RequestContext.getCurrentInstance().closeDialog(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			//return "erro";
		} finally {
			strPassoTela = "4";
			//consultorio = new Consultorio();
	
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Atendente Cadastrado com Sucesso!", "Cadastro de Atendentes"));
			
			//return "atendenteCadastrado";
		}
	}
	
	 public void cadastrarAtendente() {
			atendenteselecionado = new Atendente();
			atendenteselecionado.setCon_codigo(consultorio);
			setBooCadastrandoAtendente(true);
			
	        Map<String,Object> options = new HashMap<String, Object>();
	        options.put("modal", true);
	        options.put("draggable", false);
	        options.put("resizable", false);
	        options.put("contentHeight", 270); 
	        RequestContext.getCurrentInstance().openDialog("atendenteCadastros", options, null);
	}
	 public void fecharCadastros(){
		 RequestContext.getCurrentInstance().closeDialog(null);
	 }
	 
	 
	 
	 public void salvarHorarioAtendente(){
			try {
				System.out.println("Salvar Horario");
				String strErro = "";
				if (ateHorarioSelecionado.getHor_inicio() == null)
					strErro = "Horário Inicial Inválido!";
				else if (ateHorarioSelecionado.getHor_fim() == null)
					strErro = "Horário Final Inválido!";
				else if (ateHorarioSelecionado.getHor_duracao() == null)
					strErro = "Informação da Duração Inválida!";
				
				if (strErro.trim().length() == 0){
					atendenteDAO.saveHorario(ateHorarioSelecionado);
					RequestContext.getCurrentInstance().closeDialog(null);
				}else{
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									strErro, "Cadastro de Atendentes"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				//return "erro";
			} finally {
				strPassoTela = "4";
				//consultorio = new Consultorio();
		
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Atendente Cadastrado com Sucesso!", "Cadastro de Atendentes"));
				
				//return "atendenteCadastrado";
			}
		}
		
		 public void cadastrarHorarioAtendente(String strDiaSemanaSelecionado) {
			 int intDiaSemana = CareFunctions.diasDiaSemana().indexOf(strDiaSemanaSelecionado) +1;
			 if (atendenteselecionado == null){
				 FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Selecione o Atendente!", "Cadastro de Atendentes"));	 
			 }else if (intDiaSemana < 1 || intDiaSemana > 7){
				 FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Selecione o Dia da Semana!", "Cadastro de Atendentes"));	 
			 }else{
				 ateHorarioSelecionado = new AteHorario();
				 ateHorarioSelecionado.setAte_codigo(atendenteselecionado);
				 ateHorarioSelecionado.setCon_codigo(atendenteselecionado.getCon_codigo());
				 ateHorarioSelecionado.setHor_diasemana(intDiaSemana);
				 setBooCadastrandoAtendente(false);
					
			     Map<String,Object> options = new HashMap<String, Object>();
			     options.put("modal", true);
			     options.put("draggable", false);
			     options.put("resizable", false);
			     options.put("contentHeight", 270); 
			     RequestContext.getCurrentInstance().openDialog("atendenteCadastros", options, null);
			 }
		}
	
	
	
	 public void viewConsultorio() {
	        Map<String,Object> options = new HashMap<String, Object>();
	        options.put("modal", true);
	        options.put("draggable", false);
	        options.put("resizable", false);
	        options.put("contentHeight", 320); 
	        RequestContext.getCurrentInstance().openDialog("consultorio", options, null);
	}
	 
	 public void viewAtendentes() {
	        Map<String,Object> options = new HashMap<String, Object>();
	        options.put("modal", true);
	        options.put("draggable", false);
	        options.put("resizable", false);
	        options.put("contentHeight", 820);
	        options.put("contentWidth", 1100);
	        //RequestContext.getCurrentInstance().openDialog("atendente", options, null);
	        RequestContext r;
	        r = RequestContext.getCurrentInstance();
	        r.openDialog("atendente", options, null);
	}
	 
	 public void viewPacientes() {
		 
	        Map<String,Object> options = new HashMap<String, Object>();
	        options.put("modal", true);
	        options.put("draggable", false);
	        options.put("resizable", false);
	        options.put("contentHeight", 820);
	        options.put("contentWidth", 1100);
	        RequestContext.getCurrentInstance().openDialog("consultorioPaciente", options, null);
	}
	 
	 
	 
	
	public void validarCNPJCPF(){
		//if (strCNPJ.trim().length() == 14 ||strCNPJ.trim().length() == 18 ){
		if (CareFunctions.Valida_CNPJ_CPF(strCNPJ)){
			strPassoTela = "2";
		}else{
		
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Informe um CPNJ ou CPF válido!", "Cadastro de Consultório"));
		}
	
		
	}
	
	public void consultarCNPJCPF(){
		strCNPJ = CareFunctions.removeTracoPont(strCNPJ);
		if (strCNPJ.trim().length() == 14 ||strCNPJ.trim().length() == 18 ){
			consultorio = consultorioDAO.consultarPorCNPJ(strCNPJ);
			if (consultorio == null){
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Consultório não Cadastrado!", "Cadastro de Consultório"));
			}else if (consultorio.getCon_senha().equals(getStrSenha())){
				lstAtendente = atendenteDAO.listPorConsultorio(consultorio);
				strPassoTela = "2";
			}else{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Senha Inválida!", "Cadastro de Consultório"));
			}			
			
		}else{
		
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Informe um CPNJ ou CPF válido!", "Cadastro de Consultório"));
		}
	
		
	}
	
	public void consultarCEP(){
		String strCEP = CareFunctions.removeTracoPont(getStrCEP());
		String strCNPJ = CareFunctions.removeTracoPont(getStrCNPJ());
		if (strCEP.trim().length() == 8){
			endereco = cepDAO.buscaPorcep(strCEP);
			
			if (endereco != null){
				strPassoTela = "3";
				consultorio.setCon_cep(strCEP);
				consultorio.setCon_cnpjcpf(strCNPJ);
				consultorio.setEnd_codigo(endereco);
			}else{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Cep Não Localizado", "Informe Novamente"));		
			}
		}else{
		
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Informe um CEP válido!", "Cadastro de Consultório"));
		}
	
		
	}
	public void marcaAgenda(Agenda agendaSelecionada) {
		AgendaBean mbAgenda = new AgendaBean();
		mbAgenda.marcaAgenda(agendaSelecionada);
	}
	 
	 
	public void onRowSelectAtendente(SelectEvent event) {
		  System.out.println("SelectEvent Atendente : " + atendenteselecionado.getAte_nome());
		  abrirAgenda();
	}
	
	public void onRowSelectData(SelectEvent event) {
		  System.out.println("SelectEvent Data : " + agendaDataselecionada.getAge_data1());
		  agendaSelecionada = null;
		  listarHorario();
	}
	
	public void onRowSelectAgenda(SelectEvent event) {
		  System.out.println("SelectEvent Agenda : " + agendaSelecionada.getAge_data() + " - " + agendaSelecionada.getAge_datahora());
		  
	}
	
	public void onRowSelectUsuario(SelectEvent event) {
		  System.out.println("SelectEvent Usuario Paciente : " + usuarioSelecionado.getUsu_nome());
		  listarAgendasUsuario(usuarioSelecionado);
		  mostrar = true;
		  
	}
	
	
	
	public void abrirAgenda(){
		if (atendenteselecionado == null || strMesSelecionado == null ) 
			return;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
		int intMes =  CareFunctions.retornaMesInteiro(strMesSelecionado);
		Date dtIni = new Date(intAnoSel, intMes -1, 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dtIni);
		int intUltimoDia = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
		Date dtFim = new Date(intAnoSel, intMes -1, intUltimoDia);
		
		if (intMes < 0 || intMes > 12){
			return;
		}
		
		
		
		Calendar diaInicial =  Calendar.getInstance();
		Calendar diaFinal =  Calendar.getInstance();
		String strData = "01" + "/" + intMes + "/" +  intAnoSel;
		diaInicial.setTime(CareFunctions.TextoParaData(strData, "00:00:01"));
		strData = intUltimoDia + "/" + intMes + "/" +  intAnoSel;
		diaFinal.setTime(CareFunctions.TextoParaData(strData, "23:59:59"));
		
		
		
		System.out.println("Vai abrir a Agenda "); 
		lstAgenda = agendaDAO.listPorAtendente(atendenteselecionado, diaInicial.getTime() , diaFinal.getTime());
		if (lstAgendadodia != null)
			lstAgendadodia.clear();
				
        System.out.println("Abriu "+ lstAgenda.size() );//
        
        //criarAgenda(atendenteselecionado, 11, 2015);
		
		FacesMessage msg = new FacesMessage("Abrir Agenda ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void listarHorario() {
		System.out.println("Vai Listar Horario "); 
		lstAgendadodia = agendaDAO.listPordia(atendenteselecionado, agendaDataselecionada.getAge_data1(), false);
				
        System.out.println("Abriu do Dia "+ lstAgendadodia.size() );//
		
		FacesMessage msg = new FacesMessage("Abrir Agenda do Dia ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}
	 
	 
	 
	 public void atualizaStatusAgenda(int intStatus){
		 if (intStatus >=3){//Cria uma nova agenda disponivel
			 Agenda novaAgenda = new Agenda();
			 novaAgenda.setAge_data(agendaSelecionada.getAge_data());
			 novaAgenda.setAge_datahora(agendaSelecionada.getAge_datahora());
			 novaAgenda.setAte_codigo(agendaSelecionada.getAte_codigo());
			 novaAgenda.setAge_status(1);
			 agendaDAO.save(novaAgenda);
		 }
		 
		 if (agendaSelecionada != null){
			 agendaSelecionada.setAge_status(intStatus);
			 agendaDAO.save(agendaSelecionada);
		 }
		 
		 listarHorario();
		 agendaSelecionada = null;
	 }
	 
	 public boolean agendaUsada(){
		 if (agendaSelecionada != null && agendaSelecionada.getUsu_codigo() != null){
			 return true;
		 }else{
			 return false;
		 }
			 
	 }
	 
	 
	 public void listaUsuariosClinica(){//buscarEditarUsuario
			System.out.println("Buscar  Usuacrios da Clinica");//
				lstusuario =  usuarioDAO.listPorConsultorio(consultorio);
				System.out.println("Buscou  " + lstusuario.size());
				
				if (lstusuario.size() > 0){
					usuario = lstusuario.get(0);
					
					viewPacientes();
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Ahoe", "Bem Vindo"));	
				}else{
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Não existe Pacientes", "Cancelado"));	
				}
	}
		
	private void listarAgendasUsuario(Usuario usuario){
			System.out.println("Vai abrir a Agenda "); 
			lstAgendaMarcada = agendaDAO.listPorUsuario(usuario, true);
			
			lstAgendaEfetuadasPorAtendente = agendaDAO.listPorUsuario(usuario, false);
		
			
			FacesMessage msg = new FacesMessage("Abrir Agenda ");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			
	}
	 
	 
	 public boolean agendaVazia(){
		 if (agendaSelecionada != null && agendaSelecionada.getUsu_codigo() == null){
			 return true;
		 }else{
			 return false;
		 }
			 
	 }
	 
	 public void criarAgenda(Atendente atendente, int iMes, int iAno){
		/* String strMes = CareFunctions.ColocaZeroInicio(iMes, 2);
		 String strAno = CareFunctions.ColocaZeroInicio(iAno, 4);
		 Calendar diaAtual =  Calendar.getInstance();
		 Calendar almocoIni =  Calendar.getInstance();
		 Calendar almocoFim =  Calendar.getInstance();
		 
		 for (int iDia = 1; iDia <= 31; iDia++ ){
			 String strData = iDia + "/" + strMes + "/" + strAno;
			 Time horaIni = null;
			 Time horaFim = null;
			 Time horaAlmoco = atendente.getAte_almoco();
			 
			 boolean booPararAlmoc = false;
			 
			 diaAtual.setTime(CareFunctions.TextoParaData(strData, "01:01:01"));
			 
			 
			 int diaSemana = diaAtual.get(Calendar.DAY_OF_WEEK);
			 
			 if (diaSemana == Calendar.SUNDAY){   
					System.out.println("Domingo");   
					horaIni = atendente.getAte_domingo_ini();
					horaFim = atendente.getAte_domingo_fim();
			 }else if (diaSemana == Calendar.MONDAY){ 
					System.out.println("Segunda");   
					horaIni = atendente.getAte_segunda_ini();
					horaFim = atendente.getAte_segunda_fim();
			 }else if (diaSemana == Calendar.TUESDAY){
					System.out.println("Terça");
					horaIni = atendente.getAte_terca_ini();
					horaFim = atendente.getAte_terca_fim();	
			 }else if (diaSemana == Calendar.WEDNESDAY){  
					System.out.println("Quarta");  
					horaIni = atendente.getAte_quarta_ini();					
					horaFim = atendente.getAte_quarta_fim();
			 }else if (diaSemana == Calendar.THURSDAY){
					System.out.println("Quinta");
					horaIni = atendente.getAte_quinta_ini();
					horaFim = atendente.getAte_quinta_fim();
			}else if (diaSemana == Calendar.FRIDAY){
					System.out.println("Sexta");   
					horaIni = atendente.getAte_sexta_ini();
					horaFim = atendente.getAte_sexta_fim();
			}else if (diaSemana == Calendar.SATURDAY){
					System.out.println("Sabado");
					horaIni = atendente.getAte_sabado_ini();
					horaFim = atendente.getAte_sabado_fim();
			}   
			 
						 	
			 if (horaIni != null && horaFim != null){
				 diaAtual.setTime(CareFunctions.TextoParaData(strData, horaIni.toString()));
				 
				 Calendar horaFinal =  Calendar.getInstance();
				 horaFinal.setTime(CareFunctions.TextoParaData(strData, horaFim.toString()));
				 
				 if (atendente.getAte_almoco() != null && atendente.getAte_almoco_fim() != null){
					 booPararAlmoc = (horaIni.before(horaAlmoco));
					 almocoIni.setTime(CareFunctions.TextoParaData(strData, atendente.getAte_almoco().toString()));
					 almocoFim.setTime(CareFunctions.TextoParaData(strData, atendente.getAte_almoco_fim().toString()));
				 }
				 
				 
				 
				 while (diaAtual.before(horaFinal) ) {
					 if (booPararAlmoc & (diaAtual.after(almocoIni))){
						 booPararAlmoc = false;
					 }else{
						  Agenda novaAgenda = new Agenda();
						  novaAgenda.setAge_data(diaAtual.getTime());
						  novaAgenda.setAge_datahora(new Timestamp(diaAtual.getTimeInMillis()));
						  novaAgenda.setAte_codigo(atendente);
						  novaAgenda.setAge_status(1);
						  agendaDAO.save(novaAgenda);
						  diaAtual.add(Calendar.MINUTE, 30);
					 }
			 	 }			 		
			 }
			 
		 }
		 */
		 
	 }
	 
	
	 
	 public void selecionarAtendimento(){
			tipoAtendimentos = tipoAtendimentoDAO.listPorTipo(Integer.valueOf(strTat_tipo));
			System.out.println("Listou : " +tipoAtendimentos.size());
	  }

	public Consultorio getConsultorio() {
		return consultorio;
	}


	public void setConsultorio(Consultorio consultorio) {
		this.consultorio = consultorio;
	}


	public ConsultorioDAO getConsultorioDAO() {
		return consultorioDAO;
	}


	public void setConsultorioDAO(ConsultorioDAO consultorioDAO) {
		this.consultorioDAO = consultorioDAO;
	}

	public String getStrMaskcnpjcpf() {
		return strMaskcnpjcpf;
	}


	public void setStrMaskcnpjcpf(String strMaskcnpjcpf) {
		this.strMaskcnpjcpf = strMaskcnpjcpf;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getStrCNPJ() {
		return strCNPJ;
	}


	public void setStrCNPJ(String strCNPJ) {
		this.strCNPJ = strCNPJ;
	}


	public String getstrPassoTela() {
		return strPassoTela;
	}


	public void setstrPassoTela(String strPassoTela) {
		this.strPassoTela = strPassoTela;
	}


	public String getStrCEP() {
		return strCEP;
	}


	public void setStrCEP(String strCEP) {
		this.strCEP = strCEP;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Atendente> getLstAtendente() {
		return lstAtendente;
	}

	public void setLstAtendente(List<Atendente> lstAtendente) {
		this.lstAtendente = lstAtendente;
	}

	public String getStrPassoTela() {
		return strPassoTela;
	}

	public void setStrPassoTela(String strPassoTela) {
		this.strPassoTela = strPassoTela;
	}

	public List<Agenda> getLstAgenda() {
		return lstAgenda;
	}

	public void setLstAgenda(List<Agenda> lstAgenda) {
		this.lstAgenda = lstAgenda;
	}

	public List<Agenda> getLstAgendadodia() {
		return lstAgendadodia;
	}

	public void setLstAgendadodia(List<Agenda> lstAgendadodia) {
		this.lstAgendadodia = lstAgendadodia;
	}

	public ScheduleModel getCarregarCalendario() {
		return carregarCalendario;
	}

	public void setCarregarCalendario(ScheduleModel carregarCalendario) {
		this.carregarCalendario = carregarCalendario;
	}

	public Agenda getAgendaDataselecionada() {
		return agendaDataselecionada;
	}

	public void setAgendaDataselecionada(Agenda agendaDataselecionada) {
		this.agendaDataselecionada = agendaDataselecionada;
	}

	public Agenda getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	public List<Agenda> getLstAgendaMarcada() {
		return lstAgendaMarcada;
	}

	public void setLstAgendaMarcada(List<Agenda> lstAgendaMarcada) {
		this.lstAgendaMarcada = lstAgendaMarcada;
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

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getLstusuario() {
		return lstusuario;
	}

	public void setLstusuario(List<Usuario> lstusuario) {
		this.lstusuario = lstusuario;
	}

	public boolean isMostrar() {
		return mostrar;
	}

	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

	
	public String getStrMesSelecionado() {
		return strMesSelecionado;
	}

	public void setStrMesSelecionado(String strMesSelecionado) {
		this.strMesSelecionado = strMesSelecionado;
	}

	
	public int getIntAnoSel() {
		return intAnoSel;
	}

	public void setIntAnoSel(int intAnoSel) {
		this.intAnoSel = intAnoSel;
	}

	public List<String> getMesesDoAno() {
		return mesesDoAno;
	}

	public void setMesesDoAno(List<String> mesesDoAno) {
		this.mesesDoAno = mesesDoAno;
	}

	public String getStrSenha() {
		return strSenha;
	}

	public void setStrSenha(String strSenha) {
		this.strSenha = strSenha;
	}

	public String getStrTat_tipo() {
		return strTat_tipo;
	}

	public void setStrTat_tipo(String strTat_tipo) {
		this.strTat_tipo = strTat_tipo;
	}

	public Long getStrTat_codigo() {
		return strTat_codigo;
	}

	public void setStrTat_codigo(Long strTat_codigo) {
		this.strTat_codigo = strTat_codigo;
	}

	public Atendente getAtendenteselecionado() {
		return atendenteselecionado;
	}

	public void setAtendenteselecionado(Atendente atendenteselecionado) {
		this.atendenteselecionado = atendenteselecionado;
	}


	public boolean isBooCadastrandoAtendente() {
		return booCadastrandoAtendente;
	}


	public void setBooCadastrandoAtendente(boolean booCadastrandoAtendente) {
		this.booCadastrandoAtendente = booCadastrandoAtendente;
	}


	public String getStrAte_Titulo() {
		return strAte_Titulo;
	}


	public void setStrAte_Titulo(String strAte_Titulo) {
		this.strAte_Titulo = strAte_Titulo;
	}


	public TipoAtendimento getTipoAtendimentoSelecionado() {
		return tipoAtendimentoSelecionado;
	}


	public void setTipoAtendimentoSelecionado(TipoAtendimento tipoAtendimentoSelecionado) {
		this.tipoAtendimentoSelecionado = tipoAtendimentoSelecionado;
	}


	public AteHorario getAteHorarioSelecionado() {
		return ateHorarioSelecionado;
	}


	public void setAteHorarioSelecionado(AteHorario ateHorarioSelecionado) {
		this.ateHorarioSelecionado = ateHorarioSelecionado;
	}


	public List<String> getLstDiaSemana() {
		return lstDiaSemana;
	}


	public void setLstDiaSemana(List<String> lstDiaSemana) {
		this.lstDiaSemana = lstDiaSemana;
	}


	

	
		
	

	
}