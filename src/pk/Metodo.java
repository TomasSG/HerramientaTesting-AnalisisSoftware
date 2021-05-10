package pk;

import java.util.ArrayList;
import java.util.HashMap;

public class Metodo {
	
	// DATOS SOBRE EL METODO
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
	
	// PARA CALCULAR HALSTEAD
	private HashMap<String, Integer> operadores;
	private HashMap<String, Integer> operandos;
	
	// PARA TENER REFERENCIA A QUE CLASE PERTENECE
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
				if(linea.contains(Constantes.CIERRE_COMENTARIO_MULTI_LINEA)) {
					encontreComentarioMultiliena = false;
				}
				continue;
			}
			
			if(linea.contains(Constantes.INICIO_COMENTARIO_UNA_LINEA)) {
				this.cantidaLineasComentadas++;
			}
			
			if(linea.contains(Constantes.INICIO_COMENTARIO_MULTI_LINEA)) {
				this.cantidaLineasComentadas++;
				encontreComentarioMultiliena = true;
				continue;
			}
			
			if(Verificador.esLineaEnBlanco(linea)) {
				this.cantidadLineasBlanco++;
				continue;
			}
			
			if(Verificador.esDecision(linea)) {
				this.complejidadCiclomática += this.contarCantidadOcurrencias(linea, Constantes.OPERADOR_LOGICO_AND) + 
						this.contarCantidadOcurrencias(linea, Constantes.OPERADOR_LOGICO_OR) + 1; 
				continue;
				
			}
		}
	
		// Sumamos porque V(G) = P + 1
		this.complejidadCiclomática++;
	
		this.cantidadLinaesSoloCodigo = this.cantidadLineasTotales - (this.cantidadLineasBlanco + this.cantidaLineasComentadas);
		
		this.porcentajeComentarios = 100 * this.cantidaLineasComentadas / (double) this.cantidadLineasTotales;
		
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

	// Fan in: Cantidad de veces que un metodo X es llamado por otros.
	private void calcularFanIn() {
		this.fanIn = 0;
		for(Metodo metodo : clase.getMetodos()) {
			
			// Para no tener en cuenta el mismo metodo
			if(metodo.getNombre().equals(this.nombre)) {
				continue;
			}
			
			for(String linea : metodo.getCodigo()) {
				if(linea.contains(this.nombre) && Verificador.contieneParentesisCerradosAbiertos(linea)) {
					this.fanIn++;
				}
			}
		}
	}
	
	// Fan out: Cantidad de veces que un método X llama a otros.
	private void calcularFanOut() {
		this.fanOut = 0;
		for(String linea : this.codigo) {
			for(Metodo metodo : this.clase.getMetodos()) {
				
				// Para no tener en cuenta el mismo metodo
				if(metodo.getNombre().equals(this.clase.getNombre())) {
					continue;
				}
				
				// Para que no tenga en cuenta el encabezado
				if(Verificador.esEncabezadoMetodo(linea)) {
					continue;
				}
				
				if(linea.contains(metodo.getNombre()) && Verificador.contieneParentesisCerradosAbiertos(linea)) {
					System.out.println(linea);
					this.fanOut++;
				}
			}
		}
	}
	
	private void buscarOperadoresOperandos(String linea) {
		String[] palabras = linea.replace("(", " ( ").replace(")", " ) ").replace(",", " , ").split(Constantes.ESPACIO);
		for(String palabra : palabras) {
		
			if(Verificador.esOperadorHalstead(palabra)) {
				
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
		String[] palabras = linea.split(Constantes.ESPACIO);
		
		for(int i = 0; i < palabras.length; i++) {
			if(palabraObj.equals(palabras[i])) {
				contador++;
			}
		}
		return contador;
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
