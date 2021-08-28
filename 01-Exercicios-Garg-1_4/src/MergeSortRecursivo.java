
public class MergeSortRecursivo {

	public static void mergeSortRecursivo(int inicio, int fim, int[] vetor) {

		if (inicio < fim - 1) {

			int meio = (inicio + fim) / 2;

			mergeSortRecursivo(inicio, meio, vetor);

			mergeSortRecursivo(meio, fim, vetor);

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
