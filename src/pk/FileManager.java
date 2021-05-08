package pk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

	
	public ArrayList<String> leerArchivo(String path) {
		
		FileReader fr = null;
		BufferedReader br = null;
		
		ArrayList<String> listaResultado = new ArrayList<String>();
		
		try {
			String linea;
			
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			
			linea = br.readLine();
			while(linea != null) {
				listaResultado.add(linea);
				linea = br.readLine();	
			}
			br.close();
			
		} catch (IOException e) {
			System.out.println("Problema con el FileManager en" + path);
			e.printStackTrace();
		}
		 return listaResultado;
	}
}