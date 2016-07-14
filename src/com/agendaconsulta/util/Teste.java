package com.agendaconsulta.util;

import java.util.Arrays;
import java.util.List;

public class Teste {
	
	public static void main(String[] args) {	
		//LISTA DE VALORES int[] array = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110};	System.out.printf("%s %12s \n", "Index", "Valores");	//PERCORRE CADA ELEMENTO E IMPRIME O ÍNDICE COM O VALOR for( int counter = 0; counter < array.length; counter ++){	System.out.printf("%5d %4s %4d \n", counter, "=>" , array[ counter ]); } }
		
		String[] estados = {"ALE", "GOMES", "Jardim"};
		
		for( int counter = 0; counter < estados.length; counter ++){	
			System.out.println(counter+ "=>" + estados[ counter ]); 
		}

		
			List<String> supplierNames = Arrays.asList("sup1", "sup2", "sup3");
			System.out.println(supplierNames.indexOf("sup2"));
		
		
	}

		
}
