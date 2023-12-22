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
		Pattern regex = Pattern.compile("^(\\d+(?:\\.\\d+)?)\\s*([+-\u00d7\u00f7])\\s*(\\d+(?:\\.\\d+)?)$|^(\\d+(?:\\.\\d+)?)\\s*(\u221A$)$");
		String linea;
		System.out.print("+-\u00d7\u00f7\u221A> ");
		while ((linea = in.readLine().trim()) != null) {
			Matcher m = regex.matcher(linea);
			if (m.matches()) {
				for (int i=1; i<=m.groupCount(); i++)
					System.out.println(m.group(i));
				if (m.group(2) == null)
					enviar(Double.parseDouble(m.group(4)), m.group(5).charAt(0));
				else
					enviar(Double.parseDouble(m.group(1)), m.group(2).charAt(0), Double.parseDouble(m.group(3)));
			}
			else
				System.out.println("ERROR");
			System.out.print("> ");
		}
	}
	
	static void enviar(double op1, char operator, double op2) {
		try (Socket s = new Socket("localhost", 9999);
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				DataInputStream in = new DataInputStream(s.getInputStream())) {
			out.writeDouble(op1);
			out.writeChar(operator);
			out.writeDouble(op2);
			System.out.println(in.readDouble());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void enviar(double op, char operator) {
		try (Socket s = new Socket("localhost", 9999);
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				DataInputStream in = new DataInputStream(s.getInputStream())) {
			out.writeDouble(op);
			out.writeChar(operator);
			System.out.println(in.readDouble());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
