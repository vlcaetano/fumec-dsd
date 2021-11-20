package _01;

import java.util.concurrent.Semaphore;

public class Impressao {
	
	int contAeB;
	int contC;
	Semaphore mutex;
	
	public Impressao() {
		contAeB = 0;
		contC = 0;
		mutex = new Semaphore(1);
	}
	
	public void imprimir(char letra) {
		try {
			mutex.acquire();
			if (letra != 'C') {
				contAeB++;
				System.out.println(letra);
			} else if (contAeB > contC) {
				contC++;
				System.out.println(letra);
			}
			mutex.release();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
