package unidad3.ejercicio4.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
		try (DataInputStream in = new DataInputStream(socket.getInputStream());
		     DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
			double n = in.readDouble();
			switch (in.readChar()) {
			case '+':
				out.writeDouble(n + in.readDouble());
				break;
			case '-':
				out.writeDouble(n - in.readDouble());
				break;
			case '\u00d7':
				out.writeDouble(n * in.readDouble());
				break;
			case '\u00f7':
				out.writeDouble(n / in.readDouble());
				break;
			case '\u221A':
				out.writeDouble(Math.sqrt(n));
				break;
			}
		} catch (SocketTimeoutException e) {
			System.err.println("TIMEOUT: " + e.getLocalizedMessage() + "(" + socket.getInetAddress() + ")");
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getLocalizedMessage() + "(" + socket.getInetAddress() + ")");
		}
	}

}
