package _04;

import java.util.concurrent.Semaphore;

public class MontanhaRussa {
	int capacidadeCarro;
	int contPassageirosEmbarcados;

	Semaphore carroCheio;
	Semaphore carroDescarregado;

	Semaphore mutex;

	Semaphore lugarLivre;
	Semaphore prontoParaDesembarque;

	public MontanhaRussa(int capacidadeCarro) {
		this.capacidadeCarro = capacidadeCarro;

		contPassageirosEmbarcados = 0;

		carroCheio = new Semaphore(0);
		carroDescarregado = new Semaphore(0);

		mutex = new Semaphore(1);

		lugarLivre = new Semaphore(capacidadeCarro);
		prontoParaDesembarque = new Semaphore(0);
	}

	public void embarcar(int id) {
		P(lugarLivre);
		
		P(mutex);
		if (capacidadeCarro - contPassageirosEmbarcados == 1) { // ultimo passageiro a embarcar
			System.out.println("Passageiro " + id + " embarcou por ultimo");
			contPassageirosEmbarcados++;
			V(carroCheio);
		} else if (capacidadeCarro > contPassageirosEmbarcados) {
			System.out.println("Passageiro " + id + " embarcou");
			contPassageirosEmbarcados++;
		}
		V(mutex);
	}

	public void desembarcar(int id) {
		P(prontoParaDesembarque);
		
		P(mutex);
		if (contPassageirosEmbarcados - 1 == 0) { // ultimo passageiro a desembarcar
			for (int i = 0; i < capacidadeCarro; i++) {
				V(lugarLivre);
			}
			System.out.println("Passageiro " + id + " desembarcou por ultimo");
			V(carroDescarregado);
			contPassageirosEmbarcados--;
		} else {
			System.out.println("Passageiro " + id + " desembarcou");
			contPassageirosEmbarcados--;
		}
		V(mutex);
	}

	public void carregar() {
		P(carroCheio);
	}

	public void andar() {
		System.out.println("Montanha russa partiu");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("Montanha russa chegou");
	}

	public void descarregar() {
		for (int i = 0; i < capacidadeCarro; i++) {
			V(prontoParaDesembarque);
		}
		P(carroDescarregado);
	}

	private void P(Semaphore s) {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	private void V(Semaphore s) {
		s.release();
	}
}
