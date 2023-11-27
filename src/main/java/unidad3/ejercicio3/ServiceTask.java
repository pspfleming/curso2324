package unidad3.ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ServiceTask implements Runnable {

	Socket socket;
	
	public ServiceTask(Socket socket) throws SocketException {
		socket.setSoTimeout(3000);
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			String line;
			System.out.println("(" + socket.getInetAddress() + ")");
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				out.println(line);
				out.flush();
			}
		} catch (SocketTimeoutException e) {
			System.err.println("TIMEOUT: " + e.getLocalizedMessage() + "(" + socket.getInetAddress() + ")");
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getLocalizedMessage() + "(" + socket.getInetAddress() + ")");
		}
	}

}
