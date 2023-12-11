package unidad2.generadorsecuencias;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneradorSecuencias {

	private List<Thread> hilos = new LinkedList<>();
	private Queue<Ticket> tickets = new LinkedList<>();
	
	public GeneradorSecuencias(String patron) {
		Set<Character> letras = new TreeSet<>();
		if (patron.matches("(?:[A-Z]\\d+)+")) {
			Matcher m = Pattern.compile("([A-Z])(\\d+)").matcher(patron);
			while (m.find()) {
				char letra = m.group(1).charAt(0);
				int n = Integer.parseInt(m.group(2));
				if (letras.add(letra)) {
					tickets.add(new Ticket(letra, n));
					hilos.add(new EscribeLetras(letra, this));
				}
			}
		}
		else
			throw new IllegalArgumentException(patron);
	}
	
	public synchronized int obtenerTurno(char letra) {
		while (letra != tickets.peek().letra)
			try {
				wait();
			} catch (InterruptedException e) {}
		return tickets.peek().n;
	}
	
	public synchronized void liberarTurno() {
		tickets.offer(tickets.poll());
		notifyAll();
	}
	
	public void start() {
		hilos.forEach(h -> h.start());
	}
	
	private static class Ticket {
		char letra;
		int n;
		public Ticket(char letra, int n) {
			super();
			this.letra = letra;
			this.n = n;
		}
	}
	
}
