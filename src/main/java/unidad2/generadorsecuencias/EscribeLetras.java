package unidad2.generadorsecuencias;

public class EscribeLetras extends Thread {

	private char letra;
	private GeneradorSecuencias gs;
	
	public EscribeLetras(char letra, GeneradorSecuencias gs) {
		super("Hilo " + letra);
		this.letra = letra;
		this.gs = gs;
	}
	
	@Override
	public void run() {
		while (true) {
			int n = gs.obtenerTurno(letra);
			for (int i=0; i<n; i++) {
				System.out.print(letra);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
			}
			gs.liberarTurno();
		}
	}
	
}
