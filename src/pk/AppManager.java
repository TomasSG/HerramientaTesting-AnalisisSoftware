package pk;

import java.util.ArrayList;

public class AppManager {
	
	private Clase clase;
	
	public AppManager() {
		clase = null;
	}

	public void iniciarAplicacion(String path) {
		
		FileManager file = new FileManager();
		ArrayList<String> array = file.leerArchivo(path);
		
		
		int contadorLlaves = 0;
		boolean encontreMetodo = false;
		ArrayList<String> codigoMetodo = new ArrayList<String>();
		Metodo metodo = null;
		
		for(String linea : array) {
			
			if(encontreMetodo) {
				
				if(linea.contains("{")) {
					contadorLlaves++;
				}
				
				if(linea.contains("}")) {
					contadorLlaves--;
				}
				
				codigoMetodo.add(linea);
				
				if(contadorLlaves == 0) {
					// Terminamos de setear el codigo y añadimos le metodo a la clase
					metodo.setCodigo(codigoMetodo);
					clase.agregarMetodo(metodo);
					
					// Reiniciamos las variables
					encontreMetodo = false;
					codigoMetodo.clear();
				
				}
				
				continue;
			}
				
			
			if(Verificador.esEncabezadoClase(linea)) {
				clase = new Clase(Parser.getNombreClase(linea));
				continue;
			}
			
			if(Verificador.esEncabezadoMetodo(linea)) {
				metodo = new Metodo(Parser.getNombreMetodo(linea), clase);
				codigoMetodo.add(linea);
				
				// Inicializamos las variables una vez que encontramos el codigo
				encontreMetodo = true;
				contadorLlaves = 1;
				continue;
			}
			
		}
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}
	
	
}
