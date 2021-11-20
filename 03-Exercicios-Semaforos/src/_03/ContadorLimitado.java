package _03;

import java.util.concurrent.Semaphore;

public class ContadorLimitado {

	int cont;
	Semaphore incremento;
	Semaphore decremento;
	Semaphore mutex;

	public ContadorLimitado(int min, int max) {
		cont = min;
		incremento = new Semaphore(max);
		decremento = new Semaphore(0);
		mutex = new Semaphore(1);
	}

	public void incrementa() {
		try {
			incremento.acquire();
			mutex.acquire();
			cont++;
			System.out.println(cont);
			decremento.release();
			mutex.release();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public void decrementa() {
		try {
			decremento.acquire();
			mutex.acquire();
			cont--;
			System.out.println(cont);
			incremento.release();
			mutex.release();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
