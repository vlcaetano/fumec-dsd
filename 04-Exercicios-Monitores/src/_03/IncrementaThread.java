package _03;

public class IncrementaThread extends Thread {

	ContadorLimitado cl;

	public IncrementaThread(ContadorLimitado cl) {
		super();
		this.cl = cl;
	}

	@Override
	public void run() {
		while (true) {
			cl.incrementa();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}
}
