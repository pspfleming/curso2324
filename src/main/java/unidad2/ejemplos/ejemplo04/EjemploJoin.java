package unidad2.ejemplos.ejemplo04;

public class EjemploJoin {
	public static void main(String[] args) throws InterruptedException {
		Thread hilo = new Thread("cuenta atrás") {
			@Override
			public void run() {
				for (int i = 5; i >= 0; i--) {
					System.out.println(i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
				System.out.println("hilo finalizado");
			}
		};
		hilo.start();
		hilo.join();
		System.out.println("método main finalizado");
	}
}