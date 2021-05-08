package pk;

public class Parser {
	
	private static final String SPERADAOR_PALABRAS = " ";
	
	// Elementos del lenguaje
	private static final String PARENTESIS_ABIERTO = "(";
	private static final String PARENTESIS_CERRADO = ")";
	
	public static String getNombreClase(String string) {
		String[] palabras = string.split(SPERADAOR_PALABRAS);
		return palabras[palabras.length - 2];
	}
	
	public static String getNombreMetodo(String string) {
		String[] palabras = string.split(SPERADAOR_PALABRAS);
		
		// Primero buscamos en que elemento aparece el (, el nombre del método esta a la izquierda del paréntesis.
		for(String palabra : palabras) { 
			if(palabra.contains(PARENTESIS_ABIERTO)) {
				
				// Aca encontramos algo como Triangulo(), obtenemos el nombre dle metodo nada mas
				return palabra.substring(0, palabra.indexOf(PARENTESIS_ABIERTO));
			}
		}
		return null;
	}
}
