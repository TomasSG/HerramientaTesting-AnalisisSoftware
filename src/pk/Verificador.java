package pk;

public class Verificador {

	
	
	
	/*
	 * Tomamos que todos los encabezados de la clase son los que tienen la palabra "class"
	 */
	
	public static boolean esEncabezadoClase(String string) {
		if(string.contains(Constantes.PALABRA_CLASE)) {
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
	
	public static boolean contieneVisibilidad(String string) {
		if(string.contains(Constantes.PALABRA_PRIVATE) || string.contains(Constantes.PALABRA_PUBLIC) || 
				string.contains(Constantes.PALABRA_PROTECTED)) {
			return true;
		}
		return false;
	}
	
	public static boolean contieneParentesisCerradosAbiertos(String string) {
		if(string.contains(Constantes.PARENTESIS_ABIERTO) && string.contains(Constantes.PARENTESIS_CERRADO)) {
			return true;
		}
		return false;
	}
	
	public static boolean esLineaEnBlanco(String string) {
		if(string.equals(Constantes.ESPACIO) || string.equals(Constantes.LINEA_VACIA) || string.equals(Constantes.SALTO_LINEA)) {
			return true;
		}
		return false;
	}
	
	public static boolean esDecision(String string) {
		if(string.toUpperCase().contains(Constantes.OPERADOR_IF) || string.toUpperCase().contains(Constantes.OPERADOR_WHILE)) {
			return true;
		}
		return false;
	}
	
	public static boolean esOperadorHalstead(String string) {
		if(string.equals(Constantes.OPERADOR_LOGICO_AND) || string.equals(Constantes.OPERADOR_LOGICO_OR) || 
				string.equals(Constantes.OPERADOR_LOGICO_MAYOR) ||	string.equals(Constantes.OPERADOR_LOGICO_MAYOR) || 
				string.equals(Constantes.OPERADOR_LOGICO_MAYOR_IGUAL) || string.equals(Constantes.OPERADOR_LOGICO_MENOR) ||
				string.equals(Constantes.OPERADOR_LOGICO_MENOR_IGUAL) || string.equals(Constantes.OPERADOR_LOGICO_IGUAL) || 
				string.equals(Constantes.OPERADOR_LOGICO_DISTINTO_IGUAL)) {
			return true;
		}
		
		if(string.equals(Constantes.OPERADOR_SUMA) || string.equals(Constantes.OPERADOR_RESTA) ||
				string.equals(Constantes.OPERADOR_DIVISION) || string.equals(Constantes.OPERADOR_MULTIPLICACION) ||
				string.equals(Constantes.OPERADOR_ASIGNACION)) {
			return true;
		}
		
		if(string.toUpperCase().equals(Constantes.OPERADOR_INT) || 
				string.toUpperCase().equals(Constantes.OPERADOR_FLOAT) || 
				string.toUpperCase().equals(Constantes.OPERADOR_DOUBLE)  ) {
			return true;
		}
		
		if(string.toUpperCase().equals(Constantes.OPERADOR_PUBLIC) || string.toUpperCase().equals(Constantes.OPERADOR_STATIC) || 
				string.toUpperCase().equals(Constantes.OPERADOR_VOID)) {
			return true;
		}
		
		if(string.toUpperCase().equals(Constantes.OPERADOR_IF) || string.toUpperCase().equals(Constantes.OPERADOR_WHILE) || 
				string.toUpperCase().equals(Constantes.OPERADOR_ELSE)|| string.toUpperCase().equals(Constantes.OPERADOR_CASE) || 
				string.toUpperCase().equals(Constantes.OPERADOR_DEFAULT) || string.toUpperCase().equals(Constantes.OPERADOR_FOR) ||
				string.toUpperCase().equals(Constantes.OPERADOR_CATCH) || string.toUpperCase().equals(Constantes.OPERADOR_THROW)) {
			return true;
		}
		
		return false;
	}

}
