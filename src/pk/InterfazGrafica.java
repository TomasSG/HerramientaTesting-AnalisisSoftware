package pk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class InterfazGrafica extends JFrame {

	// CONSTANTES
	private static final String TITULO = "Herramienta Testing - Grupo 03";
	private static final String MSJ_LABEL_LINEAS_TOTALES_CODIGO = "Cantidad de l�neas totales";
	private static final String MSJ_LABEL_LINEAS_SOLO_CODIGO = "Cantidad de l�neas de solo c�digo";
	private static final String MSJ_LABEL_LINEAS_COMENTADAS = "Cantidad de l�neas comentadas";
	private static final String MSJ_LABEL_LINEAS_EN_BLANCO = "Cantidad de l�neas en blanco";
	private static final String MSJ_LABEL_PORCENTAJE_DE_COMENTARIOS = "Porcentaje de comentarios (%)";
	private static final String MSJ_LABEL_COMPLEJIDAD_CICLOMATICA = "Comlejidad ciclomatica";
	private static final String MSJ_LABEL_FAN_IN = "Fan in";
	private static final String MSJ_LABEL_FAN_OUT = "Fan out";
	private static final String MSJ_LABEL_HALSTEAD_LONGITUD = "Halstead - longitud";
	private static final String MSJ_LABEL_HALSTEAD_VOLUMEN = "Halstead - volumen";
	private static final String MSJ_LABEL_LISTA_ARCHIVOS = "Archivos Disponibles";
	private static final String MSJ_LABEL_LISTA_METODOS = "Metodos Disponibles";
	
	private static final String MSJ_BT_SELECCIONAR_CARPETA = "Seleccionar carpeta";

	private static final String STRING_NULA = "";
	private static final String EXTENSION_JAVA = ".java";

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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 958, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textoCodigo = new JTextArea();
		textoCodigo.setBounds(10, 190, 925, 107);
		textoCodigo.setEditable(false);
		textoCodigo.setLineWrap(true);
		textoCodigo.setText(STRING_NULA);

		listaArchivos = new JList<File>();
		listaArchivos.setBounds(164, 47, 486, 107);


		lblArchivos = new JLabel(MSJ_LABEL_LISTA_ARCHIVOS);
		lblArchivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblArchivos.setBounds(164, 15, 485, 60);

		lblMetodos = new JLabel(MSJ_LABEL_LISTA_METODOS);
		lblMetodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMetodos.setBounds(674, 15, 261, 25);

		listaMetodos = new JList<String>();
		listaMetodos.setBounds(675, 45, 261, 107);

		setTitle(TITULO);

		btnSeleccionarCarpeta = new JButton(MSJ_BT_SELECCIONAR_CARPETA);
		btnSeleccionarCarpeta.setFont(new Font("Tahoma", Font.PLAIN, 9));

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
		
		textLineasCodigo = new JTextField();
		textLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasCodigo.setBounds(10, 373, 124, 20);
		contentPane.add(textLineasCodigo);
		textLineasCodigo.setColumns(10);
		
		JLabel lblLineasCodigo = new JLabel(MSJ_LABEL_LINEAS_TOTALES_CODIGO);
		lblLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasCodigo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasCodigo.setBounds(10, 342, 124, 14);
		contentPane.add(lblLineasCodigo);
		
		JLabel lblLineasSoloCodigo = new JLabel(MSJ_LABEL_LINEAS_SOLO_CODIGO);
		lblLineasSoloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasSoloCodigo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasSoloCodigo.setBounds(180, 342, 165, 14);
		contentPane.add(lblLineasSoloCodigo);
		
		JLabel lblLineasComentadas = new JLabel(MSJ_LABEL_LINEAS_COMENTADAS);
		lblLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasComentadas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasComentadas.setBounds(384, 342, 165, 14);
		contentPane.add(lblLineasComentadas);
		
		JLabel lblLineasBlanco = new JLabel(MSJ_LABEL_LINEAS_EN_BLANCO);
		lblLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasBlanco.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLineasBlanco.setBounds(607, 342, 153, 14);
		contentPane.add(lblLineasBlanco);
		
		JLabel lblPorcentajeComentarios = new JLabel(MSJ_LABEL_PORCENTAJE_DE_COMENTARIOS);
		lblPorcentajeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblPorcentajeComentarios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPorcentajeComentarios.setBounds(774, 342, 153, 14);
		contentPane.add(lblPorcentajeComentarios);
		
		JLabel lblComplejidadCiclomatica = new JLabel(MSJ_LABEL_COMPLEJIDAD_CICLOMATICA);
		lblComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		lblComplejidadCiclomatica.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblComplejidadCiclomatica.setBounds(14, 424, 117, 14);
		contentPane.add(lblComplejidadCiclomatica);
		
		JLabel lblFanIn = new JLabel(MSJ_LABEL_FAN_IN);
		lblFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanIn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFanIn.setBounds(443, 424, 46, 14);
		contentPane.add(lblFanIn);
		
		JLabel lblFanOut = new JLabel(MSJ_LABEL_FAN_OUT);
		lblFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanOut.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFanOut.setBounds(239, 424, 46, 14);
		contentPane.add(lblFanOut);

		JLabel lblHalsteadLongitud = new JLabel(MSJ_LABEL_HALSTEAD_LONGITUD);
		lblHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadLongitud.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblHalsteadLongitud.setBounds(630, 424, 107, 14);
		contentPane.add(lblHalsteadLongitud);
		
		JLabel lblHalsteadVolumen = new JLabel(MSJ_LABEL_HALSTEAD_VOLUMEN);
		lblHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadVolumen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblHalsteadVolumen.setBounds(797, 424, 107, 14);
		contentPane.add(lblHalsteadVolumen);
		
		textLineasSoloCodigo = new JTextField();
		textLineasSoloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasSoloCodigo.setColumns(10);
		textLineasSoloCodigo.setBounds(200, 373, 124, 20);
		contentPane.add(textLineasSoloCodigo);
		
		textLineasComentadas = new JTextField();
		textLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasComentadas.setColumns(10);
		textLineasComentadas.setBounds(404, 373, 124, 20);
		contentPane.add(textLineasComentadas);
		
		textLineasBlanco = new JTextField();
		textLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		textLineasBlanco.setColumns(10);
		textLineasBlanco.setBounds(621, 373, 124, 20);
		contentPane.add(textLineasBlanco);
		
		textPorcentajeComentarios = new JTextField();
		textPorcentajeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeComentarios.setColumns(10);
		textPorcentajeComentarios.setBounds(788, 373, 124, 20);
		contentPane.add(textPorcentajeComentarios);
		
		textHalsteadVolumen = new JTextField();
		textHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		textHalsteadVolumen.setColumns(10);
		textHalsteadVolumen.setBounds(788, 449, 124, 20);
		contentPane.add(textHalsteadVolumen);
		
		textHalsteadLongitud = new JTextField();
		textHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		textHalsteadLongitud.setColumns(10);
		textHalsteadLongitud.setBounds(621, 449, 124, 20);
		contentPane.add(textHalsteadLongitud);
		
		textFanIn = new JTextField();
		textFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		textFanIn.setColumns(10);
		textFanIn.setBounds(404, 449, 124, 20);
		contentPane.add(textFanIn);
		
		textFanOut = new JTextField();
		textFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		textFanOut.setColumns(10);
		textFanOut.setBounds(200, 449, 124, 20);
		contentPane.add(textFanOut);
		
		textComplejidadCiclomatica = new JTextField();
		textComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		textComplejidadCiclomatica.setColumns(10);
		textComplejidadCiclomatica.setBounds(10, 449, 124, 20);
		contentPane.add(textComplejidadCiclomatica);
		
		
		btnSeleccionarCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCarpeta();
			}
		});
		
		this.listaArchivos.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				File archivo = listaArchivos.getSelectedValue();
				
				if(archivo != null) {
					cargarMetodos(archivo.toString());
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
	}
	
	private void analizarMetodo(String nombreMetodo) {
		Metodo metodo = appManager.getClase().buscarMetodo(nombreMetodo);
		
		if(metodo != null) {
			metodo.analizarMetodo();
			textoCodigo.setText(metodo.toString());
			textLineasCodigo.setText(Integer.toString(metodo.getCantidadLineasTotales()));
			textLineasSoloCodigo.setText(Integer.toString(metodo.getCantidadLinaesSoloCodigo()));
			textLineasComentadas.setText(Integer.toString(metodo.getCantidaLineasComentadas()));
			textLineasBlanco.setText(Integer.toString(metodo.getCantidadLineasBlanco()));
			textPorcentajeComentarios.setText(String.format("%.02f", metodo.getPorcentajeComentarios()));
			textComplejidadCiclomatica.setText(Integer.toString(metodo.getComplejidadCiclom�tica()));
			textFanIn.setText(Integer.toString(metodo.getFanIn()));
			textFanOut.setText(Integer.toString(metodo.getFanOut()));
			textHalsteadLongitud.setText(Integer.toString(metodo.getHalsteadLongitud()));
			textHalsteadVolumen.setText(String.format("%.02f", metodo.getHalsteadVolumen()));
		}
		
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
