
public class MergeSortThread extends Thread {
	
	private int inicio;
	private int fim;
	private int[] vetor;
	
	public MergeSortThread(int inicio, int fim, int[] vetor) {
		this.inicio = inicio;
		this.fim = fim;
		this.vetor = vetor;
	}

	@Override
	public void run() {
		if (inicio < fim - 1) {

			int meio = (inicio + fim) / 2;

			MergeSortThread mst1 = new MergeSortThread(inicio, meio, vetor);
			MergeSortThread mst2 = new MergeSortThread(meio, fim, vetor);

			mst1.start();
			mst2.start();
			
			try {
				mst1.join ();
				mst2.join ();
			} catch (InterruptedException e){
				System.out.println("Erro");
			}
			
			intercala(vetor, inicio, meio, fim);
		}
	}
	
	private static void intercala(int[] vetor, int inicio, int meio, int fim) {
		int[] novoVetor = new int[fim - inicio];
		int i = inicio;
		int m = meio;
		
		int pos = 0;
		
		while (i < meio && m < fim) {
			
			if (vetor[i] <= vetor[m]) {
				novoVetor[pos] = vetor[i];
				pos = pos + 1;
				i = i + 1;
			} else {
				novoVetor[pos] = vetor[m];
				pos = pos + 1;
				m = m + 1;
			}
		}
		
		while (i < meio) {
			novoVetor[pos] = vetor[i];
			pos = pos + 1;
			i = i + 1;
		}
		
		while (m < fim) {
			novoVetor[pos] = vetor[m];
			pos = pos + 1;
			m = m + 1;
		}
		
		for (pos = 0, i = inicio; i < fim; i++, pos++) {
			vetor[i] = novoVetor[pos];
		}
	}

}
