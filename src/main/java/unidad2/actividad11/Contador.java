package unidad2.actividad11;

public class Contador {
	private Integer n;

	public Contador(int n) {
		this.n = n;
	}

	public void inc() {
		synchronized (this) {
			n++;
		}
	}

	public int get() {
		return n;
	}
}
