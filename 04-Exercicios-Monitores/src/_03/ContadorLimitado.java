package _03;

public class ContadorLimitado {

	int cont;

	int min;
	int max;

	public ContadorLimitado(int min, int max) {
		this.min = min;
		this.max = max;

		cont = min;
	}

	public synchronized void incrementa() {
		while (cont >= max) {
			myWait(this);
		}
		cont++;
		System.out.println(cont);
		notifyAll();
	}

	public synchronized void decrementa() {
		while (cont <= min) {
			myWait(this);
		}
		cont--;
		System.out.println(cont);
		notifyAll();
	}

	private void myWait(Object obj) {
		try {
			obj.wait();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
