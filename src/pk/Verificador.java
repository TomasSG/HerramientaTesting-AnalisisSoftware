package pk;

public class Verificador {
	
	// Algunas palabras reservadas
	
	// Visibilidad
	private static final String PALABRA_PUBLIC = "public";
	private static final String PALABRA_PRIVATE = "private";
	private static final String PALABRA_PROTECTED = "protected";
	
	// Elementos del lenguaje
	private static final String PARENTESIS_ABIERTO = "(";
	private static final String PARENTESIS_CERRADO = ")";
	
	private static final String PALABRA_CLASE = "class";
	
	
	/*
	 * Tomamos que todos los encabezados de la clase son los que tienen la palabra "class"
	 */
	
	public static boolean esEncabezadoClase(String string) {
		if(string.contains(PALABRA_CLASE)) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Tomamos que todos los encabezados de métodos tienen una palabra de visibilidad y los paréntesis
	 * abiertos y cerrados.
	 */
	
	public static boolean esEncabezadoMetodo(String string) {
		if(Verificador.contieneVisibilidad(string) && Verificador.contieneParentesisCerradosAbiertos(string)) {
			return true;
		}
		
		return false;
	}
	
	private static boolean contieneVisibilidad(String string) {
		if(string.contains(PALABRA_PRIVATE) || string.contains(PALABRA_PUBLIC) || string.contains(PALABRA_PROTECTED)) {
			return true;
		}
		return false;
	}
	
	private static boolean contieneParentesisCerradosAbiertos(String string) {
		if(string.contains(PARENTESIS_ABIERTO) && string.contains(PARENTESIS_CERRADO)) {
			return true;
		}
		return false;
	}

}
