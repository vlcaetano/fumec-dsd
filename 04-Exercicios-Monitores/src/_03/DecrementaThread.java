package _03;

public class DecrementaThread extends Thread {

	ContadorLimitado cl;

	public DecrementaThread(ContadorLimitado cl) {
		super();
		this.cl = cl;
	}

	@Override
	public void run() {
		while (true) {
			cl.decrementa();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}
}
