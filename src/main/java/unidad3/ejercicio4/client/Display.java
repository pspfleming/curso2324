package unidad3.ejercicio4.client;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class Display extends JTextField {

	static final long serialVersionUID = 1L;
	private double saved, current;
	private boolean clear;
	private int decimal;
	private Character binaryOperation;

	public Display() {
		super("0", 20);
		try {
			setFont(Font.createFont(Font.PLAIN, Display.class.getResourceAsStream("/unidad3/ejercicio4/client/Calculator.ttf"))
						.deriveFont(70f));
		} catch (FontFormatException | IOException e) {
		}
		setFocusable(false);
		setEditable(false);
		setHorizontalAlignment(JTextField.RIGHT);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30), getBorder()));
	}
	
	public double getCurrent() {
		return current;
	}

	public double getSaved() {
		return saved;
	}

	public void startDecimal() {
		if (decimal == 0)
			decimal++;
	}
	
	public void clear() {
		setText("0");
		saved = current = decimal = 0;
		clear = false;
		binaryOperation = null;
	}

	
	public void binaryOperation(Character operator) {
		try {
			if (binaryOperation != null) 
				setText(String.valueOf(current = sendBinaryOperationRequest(saved, current, operator)));
			saved = current;
			clear = true;
			binaryOperation = operator;
		} catch (IOException e) {
			e.printStackTrace();
			setText("server error");
		}
	}
	
	public void unaryOperation(Character operator) {
		try {
			if (binaryOperation != null) {
				current = sendBinaryOperationRequest(saved, current, binaryOperation);
				binaryOperation = null;
			}
			if (operator == '=')
				setText(String.valueOf(current));
			else
				setText(String.valueOf(current = sendUnaryOperationRequest(current, operator)));
			clear = true;
		} catch (IOException e) {
			e.printStackTrace();
			setText("server error");
		}
		
	}
	
	private double sendBinaryOperationRequest(double op1, double op2, char operator) throws IOException {
		Socket socket = new Socket("localhost", 9999);
		try (
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		) {
			out.writeDouble(op1);
			out.writeChar(operator);
			out.writeDouble(op2);
			return in.readDouble(); 
		}	
	}
	
	private double sendUnaryOperationRequest(double op1, char operator) throws IOException {
		Socket socket = new Socket("localhost", 9999);
		try (
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		) {
			out.writeDouble(op1);
			out.writeChar(operator);
			return in.readDouble();
		}
	}
	
	public void update(int digit) {
		if (clear) {
			current = decimal = 0;
			clear = false;
		}
		if (decimal > 0)
			current = current + digit / Math.pow(10, decimal);
		else
			current = current * 10 + digit;
		String format = String.format("%%.%df", decimal == 0 ? 0 : decimal++);
		setText(String.format(format, current));
	}
	
}
