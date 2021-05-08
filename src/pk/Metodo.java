package pk;

import java.util.ArrayList;

public class Metodo {
	
	// CONSTANTES
	private static final String INICIO_COMENTARIO_UNA_LINEA = "//";
	private static final String INICIO_COMENTARIO_MULTI_LINEA = "/*";
	private static final String CIERRE_COMENTARIO_MULTI_LINEA = "*/";
	
	private static final String SALTO_LINEA = "\n";
	private static final String LINEA_VACIA = "";
	private static final String LINEA_BLANCO = " ";
	
	// Metadatos del metodo
	private String nombre;
	private ArrayList<String> codigo;
	
	// Valores a calcular
	private int cantidadLineasTotales;
	private int cantidadLinaesSoloCodigo;
	private int cantidaLineasComentadas;
	private int cantidadLineasBlanco;
	private double porcentajeComentarios;
	private int complejidadCiclomática;
	private int fanIn;
	private int fanOut;
	private int halsteadLongitud;
	private int halsteadVolumen;
	
	
	public Metodo(String nombre) {
		this.nombre = nombre;
		this.codigo = null;
		this.reestablecerValores();
	}
	
	public void analisisMetodo() {
		
		this.reestablecerValores();

		// Inicialización variables que vamos a usar
		boolean encontreComentarioMultiliena = false;
		// Sumamos uno porque tomamos el propio encabezado como una linea más de codigo
		this.cantidadLineasTotales = this.codigo.size() + 1;
		
		for(String linea: this.codigo) {
			
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
			
		}
		
		
		this.cantidadLinaesSoloCodigo = this.cantidadLineasTotales - (this.cantidadLineasBlanco + this.cantidaLineasComentadas);
		this.porcentajeComentarios = this.cantidaLineasComentadas / (double) this.cantidadLineasTotales;
	}
	
	private boolean esLineaEnBlanco(String string) {
		if(string.equals(LINEA_BLANCO) || string.equals(LINEA_VACIA) || string.equals(SALTO_LINEA)) {
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
	}
	
	
	@Override
	public String toString() {
		String resultado = "Nombre Método = " + this.nombre;
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

	public int getHalsteadVolumen() {
		return halsteadVolumen;
	}

	public void setHalsteadVolumen(int halsteadVolumen) {
		this.halsteadVolumen = halsteadVolumen;
	}

	
	
	
	
	
}
