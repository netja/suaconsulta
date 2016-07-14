
package com.agendaconsulta.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.agendaconsulta.dao.AgendaDAO;
import com.agendaconsulta.dao.AtendenteDAO;
import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.AteHorario;
import com.agendaconsulta.model.Atendente;
import com.agendaconsulta.model.TipoAtendimento;
import com.agendaconsulta.util.CareFunctions;

import sun.security.jca.GetInstance;



/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@ManagedBean(name = "mbAtendente")
@SessionScoped
//@ViewScoped
//@RequestScoped
public class AtendenteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Atendente> lstAtendentes;
	//private Atendente atendente = new Atendente();
	private Atendente atendenteSelecionado;
	private AtendenteDAO atendenteDAO;
	private AteHorario ateHorarioSelecionado = new AteHorario();
	private Agenda agenda;
	private AgendaDAO agendaDAO;
	
	private TipoAtendimento tipoAtendimentoSelecionado = new TipoAtendimento();
	private AteHorario ateHorario = new AteHorario();
	private List<AteHorario> lstAteHorario;
	
	private String strPesquisar;
	private String strTat_codigo = "0";
	private String strDiaSemanaSelecionado;
	

	public AtendenteBean() {
		atendenteDAO = new AtendenteDAO();
		agendaDAO = new AgendaDAO();
	}
	

	
	
	public void pesquisaAtendente(){
		System.out.println("Buscar: " + strPesquisar ); 
		if (strPesquisar.trim().length() == 0){
			FacesMessage msg = new FacesMessage("Informe o Nome ou o Código para Pesquisa! ");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        return;
		}
		
		if (strTat_codigo.trim().length() == 0)
			return;
		  
		if (CareFunctions.isApenasNumeros(strPesquisar)){
			System.out.println("Apenas numeros"); 
			lstAtendentes = atendenteDAO.listPorCodigo(strPesquisar, strTat_codigo);
		}else{			
			System.out.println("Tem letras");
			lstAtendentes = atendenteDAO.listPorNome(strPesquisar, strTat_codigo);
		}
		
        System.out.println("Abriu "+ lstAtendentes.size() );//
		
		FacesMessage msg = new FacesMessage("Pesquisa de Atendentes ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        
	}
	
	 public void viewAgenda() {
		 	
		 
		 
	        Map<String,Object> options = new HashMap<String, Object>();
	        options.put("modal", true);
	        options.put("draggable", false);
	        options.put("resizable", false);
	        options.put("contentHeight", 320); 
	        RequestContext.getCurrentInstance().openDialog("agenda", options, null);
	        
	}
	 
	 
	 
	 public void onRowSelectDiaSemanaSelecionado(SelectEvent event) {
		  System.out.println("SelectEvent Dia Semana : " + strDiaSemanaSelecionado);
		  
		  int intDiaSemana = CareFunctions.diasDiaSemana().indexOf(strDiaSemanaSelecionado) +1;
		  listarHorario(intDiaSemana);
		 
	}
	 
	private void listarHorario(int intDiaSemana){
		if ((atendenteSelecionado == null) || (atendenteSelecionado.getAte_codigo() == 0)){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Selecione um Atendente", "Selecione um Atendente"));
		
		}else{
			lstAteHorario = atendenteDAO.listHorarioPorAtendent(atendenteSelecionado, intDiaSemana);
		}
	}
	
	public  void gerarAgenda(String strMes, int intAno, Atendente atendente){
		int intMes = CareFunctions.retornaMesInteiro(strMes);
		gerarAgenda(intMes, intAno, atendente);
	}
	
	public  void gerarAgenda(int intMes, int intAno, Atendente atendente){
		try {
			atendenteSelecionado = atendente;
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String data = "01/" + intMes + "/" + intAno;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(data));
			
			
			
			SimpleDateFormat formatoDataPrint = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			System.out.println(formatoData.format(cal.getTime()));
			
			int intUltimoDia = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
			
			for (int intDia =1; intDia <= intUltimoDia; intDia++){
				Calendar calHorario = new GregorianCalendar(intAno, intMes - 1, intDia);  
				int diaSemana = calHorario.get(Calendar.DAY_OF_WEEK);
				
				listarHorario(diaSemana);
				if (lstAteHorario == null)
					return;
				for (int i = 0; i < lstAteHorario.size(); i ++){
					if (1==1){//   (lstAteHorario.get(i).getHor_semana() +1) == diaSemana){
						int intHoraDeduzi = 10800000;// Não sei porque,  mas está gerando com 3 horas a Mais. Então estou descontando 3 horas
						calHorario = new GregorianCalendar(intAno, intMes - 1, intDia);
						
						ateHorario = lstAteHorario.get(i);
						int intHini =  (int) ateHorario.getHor_inicio().getTime() - intHoraDeduzi;
						calHorario.add(Calendar.MILLISECOND, intHini);
				
						Calendar calHorarioFim  = new GregorianCalendar(intAno, intMes - 1, intDia);
						int intHfim =  (int) ateHorario.getHor_fim().getTime() - intHoraDeduzi;
						calHorarioFim.add(Calendar.MILLISECOND, intHfim);
						System.out.println("FIM>> " + formatoData.format( calHorarioFim.getTime()));
						
						int intHDuracao =  (int) ateHorario.getHor_duracao().getTime() - intHoraDeduzi;
						ArrayList<Agenda> agendas = new ArrayList<Agenda>();
						do{
							System.out.println(formatoData.format( calHorario.getTime()));
							agenda = new Agenda();
							agenda.setAte_codigo(atendenteSelecionado);
							
							agenda.setAge_datahora(new Timestamp(calHorario.getTimeInMillis()));
							
							Calendar calData = Calendar.getInstance();
							calData.setTime(calHorario.getTime());
							calData.set(Calendar.HOUR_OF_DAY, 0);
							calData.set(Calendar.MINUTE, 0);
							calData.set(Calendar.SECOND, 0);
							calData.set(Calendar.MILLISECOND, 0);
							agenda.setAge_data(calData.getTime());
							
							
							agenda.setAge_status(1);
							
							agendas.add(agenda);
							calHorario.add(Calendar.MILLISECOND, intHDuracao);
							
						}while ((calHorario.before(calHorarioFim)) || (calHorario.equals(calHorarioFim)));
						
						agendaDAO.save(agendas);
					}
					
				}
				
				System.out.println("FIM");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//gerarAgenda(2,2016);
		
		//Time tim = new Time(57600000);
		
		//System.out.println(tim.toString());
		
		 
		 //System.out.println("today is " + d1.toString());
		
		Calendar calendario = new GregorianCalendar(2015, 4, 1);  
		int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		String retorno = formatoData.format(calendario.getTime());
		System.out.println(retorno);
		//calendario.add(Calendar.MILLISECOND, 57600000);
		System.out.println(retorno);
		//calendario.setTime(calendario.getTime());
		calendario.add(Calendar.DATE, 1100);
		calendario.add(Calendar.MILLISECOND,  50400000 );
		retorno = formatoData.format( calendario.getTime());
		System.out.println(retorno);
	    
		Date d1 = new Date();
		 Calendar cl = Calendar.getInstance();
		 cl.setTime(d1);
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		 System.out.println( sdf.format(cl.getTime()));
	     cl.add(Calendar.MILLISECOND, 57600000); 
	     System.out.println( sdf.format(cl.getTime()));
	     
	     System.out.println( sdf.format(cl.getTime()));
	     
	     
	     //cl.add(arg0, arg1);
		
	}
	
	public void onRowSelectAtendente(SelectEvent event) {
		  System.out.println("SelectEvent Atendente : " + atendenteSelecionado.getAte_nome());
		
	}

	
	public List<Atendente> getLstAtendentes() {
		return lstAtendentes;
	}


	public void setLstAtendentes(List<Atendente> lstAtendentes) {
		this.lstAtendentes = lstAtendentes;
	}




	public Atendente getAtendenteSelecionado() {
		return atendenteSelecionado;
	}


	public void setAtendenteSelecionado(Atendente atendenteSelecionado) {
		this.atendenteSelecionado = atendenteSelecionado;
	}


	public AtendenteDAO getAtendenteDAO() {
		return atendenteDAO;
	}


	public void setAtendenteDAO(AtendenteDAO atendenteDAO) {
		this.atendenteDAO = atendenteDAO;
	}


	public String getStrPesquisar() {
		return strPesquisar;
	}


	public void setStrPesquisar(String strPesquisar) {
		this.strPesquisar = strPesquisar.toUpperCase();
	}
	
	
	
	
	public void tipoAtendimentoSelecionado(){
    	//System.out.println("Tipo Selecionado " +tipoEmbalagem.getTat_descricao());
    	FacesMessage msg = new FacesMessage("tipoAtendimentoSelecionado ");
        FacesContext.getCurrentInstance().addMessage(null, msg);        
    }

	public TipoAtendimento getTipoAtendimentoSelecionado() {
		return tipoAtendimentoSelecionado;
	}


	public void setTipoAtendimentoSelecionado(
			TipoAtendimento tipoAtendimentoSelecionado) {
		this.tipoAtendimentoSelecionado = tipoAtendimentoSelecionado;
	}


	public String getStrTat_codigo() {
		return strTat_codigo;
	}


	public void setStrTat_codigo(String strTat_codigo) {
		this.strTat_codigo = strTat_codigo;
	}


	public String getStrDiaSemanaSelecionado() {
		return strDiaSemanaSelecionado;
	}


	public void setStrDiaSemanaSelecionado(String strDiaSemanaSelecionado) {
		this.strDiaSemanaSelecionado = strDiaSemanaSelecionado;
	}


	public List<AteHorario> getLstAteHorario() {
		return lstAteHorario;
	}


	public void setLstAteHorario(List<AteHorario> lstAteHorario) {
		this.lstAteHorario = lstAteHorario;
	}




	public AteHorario getAteHorario() {
		return ateHorario;
	}




	public void setAteHorario(AteHorario ateHorario) {
		this.ateHorario = ateHorario;
	}




	public AteHorario getAteHorarioSelecionado() {
		return ateHorarioSelecionado;
	}




	public void setAteHorarioSelecionado(AteHorario ateHorarioSelecionado) {
		this.ateHorarioSelecionado = ateHorarioSelecionado;
	}  
	
	

	
		
	

	
}