package _05;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		ReaderWriter rw = new ReaderWriter();
		Random r = new Random();
		int teste = 0;

		for (int i = 0; i < 50; i++) {
			teste = r.nextInt(100); // para gerar as threads aleatoriamente
			if (teste >= 80) {
				new WriterThread(rw).start();
			} else {
				new ReaderThread(rw).start();
			}
		}
	}

}
