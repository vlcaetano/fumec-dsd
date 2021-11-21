package _05;

public class WriterThread extends Thread {

	ReaderWriter rw;

	public WriterThread(ReaderWriter rw) {
		this.rw = rw;
	}

	@Override
	public void run() {
		rw.startWrite();
		System.out.println("O writer está escrevendo");
		sleep(2500);
		System.out.println("O writer terminou a escrita");
		rw.endWrite();
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}
