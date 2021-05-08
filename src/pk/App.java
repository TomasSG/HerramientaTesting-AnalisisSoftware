package pk;

import java.util.ArrayList;

public class App {
	// PATHS
	private static final String PATH_PRUEBA = "D:\\Documentos\\Programacion\\Java\\Triangulo\\src\\pk\\Triangulo.java";

	// REGEX
	private static final String REGEX_METODO = "";

	public static void main(String args[]) {
		FileManager file = new FileManager();
		ArrayList<String> array = file.leerArchivo(PATH_PRUEBA);
		Clase clase = null;
		
		for(String linea : array) {
			if(Verificador.esEncabezadoClase(linea)) {
				clase = new Clase(Verificador.getNombreClase(linea));
			} else if(Verificador.esEncabezadoMetodo(linea))
		}
	
		
		System.out.println(clase.getNombre());
	}
}
