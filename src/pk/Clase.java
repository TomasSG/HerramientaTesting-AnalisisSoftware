package pk;

import java.util.ArrayList;

public class Clase {

	private ArrayList<Metodo> metodos;
	private String nombre;
	
	
	public Clase(String nombre) {
		this.metodos = new ArrayList<Metodo>();
		this.nombre = nombre;
	}

	
	public void agregarMetodo(Metodo metodo) {
		this.metodos.add(metodo);
	}
	
	public Metodo buscarMetodo(String nombre) {
		for(Metodo metodo : this.metodos) {
			if(metodo.getNombre().equals(nombre)) {
				return metodo;
			}
		}
		return null;
	}
	
	public void imprimirMetodos() {
		for(Metodo metodo : this.metodos) {
			System.out.println(metodo);
		}
	}
	
	// SECCIÓN GETTERS AND SETTERS
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Metodo> getMetodos() {
		return metodos;
	}

	public void setMetodos(ArrayList<Metodo> metodos) {
		this.metodos = metodos;
	}
	
	
	
}
