package unidad2.ejemplos.ejemplo01;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		for (int i = 1; i <= 3; i++)
			new UnHilo(i).start();
		Thread.sleep(300000);
	}
}
