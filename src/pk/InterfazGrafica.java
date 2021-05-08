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
	private static final String MSJ_LABEL_LISTA_METODOS = "Metodos Disponibles";
	private static final String MSJ_LABEL_AREA_CODIGO = "Código del método";
	
	private static final String MSJ_BT_SELECCIONAR_CARPETA = "Seleccionar carpeta";
	
	private static final String MSJ_CERRAR_APLICACION = "Deseas salir de la aplicación?";
	private static final String TITULO_CERRAR_APLICACION = "Confirmar operación";
	private static final String MSJ_ERROR = "Ocurrió un error al realizar la tarea";
	private static final String TITULO_ERROR = "Error";

	private static final String STRING_NULA = "";
	private static final String EXTENSION_JAVA = ".java";
	
	private static final String PATH_ICONO_BUSCAR_DIRECTORIO = "/images/folder.png";

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
	
	// ELEMENTOS DE LA LOGICA
	private AppManager appManager;
	private Metodo metodo;
	

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
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textoCodigo = new JTextArea();
		textoCodigo.setBounds(10, 170, 925, 170);
		textoCodigo.setEditable(false);
		textoCodigo.setLineWrap(true);
		textoCodigo.setText(STRING_NULA);

		listaArchivos = new JList<File>();
		listaArchivos.setBounds(164, 10, 350, 150);


		lblArchivos = new JLabel(MSJ_LABEL_LISTA_ARCHIVOS);
		lblArchivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblArchivos.setBounds(164, 15, 485, 60);

		lblMetodos = new JLabel(MSJ_LABEL_LISTA_METODOS);
		lblMetodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMetodos.setBounds(674, 15, 261, 25);

		listaMetodos = new JList<String>();
		listaMetodos.setBounds(550, 10, 350, 150);

		setTitle(TITULO);

		btnSeleccionarCarpeta = new JButton(MSJ_BT_SELECCIONAR_CARPETA);
		btnSeleccionarCarpeta.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSeleccionarCarpeta.setIcon(new ImageIcon(InterfazGrafica.class.getResource(PATH_ICONO_BUSCAR_DIRECTORIO)));
		btnSeleccionarCarpeta.setBounds(10, 35, 144, 41);
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
		
		JLabel lblAreaCodigo = new JLabel(MSJ_LABEL_AREA_CODIGO);
		lblAreaCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		scrollTextoCodigo.setColumnHeaderView(lblAreaCodigo);
		
		textLineasCodigo = new JTextField();
		textLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasCodigo.setBounds(22, 413, 124, 20);
		contentPane.add(textLineasCodigo);
		textLineasCodigo.setColumns(10);
		
		JLabel lblLineasCodigo = new JLabel(MSJ_LABEL_LINEAS_TOTALES_CODIGO);
		lblLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasCodigo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasCodigo.setBounds(18, 388, 124, 14);
		contentPane.add(lblLineasCodigo);
		
		JLabel lblLineasSoloCodigo = new JLabel(MSJ_LABEL_LINEAS_SOLO_CODIGO);
		lblLineasSoloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasSoloCodigo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasSoloCodigo.setBounds(188, 388, 165, 14);
		contentPane.add(lblLineasSoloCodigo);
		
		JLabel lblLineasComentadas = new JLabel(MSJ_LABEL_LINEAS_COMENTADAS);
		lblLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasComentadas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasComentadas.setBounds(392, 388, 165, 14);
		contentPane.add(lblLineasComentadas);
		
		JLabel lblLineasBlanco = new JLabel(MSJ_LABEL_LINEAS_EN_BLANCO);
		lblLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasBlanco.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasBlanco.setBounds(615, 388, 153, 14);
		contentPane.add(lblLineasBlanco);
		
		JLabel lblPorcentajeComentarios = new JLabel(MSJ_LABEL_PORCENTAJE_DE_COMENTARIOS);
		lblPorcentajeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblPorcentajeComentarios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPorcentajeComentarios.setBounds(782, 388, 153, 14);
		contentPane.add(lblPorcentajeComentarios);
		
		JLabel lblComplejidadCiclomatica = new JLabel(MSJ_LABEL_COMPLEJIDAD_CICLOMATICA);
		lblComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		lblComplejidadCiclomatica.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblComplejidadCiclomatica.setBounds(22, 453, 117, 14);
		contentPane.add(lblComplejidadCiclomatica);
		
		JLabel lblFanIn = new JLabel(MSJ_LABEL_FAN_IN);
		lblFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanIn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFanIn.setBounds(451, 453, 46, 14);
		contentPane.add(lblFanIn);
		
		JLabel lblFanOut = new JLabel(MSJ_LABEL_FAN_OUT);
		lblFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanOut.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFanOut.setBounds(247, 453, 46, 14);
		contentPane.add(lblFanOut);

		JLabel lblHalsteadLongitud = new JLabel(MSJ_LABEL_HALSTEAD_LONGITUD);
		lblHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadLongitud.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblHalsteadLongitud.setBounds(638, 453, 107, 14);
		contentPane.add(lblHalsteadLongitud);
		
		JLabel lblHalsteadVolumen = new JLabel(MSJ_LABEL_HALSTEAD_VOLUMEN);
		lblHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadVolumen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblHalsteadVolumen.setBounds(805, 453, 107, 14);
		contentPane.add(lblHalsteadVolumen);
		
		textLineasSoloCodigo = new JTextField();
		textLineasSoloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasSoloCodigo.setColumns(10);
		textLineasSoloCodigo.setBounds(212, 413, 124, 20);
		contentPane.add(textLineasSoloCodigo);
		
		textLineasComentadas = new JTextField();
		textLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasComentadas.setColumns(10);
		textLineasComentadas.setBounds(416, 413, 124, 20);
		contentPane.add(textLineasComentadas);
		
		textLineasBlanco = new JTextField();
		textLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasBlanco.setColumns(10);
		textLineasBlanco.setBounds(633, 413, 124, 20);
		contentPane.add(textLineasBlanco);
		
		textPorcentajeComentarios = new JTextField();
		textPorcentajeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeComentarios.setColumns(10);
		textPorcentajeComentarios.setBounds(800, 413, 124, 20);
		contentPane.add(textPorcentajeComentarios);
		
		textHalsteadVolumen = new JTextField();
		textHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		textHalsteadVolumen.setColumns(10);
		textHalsteadVolumen.setBounds(803, 478, 124, 20);
		contentPane.add(textHalsteadVolumen);
		
		textHalsteadLongitud = new JTextField();
		textHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		textHalsteadLongitud.setColumns(10);
		textHalsteadLongitud.setBounds(636, 478, 124, 20);
		contentPane.add(textHalsteadLongitud);
		
		textFanIn = new JTextField();
		textFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		textFanIn.setColumns(10);
		textFanIn.setBounds(419, 478, 124, 20);
		contentPane.add(textFanIn);
		
		textFanOut = new JTextField();
		textFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		textFanOut.setColumns(10);
		textFanOut.setBounds(215, 478, 124, 20);
		contentPane.add(textFanOut);
		
		textComplejidadCiclomatica = new JTextField();
		textComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		textComplejidadCiclomatica.setColumns(10);
		textComplejidadCiclomatica.setBounds(25, 478, 124, 20);
		contentPane.add(textComplejidadCiclomatica);
		
		
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
							JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
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
