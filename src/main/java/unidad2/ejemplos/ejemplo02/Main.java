package unidad2.ejemplos.ejemplo02;

public class Main {
	
	public static void main(String[] args) {
		Runnable tarea = new Tarea();
		for (int i = 1; i <= 3; i++)
			new Thread(tarea, "hilo " + i).start();
	}
}
