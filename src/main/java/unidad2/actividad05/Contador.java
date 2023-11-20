package unidad2.actividad05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contador implements Runnable {

	private File file;
	
	public Contador(File file) {
		this.file = file;
	}
	
	@Override
	public void run() {
		int c = 0, p = 0, l = 0;
		Pattern pattern = Pattern.compile("\\p{L}+");
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String linea;
			while ((linea = in.readLine()) != null) {
				c += linea.length();
//				p += linea.split("\\p{L}+").length;
				Matcher m = pattern.matcher(linea);
				while (m.find())
					p++;
				l++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(file.getName());
		System.out.println("c: " + c);
		System.out.println("p: " + p);
		System.out.println("l: " + l);
	}

}
