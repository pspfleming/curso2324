package unidad3.ejercicio4.client;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

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
		if (binaryOperation != null) 
			setText(String.valueOf(current = sendBinaryOperationRequest(saved, current, operator)));
		saved = current;
		clear = true;
		binaryOperation = operator;
		System.out.printf("saved(%f), current(%f), %s\n", saved, current, binaryOperation);
	}
	
	public void unaryOperation(Character operator) {
		if (binaryOperation != null) {
			current = sendBinaryOperationRequest(saved, current, binaryOperation);
			binaryOperation = null;
		}
		setText(String.valueOf(current = sendUnaryOperationRequest(current, operator)));
		clear = true;
		
		System.out.printf("saved(%f), current(%f), %s\n", saved, current, binaryOperation);
	}
	
	private double sendBinaryOperationRequest(double op1, double op2, char operator) {
		
	}
	
	private double sendUnaryOperationRequest(double op1, char operator) {
		
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
