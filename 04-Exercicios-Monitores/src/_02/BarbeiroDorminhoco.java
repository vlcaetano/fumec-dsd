package _02;

public class BarbeiroDorminhoco {

	int contClientes;
	int limite;

	boolean barbeiroPronto;
	boolean barbeiroFinalizou;
	boolean clientePronto;
	boolean clienteFinalizou;

	public BarbeiroDorminhoco(int limite) {
		this.limite = limite;
		
		contClientes = 0;
		barbeiroPronto = false;
		barbeiroFinalizou = false;
		clientePronto = false;
		clienteFinalizou = false;
	}

	public synchronized void iniciarServico() {
		while (!clientePronto) {
			myWait(this);
		}
		clientePronto = false;

		barbeiroPronto = true;
		notifyAll();

		System.out.println("Barbeiro cortando cabelo");

		while (!clienteFinalizou) {
			myWait(this);
		}
		clienteFinalizou = false;

		barbeiroFinalizou = true;
		notifyAll();

		System.out.println("Corte finalizado");
	}

	public synchronized void receberNovoCliente(int id) {
		System.out.println("Cliente " + id + " entrou na barbearia");

		if (contClientes == limite) {
			System.out.println("Cliente " + id + " foi embora");
			return;
		}
		contClientes++;

		clientePronto = true;
		notifyAll();

		while (!barbeiroPronto) {
			myWait(this);
		}
		barbeiroPronto = false;

		System.out.println("Cliente " + id + " está cortando o cabelo");

		clienteFinalizou = true;
		notifyAll();

		while (!barbeiroFinalizou) {
			myWait(this);
		}
		barbeiroFinalizou = false;

		contClientes--;
	}

	private void myWait(Object obj) {
		try {
			obj.wait();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
