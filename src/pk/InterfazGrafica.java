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
	
	private JScrollPane scrollListaArchivos;
	private JScrollPane scrollListaMetodos;
	private JScrollPane scrollTextoCodigo;
	
	// ELEMENTOS DE LA LOGICA
	private AppManager appManager;
	private Metodo metodo;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

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


		lblArchivos = new JLabel("Archivos Disponibles");
		lblArchivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblArchivos.setBounds(164, 15, 485, 60);

		lblMetodos = new JLabel("M\u00E9todos Disponibles");
		lblMetodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMetodos.setBounds(674, 15, 261, 25);

		listaMetodos = new JList<String>();
		listaMetodos.setBounds(675, 45, 261, 107);

		setTitle(TITULO);

		btnSeleccionarCarpeta = new JButton("Seleccionar carpeta");
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
		
		textField = new JTextField();
		textField.setBounds(10, 373, 124, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblLineasCodigo = new JLabel(MSJ_LABEL_LINEAS_TOTALES_CODIGO);
		lblLineasCodigo.setBounds(10, 342, 124, 14);
		contentPane.add(lblLineasCodigo);
		
		JLabel lblLineasSoloCodigo = new JLabel(MSJ_LABEL_LINEAS_SOLO_CODIGO);
		lblLineasSoloCodigo.setBounds(180, 342, 165, 14);
		contentPane.add(lblLineasSoloCodigo);
		
		JLabel lblLineasComentadas = new JLabel(MSJ_LABEL_LINEAS_COMENTADAS);
		lblLineasComentadas.setBounds(384, 342, 165, 14);
		contentPane.add(lblLineasComentadas);
		
		JLabel lblLineasBlanco = new JLabel(MSJ_LABEL_LINEAS_EN_BLANCO);
		lblLineasBlanco.setBounds(607, 342, 153, 14);
		contentPane.add(lblLineasBlanco);
		
		JLabel lblPorcentajeComentarios = new JLabel(MSJ_LABEL_PORCENTAJE_DE_COMENTARIOS);
		lblPorcentajeComentarios.setBounds(774, 342, 153, 14);
		contentPane.add(lblPorcentajeComentarios);
		
		JLabel lblComplejidadCiclomatica = new JLabel(MSJ_LABEL_COMPLEJIDAD_CICLOMATICA);
		lblComplejidadCiclomatica.setBounds(14, 424, 117, 14);
		contentPane.add(lblComplejidadCiclomatica);
		
		JLabel lblFanIn = new JLabel(MSJ_LABEL_FAN_IN);
		lblFanIn.setBounds(443, 424, 46, 14);
		contentPane.add(lblFanIn);
		
		JLabel lblFanOut = new JLabel(MSJ_LABEL_FAN_OUT);
		lblFanOut.setBounds(239, 424, 46, 14);
		contentPane.add(lblFanOut);

		JLabel lblHalsteadLongitud = new JLabel(MSJ_LABEL_HALSTEAD_LONGITUD);
		lblHalsteadLongitud.setBounds(630, 424, 107, 14);
		contentPane.add(lblHalsteadLongitud);
		
		JLabel lblHalsteadVolumen = new JLabel(MSJ_LABEL_HALSTEAD_VOLUMEN);
		lblHalsteadVolumen.setBounds(797, 424, 107, 14);
		contentPane.add(lblHalsteadVolumen);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(200, 373, 124, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(404, 373, 124, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(621, 373, 124, 20);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(788, 373, 124, 20);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(788, 449, 124, 20);
		contentPane.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(621, 449, 124, 20);
		contentPane.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(404, 449, 124, 20);
		contentPane.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(200, 449, 124, 20);
		contentPane.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(10, 449, 124, 20);
		contentPane.add(textField_9);
		
		
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
					cargarCodigo(nombreMetodo);
				}
				
			}
		});
	}
	
	private void cargarCodigo(String nombreMetodo) {
		Metodo metodo = appManager.getClase().buscarMetodo(nombreMetodo);
		
		if(metodo != null) {
			textoCodigo.setText(metodo.toString());
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
