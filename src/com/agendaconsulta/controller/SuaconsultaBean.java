
package com.agendaconsulta.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



















import com.agendaconsulta.dao.AtendenteDAO;
import com.agendaconsulta.dao.CepDAO;
import com.agendaconsulta.model.Agenda;
import com.agendaconsulta.model.Atendente;
import com.agendaconsulta.model.Consultorio;
import com.agendaconsulta.model.TipoAtendimento;
import com.agendaconsulta.model.cep.Endereco;
import com.agendaconsulta.util.CareFunctions;
import com.sun.faces.facelets.tag.jsf.core.ConvertDateTimeHandler;


/**
 * @author - Alexandre
 * @since - 08/08/2014
 */
@ManagedBean(name = "mbSuaconsulta")
@SessionScoped
//@ViewScoped
//@RequestScoped

public class SuaconsultaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	private List<Atendente> lstAtendentes;
	private List<Consultorio> lstConsultorios;
	private Consultorio consultorioSelecionado = new Consultorio(); 
	private Atendente atendente = new Atendente();
	private Atendente atendenteSelecionado = new Atendente();
	private AtendenteDAO atendenteDAO;
	
	private TipoAtendimento tipoAtendimentoSelecionado = new TipoAtendimento();
	
	private String strPesquisar;
	private String strTat_codigo = "0";
	private String strLocalizacao = "-23.5630635,-46.6566214";
	
	
	private MapModel simpleModel;
    
    private Marker marker;
	

    private CepDAO cepDAO = new CepDAO();
    private List<Endereco> enderecos = new ArrayList<Endereco>();
    
	public SuaconsultaBean() {
		atendenteDAO = new AtendenteDAO();
	}
	

	public void salvaTipoAtendimento_bkp() {
		try {
			System.out.println("teste");
			atendenteDAO.save(atendente);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			atendente = new Atendente();
			lstAtendentes = atendenteDAO.listAll();
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"TipoAtendimento adicionado", "TipoAtendimento adicionado"));
		}
	}
	
	public void tipoAtendimentoSelecionado(){
    	FacesMessage msg = new FacesMessage("tipoAtendimentoSelecionado ");
        FacesContext.getCurrentInstance().addMessage(null, msg);   
        lstConsultorios = null;
        lstAtendentes = null;
        simpleModel = new DefaultMapModel();
    }
	
	public void pesquisaAtendente(){
		System.out.println("Buscar: " + strPesquisar + " - Especialidade : " + strTat_codigo ); 
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
	
	public void pesquisaPorCep(){
		System.out.println("Buscar: " + strPesquisar ); 
		if (strPesquisar.trim().length() == 0){
			FacesMessage msg = new FacesMessage("Informe um Local (Bairro, CEP ou Nome de Rua! ");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        return;
		}
		
		if (strTat_codigo.trim().length() == 0)
			return;
		
		
		  
		if (CareFunctions.isApenasNumeros(strPesquisar)){
			System.out.println("Apenas numeros"); 
			lstConsultorios = atendenteDAO.listPorCep(strPesquisar, strTat_codigo);
			//lstAtendentes = atendenteDAO.listPorCep(strPesquisar, strTat_codigo);
		}else{			
			System.out.println("Tem letras");
			lstAtendentes = atendenteDAO.listPorNome(strPesquisar, strTat_codigo);
		}
		
        System.out.println("Abriu "+ lstConsultorios.size() );//
		
		FacesMessage msg = new FacesMessage("Pesquisa de Atendentes ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        marcarConsultorioMapa();
	}
	
	
	
	
	
	public void marcarConsultorioMapa(){
		simpleModel = new DefaultMapModel();
		
		//strLocalizacao = "-23.4879714,-46.422761,17";
		if (lstConsultorios.size() > 0)//Se Encontrou marca a Poscção geografica do Primeiro
			strLocalizacao = lstConsultorios.get(0).getCon_latitude() + "," + lstConsultorios.get(0).getCon_longitude();
		
		for(int i = 0; i < lstConsultorios.size(); i++){
			if (lstConsultorios.get(i).getCon_latitude().length() > 0 && lstConsultorios.get(i).getCon_longitude().length() > 0){
				//Shared coordinates
				double decLat = Double.parseDouble(lstConsultorios.get(i).getCon_latitude());
				double decLong = Double.parseDouble(lstConsultorios.get(i).getCon_longitude());
		        LatLng coord1 = new LatLng(decLat, decLong);
		        ///Basic marker
		        //simpleModel.addOverlay(new Marker(coord1, lstConsultorios.get(i).getCon_nome()));				 
		        simpleModel.addOverlay(new Marker(coord1, Integer.toString(i)));
		        //simpleModel.addOverlay(new Marker(latlng, title, data)  (coord1, lstConsultorios.get(i).getCon_nome()));
			}
		}  
		
      
	}
	
	 
	public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();         
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Consultório Selecionado", marker.getTitle()));
	    String strLat =  String.valueOf(marker.getLatlng().getLat());
	    String strLong =  String.valueOf(marker.getLatlng().getLng());
	      
	    consultorioSelecionado = lstConsultorios.get(Integer.parseInt(marker.getTitle()) );	        
	}
	 

	
	
	
	///  GETERS E SETERS

	public List<Atendente> getLstAtendentes() {
		return lstAtendentes;
	}


	public void setLstAtendentes(List<Atendente> lstAtendentes) {
		this.lstAtendentes = lstAtendentes;
	}


	public Atendente getAtendente() {
		return atendente;
	}


	public void setAtendente(Atendente atendente) {
		this.atendente = atendente;
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


	public TipoAtendimento getTipoAtendimentoSelecionado() {
		return tipoAtendimentoSelecionado;
	}


	public void setTipoAtendimentoSelecionado(
			TipoAtendimento tipoAtendimentoSelecionado) {
		this.tipoAtendimentoSelecionado = tipoAtendimentoSelecionado;
	}


	public String getStrPesquisar() {
		return strPesquisar;
	}


	public void setStrPesquisar(String strPesquisar) {
		this.strPesquisar = strPesquisar;
	}


	public String getStrTat_codigo() {
		return strTat_codigo;
	}


	public void setStrTat_codigo(String strTat_codigo) {
		this.strTat_codigo = strTat_codigo;
	}




	public void setMarker(Marker marker) {
		this.marker = marker;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	public List<Consultorio> getLstConsultorios() {
		return lstConsultorios;
	}


	public void setLstConsultorios(List<Consultorio> lstConsultorios) {
		this.lstConsultorios = lstConsultorios;
	}


	
	
	public MapModel getSimpleModel() {
        return simpleModel;
    }
      
    
      
    public Marker getMarker() {
        return marker;
    }


	public Consultorio getConsultorioSelecionado() {
		return consultorioSelecionado;
	}


	public void setConsultorioSelecionado(Consultorio consultorioSelecionado) {
		this.consultorioSelecionado = consultorioSelecionado;
	}

    
	
	
	
	
	
	
	//ALGUNS TESTES
	public void marcarMapa(){
		simpleModel = new DefaultMapModel();
        
        //Shared coordinates
        LatLng coord1 = new LatLng(-23.4844706,-46.4253402);
        LatLng coord2 = new LatLng(-23.4878582,-46.4172076);
        LatLng coord3 = new LatLng(-23.4879714,-46.422761);
        LatLng coord4 = new LatLng(-23.4907581,-46.4164775);
          
        ///Basic marker
        simpleModel.addOverlay(new Marker(coord1, "Marcar 1"));
        simpleModel.addOverlay(new Marker(coord2, "Marcar 2"));
        simpleModel.addOverlay(new Marker(coord3, "Marcar 3"));
        simpleModel.addOverlay(new Marker(coord4, "Marcar 4"));
      
	}
    
    
	public String getStrLocalizacao() {
		return strLocalizacao;
	}


	public void setStrLocalizacao(String strLocalizacao) {
		this.strLocalizacao = strLocalizacao;
	}


	public static void main(String args[]){
		String teste = "123e4";
		System.out.println("Buscar: " +  teste); 
		  
		if (CareFunctions.isApenasNumeros(teste))
			System.out.println("Apenas Numero:");
		else
			System.out.println("Tem Letra");
	}
    
	
	
}