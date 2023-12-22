package unidad3.ejercicio4.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleClient {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Pattern regex = Pattern.compile("^(\\d+(?:\\.\\d+)?)\\s*([+-×÷])\\s*(\\d+(?:\\.\\d+)?)$|^(\\d+(?:\\.\\d+)?)\\s*(√)$");
		String linea;
		System.out.print("+-×÷√> ");
		while ((linea = in.readLine().trim()) != null) {
			Matcher m = regex.matcher(linea);
			if (m.matches()) {
				if (m.group(2) == null)
					enviar(m.group(4), m.group(5));
				else
					enviar(m.group(1), m.group(2), m.group(3));
			}
			else
				System.out.println("ERROR");
			System.out.print("+-×÷√> ");
		}
	}
	
	static void enviar(String... tokens) {
		try (Socket s = new Socket("localhost", 9999);
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				DataInputStream in = new DataInputStream(s.getInputStream())) {
			out.writeDouble(Double.parseDouble(tokens[0]));
			out.writeChar(tokens[1].charAt(0));
			if (tokens.length == 3)
				out.writeDouble(Double.parseDouble(tokens[2]));
			System.out.println(in.readDouble());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
