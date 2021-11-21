package _05;

public class ReaderThread extends Thread {

	ReaderWriter rw;

	public ReaderThread(ReaderWriter rw) {
		this.rw = rw;
	}

	@Override
	public void run() {
		rw.startRead();
		System.out.println("O reader está lendo");
		sleep(500);
		System.out.println("O reader finalizou a leitura");
		rw.endRead();
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}
