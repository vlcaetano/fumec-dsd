package _02;

import java.util.concurrent.Semaphore;

public class BarbeiroDorminhoco {

	int contClientes;
	int limite;

	Semaphore clientePronto;
	Semaphore barbeiroPronto;

	Semaphore clienteFinalizou;
	Semaphore barbeiroFinalizou;

	Semaphore mutex;

	public BarbeiroDorminhoco(int limite) {
		this.limite = limite;
		contClientes = 0;

		clientePronto = new Semaphore(0);
		barbeiroPronto = new Semaphore(0);

		clienteFinalizou = new Semaphore(0);
		barbeiroFinalizou = new Semaphore(0);

		mutex = new Semaphore(1);
	}

	public void iniciarServico() {
		P(clientePronto); // espera o cliente chegar (barbeiro dorme)
		V(barbeiroPronto); // sinaliza o cliente que o barbeiro está disponível

		System.out.println("Barbeiro cortando cabelo");

		P(clienteFinalizou); // espera o cliente finalizar
		V(barbeiroFinalizou); // sinaliza o cliente que o barbeiro finalizou

		System.out.println("Corte finalizado");
	}

	public void receberNovoCliente(int id) {

		System.out.println("Cliente " + id + " entrou na barbearia");

		P(mutex);
		if (contClientes == limite) {
			System.out.println("Cliente " + id + " foi embora");
			mutex.release();
			return;
		}
		contClientes++;
		V(mutex);

		V(clientePronto);
		P(barbeiroPronto); // espera o barbeiro estar disponivel

		System.out.println("Cliente " + id + " está cortando o cabelo"); // cliente cortando o cabelo

		V(clienteFinalizou); // sinaliza o barbeiro que o cliente finalizou
		P(barbeiroFinalizou); // espera o barbeiro finalizar

		P(mutex);
		contClientes--;
		V(mutex);
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
