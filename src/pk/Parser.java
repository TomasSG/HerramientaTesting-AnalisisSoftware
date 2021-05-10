package pk;

public class Parser {
	
	public static String getNombreClase(String string) {
		String[] palabras = string.split(Constantes.ESPACIO);
		return palabras[palabras.length - 2];
	}
	
	public static String getNombreMetodo(String string) {
		String[] palabras = string.split(Constantes.ESPACIO);
		
		// Primero buscamos en que elemento aparece el (, el nombre del método esta a la izquierda del paréntesis.
		for(String palabra : palabras) { 
			if(palabra.contains(Constantes.PARENTESIS_ABIERTO)) {
				
				// Aca encontramos algo como Triangulo(), nos quedamos con el nombre nada más
				return palabra.substring(0, palabra.indexOf(Constantes.PARENTESIS_ABIERTO));
			}
		}
		return null;
	}
}
