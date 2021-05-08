package pk;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileFilter;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Rectangle;

public class InterfazGrafica extends JFrame {

	// CONSTANTES
	private static final String TITULO = "Herramienta Testing - Grupo 03";
	private static final String MSJ_LABEL_LINEAS_TOTALES_CODIGO = "Cantidad de líneas totales";
	private static final String MSJ_LABEL_LINEAS_SOLO_CODIGO = "Cantidad de líneas de solo código";
	private static final String MSJ_LABEL_LINEAS_COMENTADAS = "Cantidad de líneas comentadas";
	private static final String MSJ_LABEL_LINEAS_EN_BLANCO = "Cantidad de líneas en blanco";
	private static final String MSJ_LABEL_PORCENTAJE_DE_COMENTARIOS = "Porcentaje de comentarios (%)";
	private static final String MSJ_LABEL_COMPLEJIDAD_CICLOMATICA = "Comlejidad ciclomatica";
	private static final String MSJ_LABEL_FAN_IN = "Fan in";
	private static final String MSJ_LABEL_FAN_OUT = "Fan out";
	private static final String MSJ_LABEL_HALSTEAD_LONGITUD = "Halstead - longitud";
	private static final String MSJ_LABEL_HALSTEAD_VOLUMEN = "Halstead - volumen";
	private static final String MSJ_LABEL_LISTA_ARCHIVOS = "Archivos Disponibles";
	private static final String MSJ_LABEL_LISTA_METODOS = "Métodos Disponibles";
	private static final String MSJ_LABEL_AREA_CODIGO = "Código del método";
	
	private static final String MSJ_BT_SELECCIONAR_CARPETA = "Seleccionar carpeta";
	
	private static final String MSJ_CERRAR_APLICACION = "Deseas salir de la aplicación?";
	private static final String TITULO_CERRAR_APLICACION = "Confirmar operación";
	private static final String MSJ_ERROR = "Ocurrió un error al realizar la tarea";
	private static final String TITULO_ERROR = "Error";
	private static final String MSJ_SIN_METODOS = "Parece que la clase no tiene metodos, elija otra";
	
	private static final String MSJ_RECOMENDACION_COMPLEJIDAD_CICLOMATICA = "La complejidad Cicclomática es mayor a 10, se recomienda modularizar\n";
	private static final String MSJ_RECOMENDACION_PORCENTAJE_LINEAS_COMENTADAS = "El porcentaje de lineas comentadas es bajo, se recomienda agregar comentarios\n";
	private static final String MSJ_RECOMENDACION_FAN_IN = "Este método tiene un Fan In considerable, se recomienda aplicar técnicas exhaustivas de testo\n";
	private static final String MSJ_RECOMENDACION_FAN_OUT = "Este método tiene un Fan Out considerable, se recomienda tener en cuenta la dependencias de otros métodos\n";

	private static final String STRING_NULA = "";
	private static final String EXTENSION_JAVA = ".java";
	
	private static final String PATH_ICONO_BUSCAR_DIRECTORIO = "/images/folder.png";
	
	private static final int VALOR_MAXIMO_COMPLEJIDAD_CICLOMATICA = 10;
	private static final int VALOR_MINIMO_PORCENTAJE_LINEAS_COMENTADAS = 10;
	private static final int VALOR_MAXIMO_FAN_IN = 6;
	private static final int VALOR_MAXIMO_FAN_OUT = 6;

	// ELEMENTOS DE PANTALLA
	private JTextArea textoCodigo;
	private JList<File> listaArchivos;
	private JList<String> listaMetodos;
	private JButton btnSeleccionarCarpeta;
	private JLabel lblMetodos;
	private JLabel lblArchivos;
	private JPanel contentPane;
	private JTextField textLineasCodigo;
	private JTextField textLineasSoloCodigo;
	private JTextField textLineasComentadas;
	private JTextField textLineasBlanco;
	private JTextField textPorcentajeComentarios;
	private JTextField textHalsteadVolumen;
	private JTextField textHalsteadLongitud;
	private JTextField textFanIn;
	private JTextField textFanOut;
	private JTextField textComplejidadCiclomatica;
	
