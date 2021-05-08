package pk;

import java.util.ArrayList;
import java.util.HashMap;

public class Metodo {
	
	// CONSTANTES
	private static final String INICIO_COMENTARIO_UNA_LINEA = "//";
	private static final String INICIO_COMENTARIO_MULTI_LINEA = "/*";
	private static final String CIERRE_COMENTARIO_MULTI_LINEA = "*/";
	
	private static final String SALTO_LINEA = "\n";
	private static final String LINEA_VACIA = "";
	private static final String ESPACIO = " ";
	
	// OPERADORES HALSTEAD
	private static final String OPERADOR_LOGICO_AND = "&&";
	private static final String OPERADOR_LOGICO_OR = "||";
	private static final String OPERADOR_LOGICO_MAYOR = ">";
	private static final String OPERADOR_LOGICO_MAYOR_IGUAL = ">=";
	private static final String OPERADOR_LOGICO_MENOR = "<";
	private static final String OPERADOR_LOGICO_MENOR_IGUAL = "<=";
	private static final String OPERADOR_LOGICO_IGUAL = "==";
	private static final String OPERADOR_LOGICO_DISTINTO_IGUAL = "!=";
	
	private static final String OPERADOR_SUMA = "+";
	private static final String OPERADOR_RESTA = "-";
	private static final String OPERADOR_DIVISION = "/";
	private static final String OPERADOR_MULTIPLICACION = "*";
	private static final String OPERADOR_ASIGNACION = "=";
	
	private static final String OPERADOR_INT = "INT";
	private static final String OPERADOR_FLOAT = "FLOAT";
	private static final String OPERADOR_DOUBLE = "DOUBLE";
	
	private static final String OPERADOR_PUBLIC = "PUBLIC";
	private static final String OPERADOR_STATIC = "STATIC";
	private static final String OPERADOR_VOID = "VOID";
	
	private static final String OPERADOR_IF = "IF";
	private static final String OPERADOR_WHILE = "WHILE";
	private static final String OPERADOR_ELSE = "ELSE";
	private static final String OPERADOR_CASE = "CASE";
	private static final String OPERADOR_DEFAULT = "DEFAULT";
	private static final String OPERADOR_FOR = "FOR";
	private static final String OPERADOR_CATCH = "CATCH";
	private static final String OPERADOR_THROW = "THROW";
	
	// METADATOS
	private String nombre;
	private ArrayList<String> codigo;
	
	// METRICAS
	private int cantidadLineasTotales;
	private int cantidadLinaesSoloCodigo;
	private int cantidaLineasComentadas;
	private int cantidadLineasBlanco;
	private double porcentajeComentarios;
	private int complejidadCiclomática;
	private int fanIn;
	private int fanOut;
	private int halsteadLongitud;
	private double halsteadVolumen;
	
	private HashMap<String, Integer> operadores;
	private HashMap<String, Integer> operandos;
	
	private Clase clase;
	
	public Metodo(String nombre, Clase clase) {
		this.nombre = nombre;
		this.codigo = null;
		this.clase = clase;
		this.reestablecerValores();
	}
	
	public void analizarMetodo() {
		
		this.reestablecerValores();
		
		// Inicialización variables que vamos a usar
		boolean encontreComentarioMultiliena = false;
		this.cantidadLineasTotales = this.codigo.size();
		
		for(String linea: this.codigo) {
			
			// No es lo optimo, pero es para simplificar la lógica
			this.buscarOperadoresOperandos(linea);
			
			if(encontreComentarioMultiliena) {
				this.cantidaLineasComentadas++;
				if(linea.contains(CIERRE_COMENTARIO_MULTI_LINEA)) {
					encontreComentarioMultiliena = false;
				}
				continue;
			}
			
			if(linea.contains(INICIO_COMENTARIO_UNA_LINEA)) {
				this.cantidaLineasComentadas++;
			}
			
			if(linea.contains(INICIO_COMENTARIO_MULTI_LINEA)) {
				this.cantidaLineasComentadas++;
				encontreComentarioMultiliena = true;
				continue;
			}
			
			if(this.esLineaEnBlanco(linea)) {
				this.cantidadLineasBlanco++;
				continue;
			}
			
			if(this.esDecision(linea)) {
				this.complejidadCiclomática += this.contarCantidadOcurrencias(linea, OPERADOR_LOGICO_AND) + 
						this.contarCantidadOcurrencias(linea, OPERADOR_LOGICO_OR) + 1; 
				continue;
				
			}
		}
		
		// Sumamos porque V(G) = P + 1
		this.complejidadCiclomática++;
		
		this.cantidadLinaesSoloCodigo = this.cantidadLineasTotales - (this.cantidadLineasBlanco + this.cantidaLineasComentadas);
		
		this.porcentajeComentarios = this.cantidaLineasComentadas / (double) this.cantidadLineasTotales;
		
		// Calculos relacionados con hasltead
		int n1 = this.operadores.keySet().size();
		int N1 = 0;
		for(Integer valor : operadores.values()) {
			N1 += valor;
		}
		int n2 = this.operandos.keySet().size();
		int N2 = 0;
		for(Integer valor : operandos.values()) {
			N2 += valor;
		}
		
		this.halsteadLongitud = N1 + N2;
		this.halsteadVolumen = this.halsteadLongitud * (Math.log(n1 + n2) / Math.log(2));
		
		this.calcularFanIn();
		this.calcularFanOut();
	}

