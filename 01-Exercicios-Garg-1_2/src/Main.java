import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		int tamanhoVetor = 10000;

		int[] vetor = new int[tamanhoVetor];

		for (int i = 0; i < tamanhoVetor; i++) {
			vetor[i] = i;
		}

		System.out.println("Índice " + parallelSearch(1000, vetor, 3));
	}

	public static int parallelSearch(int x, int[] vetor, int numThreads) {
		List<BuscaThread> listaThreads = new ArrayList<>();
		int tamanhoVetor = vetor.length;

		for (int i = 0; i < numThreads; i++) {
			int inicioBusca = (int) Math.ceil((double) tamanhoVetor / numThreads) * i;
			int finalBusca = (int) Math.ceil((double) tamanhoVetor / numThreads) * (i + 1);

			if (finalBusca > tamanhoVetor) {
				finalBusca = tamanhoVetor;
			}
			
			listaThreads.add(new BuscaThread(vetor, x, inicioBusca, finalBusca));
		}

		for (BuscaThread t : listaThreads)
			t.setListaThreads(listaThreads);

		for (BuscaThread t : listaThreads)
			t.start();

		try {
			for (BuscaThread t : listaThreads)
				t.join();
		} catch (InterruptedException e) {
			System.out.println("Erro");
		}

		for (BuscaThread t : listaThreads) {
			if (t.isNumeroEncontrado()) {
				return t.getIndiceNumEncontrado();
			}
		}

		return -1;
	}
}
