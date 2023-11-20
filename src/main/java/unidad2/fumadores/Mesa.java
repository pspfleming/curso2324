package unidad2.fumadores;

public class Mesa {
	private Ingrediente ingrediente1;
	private Ingrediente ingrediente2;
	
	public synchronized void retirar(Ingrediente ingrediente) {
		while (ingrediente == ingrediente1 || ingrediente == ingrediente2)
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		ingrediente1 = ingrediente2 = null;
		notifyAll();
	}
	
	public synchronized void colocar(Ingrediente ingrediente1, Ingrediente ingrediente2) {
		while (this.ingrediente1 != null && this.ingrediente2 != null)
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		this.ingrediente1 = ingrediente1;
		this.ingrediente2 = ingrediente2;
		notifyAll();
	}
}