	private JScrollPane scrollListaArchivos;
	private JScrollPane scrollListaMetodos;
	private JScrollPane scrollTextoCodigo;
	private JScrollPane scrollTextoRecomendaciones;
	
	// ELEMENTOS DE LA LOGICA
	private AppManager appManager;
	private Metodo metodo;
	private JTextArea textoRecomendaciones;
	private JLabel lblAreaRecomendaciones;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazGrafica frame = new InterfazGrafica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazGrafica() {
		
		this.appManager = new AppManager();
		this.metodo = null;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 958, 569);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textoCodigo = new JTextArea();
		textoCodigo.setBounds(10, 120, 925, 170);
		textoCodigo.setEditable(false);
		textoCodigo.setLineWrap(true);
		textoCodigo.setText(STRING_NULA);
		
		textoRecomendaciones = new JTextArea();
		textoRecomendaciones.setText("");
		textoRecomendaciones.setLineWrap(true);
		textoRecomendaciones.setEditable(false);
		textoRecomendaciones.setBounds(12, 455, 923, 64);
		textoRecomendaciones.setText(STRING_NULA);

		listaArchivos = new JList<File>();
		listaArchivos.setBounds(164, 10, 350, 90);


		lblArchivos = new JLabel(MSJ_LABEL_LISTA_ARCHIVOS);
		lblArchivos.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 15));
		lblArchivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblArchivos.setBounds(164, 15, 485, 60);

		lblMetodos = new JLabel(MSJ_LABEL_LISTA_METODOS);
		lblMetodos.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 15));
		lblMetodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMetodos.setBounds(674, 15, 261, 25);

		listaMetodos = new JList<String>();
		listaMetodos.setBounds(550, 10, 350, 90);

		setTitle(TITULO);

		btnSeleccionarCarpeta = new JButton(MSJ_BT_SELECCIONAR_CARPETA);
		btnSeleccionarCarpeta.setFont(new Font("Cambria", Font.PLAIN, 10));
		btnSeleccionarCarpeta.setIcon(new ImageIcon(InterfazGrafica.class.getResource(PATH_ICONO_BUSCAR_DIRECTORIO)));
		btnSeleccionarCarpeta.setBounds(10, 11, 144, 41);
		contentPane.add(btnSeleccionarCarpeta);
		
		scrollListaArchivos = new JScrollPane();
		scrollListaArchivos.setBounds(listaArchivos.getBounds());
		contentPane.add(scrollListaArchivos);
		scrollListaArchivos.setViewportView(listaArchivos);
		scrollListaArchivos.setColumnHeaderView(lblArchivos);
		scrollListaArchivos.setVisible(true);
		
		scrollListaMetodos = new JScrollPane();
		scrollListaMetodos.setBounds(listaMetodos.getBounds());
		contentPane.add(scrollListaMetodos);
		scrollListaMetodos.setViewportView(listaMetodos);
		scrollListaMetodos.setColumnHeaderView(lblMetodos);
		scrollListaMetodos.setVisible(true);
		
		scrollTextoCodigo = new JScrollPane();
		scrollTextoCodigo.setBounds(textoCodigo.getBounds());
		contentPane.add(scrollTextoCodigo);
		scrollTextoCodigo.setViewportView(textoCodigo);
		
		scrollTextoRecomendaciones = new JScrollPane();
		scrollTextoRecomendaciones.setBounds(textoRecomendaciones.getBounds());
		contentPane.add(scrollTextoRecomendaciones);
		scrollTextoRecomendaciones.setViewportView(textoRecomendaciones);
		
		lblAreaRecomendaciones = new JLabel("Recomendaciones");
		lblAreaRecomendaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblAreaRecomendaciones.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 15));
		scrollTextoRecomendaciones.setColumnHeaderView(lblAreaRecomendaciones);
		
		JLabel lblAreaCodigo = new JLabel(MSJ_LABEL_AREA_CODIGO);
		lblAreaCodigo.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 15));
		lblAreaCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		scrollTextoCodigo.setColumnHeaderView(lblAreaCodigo);
		
		textLineasCodigo = new JTextField();
		textLineasCodigo.setBackground(Color.WHITE);
		textLineasCodigo.setEditable(false);
		textLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasCodigo.setBounds(24, 359, 124, 20);
		contentPane.add(textLineasCodigo);
		textLineasCodigo.setColumns(10);
		
		JLabel lblLineasCodigo = new JLabel(MSJ_LABEL_LINEAS_TOTALES_CODIGO);
		lblLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasCodigo.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblLineasCodigo.setBounds(20, 334, 124, 14);
		contentPane.add(lblLineasCodigo);
		
		JLabel lblLineasSoloCodigo = new JLabel(MSJ_LABEL_LINEAS_SOLO_CODIGO);
		lblLineasSoloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasSoloCodigo.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblLineasSoloCodigo.setBounds(190, 334, 165, 14);
		contentPane.add(lblLineasSoloCodigo);
		
		JLabel lblLineasComentadas = new JLabel(MSJ_LABEL_LINEAS_COMENTADAS);
		lblLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasComentadas.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblLineasComentadas.setBounds(394, 334, 165, 14);
		contentPane.add(lblLineasComentadas);
		
		JLabel lblLineasBlanco = new JLabel(MSJ_LABEL_LINEAS_EN_BLANCO);
		lblLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasBlanco.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblLineasBlanco.setBounds(616, 334, 153, 14);
		contentPane.add(lblLineasBlanco);
		
		JLabel lblPorcentajeComentarios = new JLabel(MSJ_LABEL_PORCENTAJE_DE_COMENTARIOS);
		lblPorcentajeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblPorcentajeComentarios.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblPorcentajeComentarios.setBounds(783, 334, 153, 14);
		contentPane.add(lblPorcentajeComentarios);
		
		JLabel lblComplejidadCiclomatica = new JLabel(MSJ_LABEL_COMPLEJIDAD_CICLOMATICA);
		lblComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		lblComplejidadCiclomatica.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblComplejidadCiclomatica.setBounds(24, 399, 117, 14);
		contentPane.add(lblComplejidadCiclomatica);
		
		JLabel lblFanIn = new JLabel(MSJ_LABEL_FAN_IN);
		lblFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanIn.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblFanIn.setBounds(453, 399, 46, 14);
		contentPane.add(lblFanIn);
		
		JLabel lblFanOut = new JLabel(MSJ_LABEL_FAN_OUT);
		lblFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanOut.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblFanOut.setBounds(249, 399, 46, 14);
		contentPane.add(lblFanOut);

		JLabel lblHalsteadLongitud = new JLabel(MSJ_LABEL_HALSTEAD_LONGITUD);
		lblHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadLongitud.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblHalsteadLongitud.setBounds(639, 399, 107, 14);
		contentPane.add(lblHalsteadLongitud);
		
		JLabel lblHalsteadVolumen = new JLabel(MSJ_LABEL_HALSTEAD_VOLUMEN);
		lblHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadVolumen.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblHalsteadVolumen.setBounds(806, 399, 107, 14);
		contentPane.add(lblHalsteadVolumen);
		
		textLineasSoloCodigo = new JTextField();
		textLineasSoloCodigo.setBackground(Color.WHITE);
		textLineasSoloCodigo.setEditable(false);
		textLineasSoloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasSoloCodigo.setColumns(10);
		textLineasSoloCodigo.setBounds(214, 359, 124, 20);
		contentPane.add(textLineasSoloCodigo);
		
		textLineasComentadas = new JTextField();
		textLineasComentadas.setEditable(false);
		textLineasComentadas.setBackground(Color.WHITE);
		textLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasComentadas.setColumns(10);
		textLineasComentadas.setBounds(418, 359, 124, 20);
		contentPane.add(textLineasComentadas);
		
		textLineasBlanco = new JTextField();
		textLineasBlanco.setBackground(Color.WHITE);
		textLineasBlanco.setEditable(false);
		textLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasBlanco.setColumns(10);
		textLineasBlanco.setBounds(634, 359, 124, 20);
		contentPane.add(textLineasBlanco);
		
		textPorcentajeComentarios = new JTextField();
		textPorcentajeComentarios.setBackground(Color.WHITE);
		textPorcentajeComentarios.setEditable(false);
		textPorcentajeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeComentarios.setColumns(10);
		textPorcentajeComentarios.setBounds(801, 359, 124, 20);
		contentPane.add(textPorcentajeComentarios);
		
		textHalsteadVolumen = new JTextField();
		textHalsteadVolumen.setBackground(Color.WHITE);
		textHalsteadVolumen.setEditable(false);
		textHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		textHalsteadVolumen.setColumns(10);
		textHalsteadVolumen.setBounds(804, 424, 124, 20);
		contentPane.add(textHalsteadVolumen);
		
		textHalsteadLongitud = new JTextField();
		textHalsteadLongitud.setBackground(Color.WHITE);
		textHalsteadLongitud.setEditable(false);
		textHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		textHalsteadLongitud.setColumns(10);
		textHalsteadLongitud.setBounds(637, 424, 124, 20);
		contentPane.add(textHalsteadLongitud);
		
		textFanIn = new JTextField();
		textFanIn.setBackground(Color.WHITE);
		textFanIn.setEditable(false);
		textFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		textFanIn.setColumns(10);
		textFanIn.setBounds(421, 424, 124, 20);
		contentPane.add(textFanIn);
		
		textFanOut = new JTextField();
		textFanOut.setBackground(Color.WHITE);
		textFanOut.setEditable(false);
		textFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		textFanOut.setColumns(10);
		textFanOut.setBounds(217, 424, 124, 20);
		contentPane.add(textFanOut);
		
		textComplejidadCiclomatica = new JTextField();
		textComplejidadCiclomatica.setBackground(Color.WHITE);
		textComplejidadCiclomatica.setEditable(false);
		textComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		textComplejidadCiclomatica.setColumns(10);
		textComplejidadCiclomatica.setBounds(27, 424, 124, 20);
		contentPane.add(textComplejidadCiclomatica);
		
		JLabel lblResultado = new JLabel("Resultados del An\u00E1lisis");
		lblResultado.setBackground(Color.WHITE);
		lblResultado.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 15));
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultado.setBounds(318, 301, 332, 24);
		contentPane.add(lblResultado);
		
		
		
		btnSeleccionarCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerMetricas();
				buscarCarpeta();
			}
		});
		
		this.listaArchivos.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				File archivo = listaArchivos.getSelectedValue();
				
				if(archivo != null) {
					restablecerMetricas();
					cargarMetodos(archivo.toString());
				} else {
					JOptionPane.showConfirmDialog(null, MSJ_ERROR, TITULO_ERROR, 
							JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		this.listaMetodos.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String nombreMetodo = listaMetodos.getSelectedValue();
				
				if(nombreMetodo != null) {
					analizarMetodo(nombreMetodo);
				}
				
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(null, MSJ_CERRAR_APLICACION, TITULO_CERRAR_APLICACION, 
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				if(result == JOptionPane.YES_OPTION) {
					we.getWindow().dispose();
				}
			}
		});
	}
	
	private void analizarMetodo(String nombreMetodo) {
		metodo = appManager.getClase().buscarMetodo(nombreMetodo);
		
		if(metodo != null) {
			metodo.analizarMetodo();
			
			// Para escribir los valores en las casillas
			textoCodigo.setText(metodo.toString());
			textLineasCodigo.setText(Integer.toString(metodo.getCantidadLineasTotales()));
			textLineasSoloCodigo.setText(Integer.toString(metodo.getCantidadLinaesSoloCodigo()));
			textLineasComentadas.setText(Integer.toString(metodo.getCantidaLineasComentadas()));
			textLineasBlanco.setText(Integer.toString(metodo.getCantidadLineasBlanco()));
			textPorcentajeComentarios.setText(String.format("%.02f", metodo.getPorcentajeComentarios()));
			textComplejidadCiclomatica.setText(Integer.toString(metodo.getComplejidadCiclomática()));
			textFanIn.setText(Integer.toString(metodo.getFanIn()));
			textFanOut.setText(Integer.toString(metodo.getFanOut()));
			textHalsteadLongitud.setText(Integer.toString(metodo.getHalsteadLongitud()));
			textHalsteadVolumen.setText(String.format("%.02f", metodo.getHalsteadVolumen()));
			
			// Para dar recomendaciones
			String msj = "";
			
			if(metodo.getComplejidadCiclomática() > VALOR_MAXIMO_COMPLEJIDAD_CICLOMATICA) {
				msj += MSJ_RECOMENDACION_COMPLEJIDAD_CICLOMATICA;
			}
			
			if(metodo.getPorcentajeComentarios() < VALOR_MINIMO_PORCENTAJE_LINEAS_COMENTADAS) {
				msj += MSJ_RECOMENDACION_PORCENTAJE_LINEAS_COMENTADAS;
			}
			
			if(metodo.getFanIn() > VALOR_MAXIMO_FAN_IN) {
				msj += MSJ_RECOMENDACION_FAN_IN;
			}
			
			if(metodo.getFanOut() > VALOR_MAXIMO_FAN_OUT) {
				msj += MSJ_RECOMENDACION_FAN_OUT;
			}
			
			this.textoRecomendaciones.setText(msj);
			
		} else {
			JOptionPane.showConfirmDialog(null, MSJ_ERROR, TITULO_ERROR, 
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void restablecerMetricas() {
		textoCodigo.setText(STRING_NULA);
		textLineasCodigo.setText(STRING_NULA);
		textLineasSoloCodigo.setText(STRING_NULA);
		textLineasComentadas.setText(STRING_NULA);
		textLineasBlanco.setText(STRING_NULA);
		textPorcentajeComentarios.setText(STRING_NULA);
		textComplejidadCiclomatica.setText(STRING_NULA);
		textFanIn.setText(STRING_NULA);
		textFanOut.setText(STRING_NULA);
		textHalsteadLongitud.setText(STRING_NULA);
		textHalsteadVolumen.setText(STRING_NULA);
	}
	
	private void cargarMetodos(String path) {
		
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		this.appManager.iniciarAplicacion(path);
		
		if(this.appManager.getClase().getMetodos().size() == 0) {
			JOptionPane.showConfirmDialog(null, MSJ_SIN_METODOS, TITULO_ERROR,	JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		for(Metodo metodo : this.appManager.getClase().getMetodos()) {
			dlm.addElement(metodo.getNombre());
		}
		this.listaMetodos.setModel(dlm);
	}

	private void buscarCarpeta() {
		JFileChooser chooser = new JFileChooser();

		// Para que no seleccionen archivos
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			cargarArchivos(chooser.getSelectedFile());
		}
	}

	private void cargarArchivos(File directorio) {
		DefaultListModel<File> dlm = new DefaultListModel<File>();
		for (File archivo : directorio.listFiles(new FiltroJava())) {
			if (!archivo.isDirectory()) {
				dlm.addElement(archivo);
			}
			this.listaArchivos.setModel(dlm);
		}
	}

	public class FiltroJava implements FileFilter {

		@Override
		public boolean accept(File archivo) {
			if (archivo.isDirectory()) {
				return true;
			}

			if (archivo.getName().toLowerCase().endsWith(EXTENSION_JAVA)) {
				return true;
			}
			return false;
		}

	}
}
