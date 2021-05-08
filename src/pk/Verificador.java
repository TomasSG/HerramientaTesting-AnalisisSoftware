package pk;

public class Verificador {
	
	// Algunas palabras reservadas
	
	// Visibilidad
	static final String PALABRA_PUBLIC = "public";
	static final String PALABRA_PRIVATE = "private";
	static final String PALABRA_PROTECTED = "protected";
	
	static final String PALABRA_CLASE = "class";
	
	
	public static boolean esEncabezadoClase(String string) {
		if(string.contains(PALABRA_CLASE)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean esEncabezadoMetodo(String string) {
		
	}
	
	private boolean contieneVisibilidad(String string) {
		if(string.contains(PALABRA_PRIVATE) || string.contains(PALABRA_PUBLIC) || string.contains(PALABRA_PROTECTED)) {
			return true;
		}
		
		return false;
	}
	
	public static String getNombreClase(String string) {
		String[] palabras = string.split(" ");
		return palabras[palabras.length - 2];
	}

}
