package unidad2.actividad14;

import java.util.Random;

public class Banco {
	
	static final Random R = new Random();
	private final int plazas;
	private int ocupadas;
	
	public Banco(int plazas) {
		this.plazas = plazas;
	}
	
	public synchronized void sentarse() {
		while (ocupadas == plazas)
			try {
				wait();
			} catch (InterruptedException e) {
			}
			ocupadas++;
			System.out.println(Thread.currentThread().getName() + " se ha sentado");
			try {
				Thread.sleep(R.nextLong(701) + 100);
			} catch (InterruptedException e) {
			}
			ocupadas--;
			System.out.println(Thread.currentThread().getName() + " se ha levantado");
			notifyAll();
	}
	
}
