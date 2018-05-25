package gt.edu.umg.compiladores.imt.controller.analizador;

import java.util.List;

import gt.edu.umg.compiladores.imt.dto.Tipos;
import gt.edu.umg.compiladores.imt.dto.Token;

public class Sintactico {

	private static final Tipos[] diccionario = {
		Tipos.VARIABLE,
		Tipos.ASIGNADOR,
		Tipos.APOSTROFE,
		Tipos.TERMINAL,
		Tipos.OPCIONALIDAD,
		Tipos.EPSILON,
		Tipos.LINEANUEVA
	};
	
	private static final int[][] grafo = {
			{1,	1,	1,	0,	1,	0,	1},
			{1,	0,	1,	0,	0,	1,	0},
			{1,	0,	1,	1,	1,	1,	1},
			{0,	0,	1,	1,	0,	0,	0},
			{1,	0,	1,	0,	0,	1,	0},
			{0,	0,	1,	0,	1,	1,	1},
			{1,	0,	0,	0,	0,	0,	1}
	};
	
	public static void analizar(List<Token> tokens) {
		int posTokenActual = -1;
		int posTokenAnterior = -1;
		int linea = 1;
		boolean error = false;
		boolean asignacionProd = false;
		boolean apostrofeIni = false;
		
		System.out.println("Analisis Sintactico: ");
		
		for(Token token: tokens) {
			posTokenActual = posicionDiccionario(token.getTipo());
			System.out.print(posTokenActual+"["+token.getTipo()+"]"+",");
			if(posTokenAnterior >= 0) {
				
				if(grafo[posTokenAnterior][posTokenActual] == 0) {
					error = true;
				}
				
				if(Tipos.LINEANUEVA.equals(token.getTipo())) {
					if(posTokenAnterior == 0 && !asignacionProd) {//Error de sintaxis si es una linea nueva y no se asigno regla a produccion
						error = true;
					} else {
						linea++;
						System.out.println();
						asignacionProd = false;
					}
				} else if(Tipos.ASIGNADOR.equals(token.getTipo()) && posTokenAnterior == 0) {//Inicia regla de produccion
					asignacionProd = true;
				} else if(Tipos.VARIABLE.equals(token.getTipo())) {
					if(posTokenAnterior == 0 && !asignacionProd) {//Error de sintaxis si dos variables estan juntas y no es regla de produccion
						error = true;
					}else if(posTokenAnterior == 3) {//Error de sintaxis dos terminales estan juntos sin utilizar apostrofe 
						error = true;
					}
					
				} else if(Tipos.APOSTROFE.equals(token.getTipo())) {
					if(!apostrofeIni) {
						apostrofeIni = true;//abre apostrofe para colocar terminal(es)
						
						if(posTokenAnterior == 0 && !asignacionProd) {//Error de sintaxis si viene un apostrofe y no se ha inisiado la regla de produccion con los dos puntos
							error = true;
						}
						
					} else if(posTokenAnterior != 3) {//Error de sintaxis si se abrio comilla simple y no viene una terminal
						error = true;
					} else {
						apostrofeIni = false;//cierra apostrofe para colocar terminal(es)
					}
				}
				
			} else if(posTokenActual != 0) {//Error de sintaxis si el primer token de la primer linea no es una variable
				error = true;
			}
			
			if(error) {
				System.out.println();
				throw new RuntimeException("Error de sintaxis cerca de: " + token.getValor() + " en linea: " + linea);
			}
			
			posTokenAnterior = posTokenActual;
		}
	}
	
	private static int posicionDiccionario(Tipos tokenTipo) {
		int posicion = -1;
		for(int i = 0; i < diccionario.length; i++) {
			if(diccionario[i].equals(tokenTipo)) {
				return i;
			}
		}
		return posicion;
	}
}
