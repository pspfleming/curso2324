package unidad2.ejemplos.ejemplo02;

public class Main2 {
	
	public static void tarea() {
		for (int i = 1; i <= 5; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			System.out.printf("%s, mensaje %d\n", Thread.currentThread().getName(), i);
		}
	}
	
	public static void main(String[] args) {
		for (int i = 1; i <= 3; i++)
			new Thread(Main2::tarea, "hilo " + i).start();
	}
}
