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
		double op1, op2 = 0, result;
		char operator;
		try (DataInputStream in = new DataInputStream(socket.getInputStream());
		     DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
			op1 = in.readDouble();
			switch (operator = in.readChar()) {
			case '+':
				out.writeDouble(result = op1 + (op2 = in.readDouble()));
				break;
			case '-':
				out.writeDouble(result = op1 - (op2 = in.readDouble()));
				break;
			case '×':
				out.writeDouble(result = op1 * (op2 = in.readDouble()));
				break;
			case '÷':
				out.writeDouble(result = op1 / (op2 = in.readDouble()));
				break;
			case '√':
				out.writeDouble(Math.sqrt(op1));
				break;
			}
			System.out.println(socket.getInetAddress());
			System.out.println(op1 + " " + operator + (operator == '√' ? "" : " " + op2));
		} catch (SocketTimeoutException e) {
			System.err.println("TIMEOUT: " + e.getLocalizedMessage() + "(" + socket.getInetAddress() + ")");
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getLocalizedMessage() + "(" + socket.getInetAddress() + ")");
		}
	}

}
