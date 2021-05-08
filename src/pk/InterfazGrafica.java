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

public class InterfazGrafica extends JFrame {

	// CONSTANTES
	private static final String TITULO = "Herramienta Testing - Grupo 03";
	private static final String MSJ_POR_DEFECTO_LISTA_ARCHIVOS = "Aun no se encontraron archivos disponibles";

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
		scrollTextoCodigo.setVisible(true);
		
		
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
