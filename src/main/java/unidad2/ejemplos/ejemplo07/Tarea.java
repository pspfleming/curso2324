package unidad2.ejemplos.ejemplo07;

public class Tarea implements Runnable {

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println("en el Hilo");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println("interrumpido mientras dormía");
				Thread.currentThread().interrupt();
			}
			
		}

	}

}
