import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int[] vetor = new int[30];
		Random random = new Random();
		
		System.out.println("Vetor desordenado: ");
		for (int i = 0; i < vetor.length; i++) {
			vetor[i] = random.nextInt(100);
			System.out.print(vetor[i] + " ");
		}

		MergeSortThread t = new MergeSortThread(0, vetor.length, vetor);
		t.start();

		try {
			t.join();
		} catch (InterruptedException e) {
			System.out.println("Erro");
		}

		System.out.println();
		System.out.println("Vetor ordenado: ");
		for (int i : vetor) {
			System.out.print(i + " ");
		}
	}
}
