package _04;

public class MontanhaRussa {
	int capacidadeCarro;
	int contPassageirosEmbarcados;
	int contLugaresLivres;
	
	boolean carroCheio;
	boolean carroDescarregado;
	boolean prontoParaDesembarque;

	public MontanhaRussa(int capacidadeCarro) {
		this.capacidadeCarro = capacidadeCarro;

		contLugaresLivres = capacidadeCarro;
		contPassageirosEmbarcados = 0;

		carroCheio = false;
		carroDescarregado = false;

		prontoParaDesembarque = false;
	}

	public synchronized void embarcar(int id) {
		while(contLugaresLivres == 0) {
			myWait(this);
		}
		contLugaresLivres--;
		
		if (contLugaresLivres == 0) {
			System.out.println("Passageiro " + id + " embarcou por ultimo");
			contPassageirosEmbarcados++;
			carroCheio = true;
			notifyAll();
		} else {
			System.out.println("Passageiro " + id + " embarcou");
			contPassageirosEmbarcados++;
		}
	}

	public synchronized void desembarcar(int id) {
		while(!prontoParaDesembarque) {
			myWait(this);
		}
		
		contPassageirosEmbarcados--;
		if (contPassageirosEmbarcados == 0) {
			contLugaresLivres = capacidadeCarro;
			System.out.println("Passageiro " + id + " desembarcou por ultimo");
			carroDescarregado = true;
			prontoParaDesembarque = false;
		} else {
			System.out.println("Passageiro " + id + " desembarcou");
		}
		notifyAll();
	}

	public synchronized void carregar() {
		while(!carroCheio) {
			myWait(this);
		}
		carroCheio = false;
	}

	public synchronized void andar() {
		System.out.println("Montanha russa partiu");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("Montanha russa chegou");
	}

	public synchronized void descarregar() {
		prontoParaDesembarque = true;
		notifyAll();
		
		while(!carroDescarregado) {
			myWait(this);
		}
		carroDescarregado = false;
	}

	private void myWait(Object obj) {
		try {
			obj.wait();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
