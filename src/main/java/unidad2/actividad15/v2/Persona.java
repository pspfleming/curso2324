package unidad2.actividad15.v2;

public class Persona implements Runnable {

	private Parque parque;
	private String nombre;
	
	public Persona(int id, Parque parque) {
		nombre = "Persona " + id;
		this.parque = parque;
	}

	@Override
	public void run() {
		Thread.currentThread().setName(nombre);
		try {
			do {
				System.out.println(Thread.currentThread().getName() + " pasea por el parque");
				Thread.sleep((long) (Math.random() * 2000 + 1000)); // tiempo paseando
				System.out.println(Thread.currentThread().getName() + " llega al banco");
			} while (!parque.sentarseEnElBanco());
			parque.levantarseDelBanco();
		} catch (InterruptedException e) {
		}
	}

}
