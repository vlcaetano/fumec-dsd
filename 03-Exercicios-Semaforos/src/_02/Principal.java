package _02;

public class Principal {

	public static void main(String[] args) {
		BarbeiroDorminhoco bd = new BarbeiroDorminhoco(5);
		BarbeiroThread barbeiro = new BarbeiroThread(bd);
		barbeiro.start();

		for (int i = 0; i < 20; i++) {
			ClienteThread cliente = new ClienteThread(bd, i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			cliente.start();
		}
	}

}