	// Fan in: Cantidad de veces que un metodo es llamado
	private void calcularFanIn() {
		for(Metodo metodo : clase.getMetodos()) {
			if(metodo.getNombre().equals(this.nombre)) {
				continue;
			}
			
			for(String linea : metodo.getCodigo()) {
				if(linea.contains(this.nombre)) {
					this.fanIn++;
				}
			}
		}
	}
	
	// Fan out: Cantidad de veces que una funcion llama a otras funciones
	private void calcularFanOut() {
		for(String linea : this.codigo) {
			for(Metodo metodo : this.clase.getMetodos()) {
				
				if(metodo.getNombre().equals(this.nombre)) {
					continue;
				}
				
				if(metodo.getNombre().equals(this.clase.getNombre())) {
					continue;
				}
				
				if(linea.contains(metodo.getNombre())) {
					this.fanOut++;
				}
			}
		}
	}
	
	private void buscarOperadoresOperandos(String linea) {
		String[] palabras = linea.replace("(", " ( ").replace(")", " ) ").replace(",", " , ").split(ESPACIO);
		for(String palabra : palabras) {
		
			if(this.esOperadorHalstead(palabra)) {
				
				if(this.operadores.containsKey(palabra)) {
					this.operadores.put(palabra, this.operadores.get(palabra) + 1);
				} else {
					this.operadores.put(palabra, 1);
				}			
			} else {
				if(this.operandos.containsKey(palabra)) {
					this.operandos.put(palabra, this.operandos.get(palabra) + 1);
				} else {
					this.operandos.put(palabra, 1);
				}
			}
		}
	}
	
	
	private int contarCantidadOcurrencias(String linea, String palabraObj) {
		int contador = 0;
		String[] palabras = linea.split(ESPACIO);
		
		for(int i = 0; i < palabras.length; i++) {
			if(palabraObj.equals(palabras[i])) {
				contador++;
			}
		}
		return contador;
	}
	
	private boolean esLineaEnBlanco(String string) {
		if(string.equals(ESPACIO) || string.equals(LINEA_VACIA) || string.equals(SALTO_LINEA)) {
			return true;
		}
		return false;
	}
	
	private boolean esDecision(String string) {
		if(string.toUpperCase().contains(OPERADOR_IF) || string.toUpperCase().contains(OPERADOR_WHILE)) {
			return true;
		}
		return false;
	}
	
	private boolean esOperadorHalstead(String string) {
		if(string.equals(OPERADOR_LOGICO_AND) || string.equals(OPERADOR_LOGICO_OR) || string.equals(OPERADOR_LOGICO_MAYOR) ||
				string.equals(OPERADOR_LOGICO_MAYOR) || string.equals(OPERADOR_LOGICO_MAYOR_IGUAL) || string.equals(OPERADOR_LOGICO_MENOR) ||
				string.equals(OPERADOR_LOGICO_MENOR_IGUAL) || string.equals(OPERADOR_LOGICO_IGUAL) || string.equals(OPERADOR_LOGICO_DISTINTO_IGUAL)) {
			return true;
		}
		
		if(string.equals(OPERADOR_SUMA) || string.equals(OPERADOR_RESTA) || string.equals(OPERADOR_DIVISION) || string.equals(OPERADOR_MULTIPLICACION) ||
				string.equals(OPERADOR_ASIGNACION)) {
			return true;
		}
		
		if(string.toUpperCase().equals(OPERADOR_INT) || string.toUpperCase().equals(OPERADOR_FLOAT) || string.toUpperCase().equals(OPERADOR_DOUBLE)  ) {
			return true;
		}
		
		if(string.toUpperCase().equals(OPERADOR_PUBLIC) || string.toUpperCase().equals(OPERADOR_STATIC) || 
				string.toUpperCase().equals(OPERADOR_VOID)) {
			return true;
		}
		
		if(string.toUpperCase().equals(OPERADOR_IF) || string.toUpperCase().equals(OPERADOR_WHILE) || string.toUpperCase().equals(OPERADOR_ELSE)|| 
				string.toUpperCase().equals(OPERADOR_CASE) || string.toUpperCase().equals(OPERADOR_DEFAULT) || string.toUpperCase().equals(OPERADOR_FOR) ||
				string.toUpperCase().equals(OPERADOR_CATCH) || string.toUpperCase().equals(OPERADOR_THROW)) {
			return true;
		}
		
		return false;
	}
	
