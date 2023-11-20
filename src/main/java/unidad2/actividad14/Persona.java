package unidad2.actividad14;

import java.util.Random;

public class Persona extends Thread {
	
	static final Random R = new Random();
	private Banco banco;
	
	public Persona(String nombre, Banco banco) {
		super(nombre);
		this.banco = banco;
	}
	
	@Override
	public void run() {
		System.out.println(getName() + " esta paseando");
		try {
			Thread.sleep(R.nextLong(2001) + 1000);
		} catch (InterruptedException e) {
		}
		System.out.println(getName() + " ha llegado al banco");
		banco.sentarse();
		System.out.println(getName() + " se va del parque");
	}
	
}
