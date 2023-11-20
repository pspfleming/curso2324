package unidad2.actividad15.v2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Parque {
	private final int numPersonas;
	private final int numPlazas;
	private final Semaphore BANCO;

	public Parque(int numPersonas, int numPlazasEnElBanco) {
		this.numPersonas = numPersonas;
		numPlazas = numPlazasEnElBanco;
		BANCO = new Semaphore(numPlazas);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Parque parque = new Parque(20, 5);
		parque.start();
	}
	
	public void start() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < numPersonas; i++)
			executor.submit(new Persona(i + 1, this));
		executor.shutdown();
	}
	
	public boolean sentarseEnElBanco() {
		try {
			if (BANCO.tryAcquire()) {
				System.out.println(Thread.currentThread().getName() + " se ha sentado");
				Thread.sleep((long) (Math.random() * 500)); // tiempo sentada
				return true;
			}
			else
				return false;
		} catch (InterruptedException e) {
			return true;
		} 
	}
	
	public void levantarseDelBanco() {
		BANCO.release();
		System.out.println(Thread.currentThread().getName() + " se ha levantado");
	}
	
	public boolean bancoLleno() {
		return BANCO.availablePermits() == 0;
	}
}