	private void reestablecerValores() {
		this.cantidadLineasTotales = 0;
		this.cantidadLinaesSoloCodigo = 0;
		this.cantidaLineasComentadas = 0;
		this.cantidadLineasBlanco = 0;
		this.porcentajeComentarios = 0;
		this.complejidadCiclomática = 0;
		this.fanIn = 0;
		this.fanOut = 0;
		this.halsteadLongitud = 0;
		this.halsteadVolumen = 0;
		
		this.operadores = new HashMap<>();
		this.operandos = new HashMap<>();
	}
	
	
	@Override
	public String toString() {
		String resultado = "";
		for(String linea : this.codigo) {
			resultado += "\n " + linea;
		}
		
		return resultado;
	}
	
	// SECCIÓN GETTERS AND SETTERS
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getCodigo() {
		return codigo;
	}

	public void setCodigo(ArrayList<String> codigo) {
		this.codigo = (ArrayList<String>) codigo.clone();
	}

	public int getCantidadLineasTotales() {
		return cantidadLineasTotales;
	}

	public void setCantidadLineasTotales(int cantidadLineasTotales) {
		this.cantidadLineasTotales = cantidadLineasTotales;
	}

	public int getCantidadLinaesSoloCodigo() {
		return cantidadLinaesSoloCodigo;
	}

	public void setCantidadLinaesSoloCodigo(int cantidadLinaesSoloCodigo) {
		this.cantidadLinaesSoloCodigo = cantidadLinaesSoloCodigo;
	}

	public int getCantidaLineasComentadas() {
		return cantidaLineasComentadas;
	}

	public void setCantidaLineasComentadas(int cantidaLineasComentadas) {
		this.cantidaLineasComentadas = cantidaLineasComentadas;
	}

	public int getCantidadLineasBlanco() {
		return cantidadLineasBlanco;
	}

	public void setCantidadLineasBlanco(int cantidadLienasBlanco) {
		this.cantidadLineasBlanco = cantidadLienasBlanco;
	}

	public double getPorcentajeComentarios() {
		return porcentajeComentarios;
	}

	public void setPorcentajeComentarios(float porcentajeComentarios) {
		this.porcentajeComentarios = porcentajeComentarios;
	}

	public int getComplejidadCiclomática() {
		return complejidadCiclomática;
	}

	public void setComplejidadCiclomática(int complejidadCiclomática) {
		this.complejidadCiclomática = complejidadCiclomática;
	}

	public int getFanIn() {
		return fanIn;
	}

	public void setFanIn(int fanIn) {
		this.fanIn = fanIn;
	}

	public int getFanOut() {
		return fanOut;
	}

	public void setFanOut(int fanOut) {
		this.fanOut = fanOut;
	}

	public int getHalsteadLongitud() {
		return halsteadLongitud;
	}

	public void setHalsteadLongitud(int halsteadLongitud) {
		this.halsteadLongitud = halsteadLongitud;
	}

	public double getHalsteadVolumen() {
		return halsteadVolumen;
	}

	public void setHalsteadVolumen(int halsteadVolumen) {
		this.halsteadVolumen = halsteadVolumen;
	}

	public HashMap<String, Integer> getOperadores() {
		return operadores;
	}

	public void setOperadores(HashMap<String, Integer> operadores) {
		this.operadores = operadores;
	}

	public HashMap<String, Integer> getOperandos() {
		return operandos;
	}

	public void setOperandos(HashMap<String, Integer> operandos) {
		this.operandos = operandos;
	}

	public void setPorcentajeComentarios(double porcentajeComentarios) {
		this.porcentajeComentarios = porcentajeComentarios;
	}

	public void setHalsteadVolumen(double halsteadVolumen) {
		this.halsteadVolumen = halsteadVolumen;
	}

	
}
