package pk;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class InterfazGrafica extends JFrame {

	// CONSTANTES
	private static final String TITULO = "Herramienta Testing - Grupo 03";
	private static final String MSJ_POR_DEFECTO_LISTA_ARCHIVOS = "Aun no se encontraron archivos disponibles";

	private static final String STRING_NULA = "";
	private static final String EXTENSION_JAVA = ".java";

	// ELEMENTOS DE PANTALLA
	private JTextArea textCodigo;
	private JList<String> listaArchivos;
	private JList<String> listaMetodos;
	private JButton btnSeleccionarCarpeta;
	private JLabel lblMetodos;
	private JLabel lblArchivos;

	private JPanel contentPane;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 958, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textCodigo = new JTextArea();
		textCodigo.setBounds(61, 190, 749, 107);
		textCodigo.setEditable(false);
		textCodigo.setLineWrap(true);
		textCodigo.setText(STRING_NULA);
		contentPane.add(textCodigo);

		listaArchivos = new JList<String>();
		listaArchivos.setBounds(232, 47, 261, 107);

		contentPane.add(listaArchivos);

		lblArchivos = new JLabel("Archivos Disponibles");
		lblArchivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblArchivos.setBounds(232, 11, 261, 25);
		contentPane.add(lblArchivos);

		lblMetodos = new JLabel("M\u00E9todos Disponibles");
		lblMetodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMetodos.setBounds(584, 11, 261, 25);
		contentPane.add(lblMetodos);

		listaMetodos = new JList<String>();
		listaMetodos.setBounds(584, 47, 261, 107);
		contentPane.add(listaMetodos);

		setTitle(TITULO);

		btnSeleccionarCarpeta = new JButton("Seleccionar carpeta");

		btnSeleccionarCarpeta.setBounds(10, 11, 144, 41);
		contentPane.add(btnSeleccionarCarpeta);
		btnSeleccionarCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCarpeta();
			}
		});
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
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		for (File archivo : directorio.listFiles(new FiltroJava())) {
			if (!archivo.isDirectory()) {
				dlm.addElement(archivo.getName());
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
