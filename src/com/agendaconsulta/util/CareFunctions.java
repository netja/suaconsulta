package com.agendaconsulta.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CareFunctions {
	private List<String> mesesDoAno;
	
	public static int retornaMesInteiro(String strMes){
		return mesesDoAno().indexOf(strMes) +1;
	}
	
	public static List<String> mesesDoAno(){
		List<String> mesesDoAno; 
		mesesDoAno = new ArrayList<String>();
		mesesDoAno.add("Janeiro");
		mesesDoAno.add("Fevereiro");
		mesesDoAno.add("Março");
		mesesDoAno.add("Abril");
		mesesDoAno.add("Maio");
		mesesDoAno.add("Junho");
		mesesDoAno.add("Julho");
		mesesDoAno.add("Agosto");
		mesesDoAno.add("Setembro");
		mesesDoAno.add("Outubro");
		mesesDoAno.add("Novembro");
		mesesDoAno.add("Dezembro");
		
		return mesesDoAno;
	}
	
	public static boolean isApenasNumeros(String str) {  
	    if (str == null || str.length() == 0)  
	        return false;  
	      
	    for (int i = 0; i < str.length(); i++) {  
	    
	        if (!Character.isDigit(str.charAt(i)))  
	            return false;  
	    }  
	      
	    return true;  
	} 
	
	public static Date addDays(Date d, int days) {
        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
        return d;
    }

	
	public static List<String> diasDiaSemana(){
		ArrayList<String> lstDiaSemana = new ArrayList<String>();
		lstDiaSemana.add("Segunda-Feira");
		lstDiaSemana.add("Terça-Feira");
		lstDiaSemana.add("Quarta-Feira");
		lstDiaSemana.add("Quinta-Feira");
		lstDiaSemana.add("Sexta-Feira");
		lstDiaSemana.add("Sábado");
		lstDiaSemana.add("Domingo");
		
		return lstDiaSemana;
	}
	
	public static String removeAcentos(String strTexto){
		strTexto = Normalizer.normalize(strTexto, Normalizer.Form.NFD);  
		strTexto = strTexto.replaceAll("[^\\p{ASCII}]", "");  
	    return strTexto;  
	}
	
	public static String removeTracoPont(String strTexto){
		strTexto = strTexto.replaceAll("[^0-9]", "");   
	    return strTexto;  
	}
	
	public static String limpaStrFone(String strFone){
		strFone = strFone.toString().replaceAll("[- /.]", "");  
		strFone = strFone.toString().replaceAll("[-()]", "");
		
		return strFone;
	}
	
	static public String DataParaTexto(Timestamp Data) {
		return DataParaTexto(Data, "dd/MM/yyyy");
	}
	
	static public String DataParaTexto(Timestamp Data, String Mascara ) {
		return DataParaTexto(Data, Mascara, false);
	}
	static public String DataParaTexto(Timestamp Data, String Mascara , boolean retornaVazio) {
		if (Data == null || (Data.equals(new Timestamp(0)) && retornaVazio)) {
			return "";
		} else {
			return new SimpleDateFormat(Mascara).format(Data);
		}
	}

	static public Timestamp getDataAtual() {
		Timestamp DataAtual = new Timestamp(System.currentTimeMillis());

		DataAtual = TextoParaData(DataParaTexto(DataAtual), "");

		return DataAtual;
	}

	static public Timestamp TextoParaData(String strData, String strHora) {
		/*if (Hora) {
			Data = Data + " 23:59:59";
			return TextoParaData(Data,  "dd/MM/yyyy HH:mm:ss");
		} else {
			return TextoParaData(Data, "dd/MM/yyyy");
		}*/
		
		if (strHora.trim().length() > 0){
			strData = strData + " " + strHora;
			return TextoParaDataFormat(strData,  "dd/MM/yyyy HH:mm:ss");
		}else{
			return TextoParaDataFormat(strData,  "dd/MM/yyyy");
		}
	}

	static public Timestamp TextoParaDataFormat(String Data,  String formato) {

		DateFormat df = new SimpleDateFormat(formato);
		Timestamp tsData = null;
		if (Data == null) {
			return tsData;
		}
		if ((Data.equals("")) || (Data.equals("__/__/____")) || (Data.equals("__/__/____ __:__"))) {
			return tsData;
		}
		df.setLenient(false); // gera exception se a data for invalida
		try {
			tsData = new Timestamp(df.parse(Data).getTime());

		} catch (Exception ex) {
			// Devido ao horario de verão no existe a ata 10/10/2010 00:00:00 enta passa para 10/10/2010 01:00:00
			// Se não consigo converter assim mesmo é porque a data a converter não é a data citada acima então gera exceção.
			if (formato.indexOf("HH") > 0) {
				Data = Data.substring(0, formato.indexOf("HH")) + "01" + Data.substring(formato.indexOf("HH") + 2);
			}

			try {
				tsData = new Timestamp(df.parse(Data).getTime());
			} catch (Exception ex1) {
				return null;
			}

		}
		return tsData;
	}

	static public long getUmDia() {
		return getUmaHora() * 24;
	}

	static public void EsperePorSegundo(long segundos) {
		long agora = System.currentTimeMillis();
		long tempoEsperado = agora + (1000 * segundos);
		while (tempoEsperado > System.currentTimeMillis()) {
			;
		}
	}

	static public long getUmaHora() {
		return 1000 * 60 * 60;
	}
	
	public static String ColocaBrancoInicio(String Texto, int Tamanho) {
		return ColocaBranco(Texto, Tamanho, "Inicio");
	}

	public static String ColocaBrancoFim(String Texto, int Tamanho) {
		return ColocaBranco(Texto, Tamanho, "Fim");
	}

	protected static String ColocaBranco(String Texto, int Tamanho, String Posicao) {
		if (Texto == null) {
			Texto = "";
		}
		String TextoOriginal = Texto;
		if (Texto.length() > Tamanho) {
			return Texto.substring(0, Tamanho);
		}
		for (int x = 0; x < Tamanho - TextoOriginal.length(); x++) {
			if (Posicao.equals("Inicio")) {
				Texto = " " + Texto;
			} else {
				Texto = Texto + " ";
			}

		}
		return Texto;
	}
	
	public static String ColocaZeroInicio(int iNumero, int Tamanho) {
		String strNumero = "" + iNumero;
		return ColocaZero(strNumero , Tamanho, "Inicio");
	}

	public static String ColocaZeroInicio(String Texto, int Tamanho) {
		return ColocaZero(Texto, Tamanho, "Inicio");
	}

	public static String ColocaZeroFim(String Texto, int Tamanho) {
		return ColocaZero(Texto, Tamanho, "Fim");
	}

	protected static String ColocaZero(String Texto, int Tamanho, String Posicao) {
		String TextoOriginal = Texto;
		if (Texto.length() > Tamanho) {
			return Texto.substring(0, Tamanho);
		}
		for (int x = 0; x < Tamanho - TextoOriginal.length(); x++) {
			if (Posicao.equals("Inicio")) {
				Texto = "0" + Texto;
			} else {
				Texto = Texto + "0";
			}

		}
		return Texto;
	}
	
	public static String RemoveCaracteresEspecias(String TextoOld) {
		String Texto = "";
		for (int x = 0; x < TextoOld.length(); x++) {
			if (((TextoOld.charAt(x) >= '0') && (TextoOld.charAt(x) <= '9')) || ((TextoOld.charAt(x) >= 'A') && (TextoOld.charAt(x) <= 'z'))) {
				Texto = Texto + TextoOld.charAt(x);
			} else {
				Texto = Texto + "_";
			}
		}
		return Texto;
	}

	public static String RemoveCaracteresNaoAlfaNumericos(String TextoOld) {
		String Texto = "";
		for (int x = 0; x < TextoOld.length(); x++) {
			if (((TextoOld.charAt(x) >= '0') && (TextoOld.charAt(x) <= '9')) || ((TextoOld.charAt(x) >= 'A') && (TextoOld.charAt(x) <= 'z'))) {
				Texto = Texto + TextoOld.charAt(x);
			}
		}
		return Texto;
	}
	

	public static String RemoveCaracteresNaoNumericos(String TextoOld) {
		String Texto = "";
		for (int x = 0; x < TextoOld.length(); x++) {
			if (((TextoOld.charAt(x) >= '0') && (TextoOld.charAt(x) <= '9'))) {
				Texto = Texto + TextoOld.charAt(x);
			}
		}
		return Texto;
	}

	public static String RemoveEnter(String TextoOld) {
		String Texto = "";
		for (int x = 0; x < TextoOld.length(); x++) {
			if (TextoOld.charAt(x) == '\n') {
				Texto = Texto + " ";
			} else if (TextoOld.charAt(x) == '\r') {
				// nao faz nada
			} else {
				Texto = Texto + TextoOld.charAt(x);
			}
		}
		return Texto;
	}
	
	static public boolean Valida_CNPJ_CPF(String str_cnpj) {
		str_cnpj = CareFunctions.RemoveCaracteresNaoNumericos(str_cnpj);
		if (str_cnpj.trim().length() == 14){
			return Valida_CNPJ(str_cnpj);
		}else if (str_cnpj.trim().length() == 11){
			return Valida_CPF(str_cnpj);
		}else{
			return false;
		}
	}
	
	static public boolean Valida_CNPJ(String str_cnpj) {
		if (str_cnpj.trim().equals("") || str_cnpj.trim().equals("99999999999999")) {
			return true;
		}

		if (str_cnpj.length() != 14) {
			return false;
		}

		int soma = 0, aux, dig;
		String cnpj_calc = str_cnpj.substring(0, 12);

		char[] chr_cnpj = str_cnpj.toCharArray();

		/* Primeira parte */
		for (int i = 0; i < 4; i++) {
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
			}
		}
		for (int i = 0; i < 8; i++) {
			if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
				soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
			}
		}
		dig = 11 - (soma % 11);

		cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

		/* Segunda parte */
		soma = 0;
		for (int i = 0; i < 5; i++) {
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
			}
		}
		for (int i = 0; i < 8; i++) {
			if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
				soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
			}
		}
		dig = 11 - (soma % 11);
		cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

		return str_cnpj.equals(cnpj_calc);
	}

	// public class Cpf {

	static public boolean Valida_CPF(String strCpf) {
		if (strCpf.trim().equals("")) {
			return true;
		}

		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}
		;

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
		return nDigVerific.equals(nDigResult);
	}

	/*
	 * public static void main(String[] args) { System.out.println( CPF("04624193806") ); }
	 */
	// }

	public static BigDecimal ConverteStringToBigDecimal(String strValor) {

		BigDecimal valorRetorno = new BigDecimal(0.0);

		int qtdeCaracter = strValor.length();
		int caracterAtual = 0;

		String strNovo = "";

		while (caracterAtual < qtdeCaracter) {

			if (!strValor.substring(caracterAtual, caracterAtual + 1).equals(".")) {
				strNovo = strNovo + strValor.substring(caracterAtual, caracterAtual + 1);
			}

			caracterAtual = caracterAtual + 1;

		}

		strNovo = strNovo.replaceAll(",", ".");

		if (strNovo.trim().equals("")) {
			valorRetorno = new BigDecimal(0.0);
		} else {
			valorRetorno = new BigDecimal(strNovo);
		}
		return valorRetorno;

	}
	
	/**
	 * Retorna substring com tratativa para não lançar ArrayIndexOutOfBounds
	 * 
	 * @param texto
	 *            Texto a ser aplicado substring
	 * @param arg0
	 *            endIndex do comando substring comum
	 * @return substring conforme parametros, porém sem lançar Exception
	 */
	public static String subString(String texto, int arg0) {
		try {
			return texto.substring(0, arg0);
		} catch (Exception e) {
			return texto;
		}
	}

	/**
	 * Retorna substring com tratativa para não lançar ArrayIndexOutOfBounds
	 */
	public static String subString(String texto, int arg0, int arg1) {
		try {
			return texto.substring(arg0, arg1);
		} catch (Exception e) {
			return "";
		}
	}
	
	private static String removerZeros(String texto , String posicao){		
		if("Inicio".equals(posicao)){
			for (int i = 0; i < texto.length(); i++) {  
				if (!String.valueOf(texto.charAt(i)).equals("0")) {   
					return texto.substring(i);
				}  
			}  
		}else if ("Fim".equals(posicao)){
			for (int i = texto.length()-1;i > 0; i--) {  
				if (!String.valueOf(texto.charAt(i)).equals("0")) {   
					return texto.substring(0,i+1);
				}  
			}  
		}
		return texto;

	}
	public static String removerZerosInicio(String texto){
		return removerZeros(texto, "Inicio");
	}
	public static String removerZerosFim(String texto){
		return removerZeros(texto, "Fim");
	}
	
	
}
