package pk;

public class Constantes {
	
	public static final String INICIO_COMENTARIO_UNA_LINEA = "//";
	public static final String INICIO_COMENTARIO_MULTI_LINEA = "/*";
	public static final String CIERRE_COMENTARIO_MULTI_LINEA = "*/";
	
	public static final String SALTO_LINEA = "\n";
	public static final String LINEA_VACIA = "";
	public static final String ESPACIO = " ";
	
	public static final String PARENTESIS_ABIERTO = "(";
	public static final String PARENTESIS_CERRADO = ")";
	
	// PALABRAS RESERVADAS
	public static final String PALABRA_PUBLIC = "public";
	public static final String PALABRA_PRIVATE = "private";
	public static final String PALABRA_PROTECTED = "protected";
	public static final String PALABRA_CLASE = "class";
	
	// OPERADORES HALSTEAD
	public static final String OPERADOR_LOGICO_AND = "&&";
	public static final String OPERADOR_LOGICO_OR = "||";
	public static final String OPERADOR_LOGICO_MAYOR = ">";
	public static final String OPERADOR_LOGICO_MAYOR_IGUAL = ">=";
	public static final String OPERADOR_LOGICO_MENOR = "<";
	public static final String OPERADOR_LOGICO_MENOR_IGUAL = "<=";
	public static final String OPERADOR_LOGICO_IGUAL = "==";
	public static final String OPERADOR_LOGICO_DISTINTO_IGUAL = "!=";
	
	public static final String OPERADOR_SUMA = "+";
	public static final String OPERADOR_RESTA = "-";
	public static final String OPERADOR_DIVISION = "/";
	public static final String OPERADOR_MULTIPLICACION = "*";
	public static final String OPERADOR_ASIGNACION = "=";
	
	public static final String OPERADOR_INT = "INT";
	public static final String OPERADOR_FLOAT = "FLOAT";
	public static final String OPERADOR_DOUBLE = "DOUBLE";
	
	public static final String OPERADOR_PUBLIC = "PUBLIC";
	public static final String OPERADOR_STATIC = "STATIC";
	public static final String OPERADOR_VOID = "VOID";
	
	public static final String OPERADOR_IF = "IF";
	public static final String OPERADOR_WHILE = "WHILE";
	public static final String OPERADOR_ELSE = "ELSE";
	public static final String OPERADOR_CASE = "CASE";
	public static final String OPERADOR_DEFAULT = "DEFAULT";
	public static final String OPERADOR_FOR = "FOR";
	public static final String OPERADOR_CATCH = "CATCH";
	public static final String OPERADOR_THROW = "THROW";
	
	// VALORES NUMERICOS
	public static final int VALOR_MAXIMO_COMPLEJIDAD_CICLOMATICA = 10;
	public static final int VALOR_MINIMO_PORCENTAJE_LINEAS_COMENTADAS = 10;
	public static final int VALOR_MAXIMO_FAN_IN = 6;
	public static final int VALOR_MAXIMO_FAN_OUT = 6;
}
