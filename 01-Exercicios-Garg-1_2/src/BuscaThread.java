import java.util.List;

public class BuscaThread extends Thread {

	private int[] vetor;
	private int numero;
	private int inicioBusca;
	private int finalBusca;

	private boolean numeroEncontrado = false;
	private int indiceNumEncontrado = -1;
	private List<BuscaThread> listaThreads;

	public BuscaThread(int[] vetor, int numero, int inicioBusca, int finalBusca) {
		super();
		this.vetor = vetor;
		this.numero = numero;
		this.inicioBusca = inicioBusca;
		this.finalBusca = finalBusca;
	}

	public boolean isNumeroEncontrado() {
		return numeroEncontrado;
	}

	public int getIndiceNumEncontrado() {
		return indiceNumEncontrado;
	}

	public void setListaThreads(List<BuscaThread> listaThreads) {
		this.listaThreads = listaThreads;
	}

	@Override
	public void run() {
		busca();
	}

	private void busca() {
		for (int i = inicioBusca; i < finalBusca; i++) {
			if (numero == vetor[i]) {
				numeroEncontrado = true;
				indiceNumEncontrado = i;
				interromperOutrasThreads();
				System.out.println("Encontrado entre " + inicioBusca + " e " + finalBusca);
				break;
			}

			if (this.isInterrupted()) {
				System.out.println("Interrompendo busca entre " + inicioBusca + " e " + finalBusca);
				break;
			}
		}
	}

	private void interromperOutrasThreads() {
		for (BuscaThread t : listaThreads) {
			t.interrupt();
		}
	}
}
