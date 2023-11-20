package unidad2.ejemplos.ejemplo01;

public class Main2 {
	public static void main(String[] args) throws InterruptedException {
		for (int i = 1; i <= 3; i++)
			new Thread("hilo " + i) {
				@Override
				public void run() {
					for (int i = 1; i <= 5; i++) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
						System.out.println(getName() + ", mensaje " + i);
					}
				}
			}.start();
		Thread.sleep(300);
	}
}
