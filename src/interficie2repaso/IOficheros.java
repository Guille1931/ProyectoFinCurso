package interficie2repaso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class IOficheros {

private List <Integer> valoresInt;
private List <String> valoresStr;

public IOficheros() {
	this.valoresInt = new ArrayList<>();
	this.valoresStr = new ArrayList<>();

}
	//Metodo que importa a una lista numeros de un fichero IMPORTANTE tener una lista de enteros tambien en la clase que llame a este metodo
	public ArrayList<Integer> importInt(String path) {
		valoresInt.clear();
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    try {
			 archivo = new File (path); //la ubicacion del archivos de texto
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);
	         String linea;
	         while((linea=br.readLine())!=null)
	        	 valoresInt.add(Integer.parseInt(linea));
	         JOptionPane.showMessageDialog(null,"Valores importados correctamente del archivo\n ["+path+"]");
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try{
	            if( null != fr ){
	               fr.close();
	            }
	         }catch (Exception e2){
	            e2.printStackTrace();
	         }
		}
	return (ArrayList<Integer>) valoresInt;
	}

	//metodo que devuelve el contenido de un fichero de strings IMPORTANTE tener una lista de strings en la clase que llame a este metodo
	public ArrayList<String> importStr(String path) {
		valoresStr.clear();
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    try {
			 archivo = new File (path);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);
	         String linea;
	         while((linea=br.readLine())!=null)
	        	 valoresStr.add(linea);
	         JOptionPane.showMessageDialog(null,"Valores importados correctamente del archivo\n ["+path+"]");
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try{
	            if( null != fr ){
	               fr.close();
	            }
	         }catch (Exception e2){
	            e2.printStackTrace();
	         }
		}
	return (ArrayList<String>) valoresStr;
	}

	//guarda en un fichero los valores de una lista de enteros IMPORTANTE pasas lista, path y modo por este orden
	public void exportarInt(List<Integer> vInt, String path, boolean modo) {
		FileWriter archivo = null;
        PrintWriter pw = null;
        try {
        	archivo = new FileWriter(path, modo);
            pw = new PrintWriter(archivo);
            for (Integer element : vInt)
				pw.println(element);
            JOptionPane.showMessageDialog(null,"Exportacion realizada con exito al archivo\n ["+path+"]");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
	           if (null != archivo)
	        	   archivo.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
        }
	}

	//guarda en un fichero los valores de una lista de strings IMPORTANTE pasas lista, path y modo por este orden
	public void exportarStr(List<String> vStr, String path, boolean modo) {
		FileWriter archivo = null;
        PrintWriter pw = null;
        try {
        	archivo = new FileWriter(path, modo);
            pw = new PrintWriter(archivo);
            for (String element : vStr)
				pw.println(element);
            JOptionPane.showMessageDialog(null,"Exportacion realizada con exito al archivo\n ["+path+"]");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
	           if (null != archivo)
	        	   archivo.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
        }
    }
	public void exportarValorDouble (double valor, String path, Boolean modo) {
		FileWriter archivo = null;
        PrintWriter pw = null;
        try {
        	archivo = new FileWriter(path, modo);
            pw = new PrintWriter(archivo);
            pw.println(valor);
            JOptionPane.showMessageDialog(null,"Exportacion realizada con exito al archivo\n ["+path+"]");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
	           if (null != archivo)
	        	   archivo.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
        }
	}
}
