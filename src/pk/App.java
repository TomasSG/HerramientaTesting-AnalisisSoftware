package pk;

import java.util.ArrayList;

public class App {
	// PATHS
	private static final String PATH_PRUEBA = "D:\\Documentos\\Programacion\\Java\\Triangulo\\src\\pk\\Triangulo.java";

	public static void main(String args[]) {
		FileManager file = new FileManager();
		ArrayList<String> array = file.leerArchivo(PATH_PRUEBA);
		Clase clase = null;
		
		
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
					// Terminamos de setear el codigo y a�adimos le metodo a la clase
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
				metodo = new Metodo(Parser.getNombreMetodo(linea));
				
				// Inicializamos las variables una vez que encontramos el codigo
				encontreMetodo = true;
				contadorLlaves = 1;
				continue;
			}
			
		}
	
		
		System.out.println(clase.getNombre());
		Metodo metodoPrueba = clase.getMetodos().get(0);
		metodoPrueba.analisisMetodo();
		System.out.println("Cantidad de l�neas totales = " + metodoPrueba.getCantidadLineasTotales());
		System.out.println("Cantidad de l�neas solo codigo = " + metodoPrueba.getCantidadLinaesSoloCodigo());
		System.out.println("Cantidad de l�neas comentadas = " + metodoPrueba.getCantidaLineasComentadas());
		System.out.println("Cantidad de l�neas en blanco = " + metodoPrueba.getCantidadLineasBlanco());
		System.out.println("porcentaje de comentarios = " + metodoPrueba.getPorcentajeComentarios());
	}
}
