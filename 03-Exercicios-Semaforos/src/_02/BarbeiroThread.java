package _02;

public class BarbeiroThread extends Thread {

	BarbeiroDorminhoco bd;

	public BarbeiroThread(BarbeiroDorminhoco bd) {
		this.bd = bd;
	}

	@Override
	public void run() {
		while (true) {
			bd.iniciarServico();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}