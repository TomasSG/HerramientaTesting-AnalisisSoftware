package pk;

import java.util.ArrayList;

public class Metodo {
	
	private String nombre;
	private ArrayList<String> codigo;
	
	public Metodo(String nombre) {
		this.nombre = nombre;
		this.codigo = null;
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
	
	
	
}
