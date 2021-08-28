
public class Main {

	public static void main(String[] args) {
		int[] vetor = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50};

		System.out.println("Vetor desordenado: ");
		for (int i : vetor) {
			System.out.println(i + " ");
		}
		
		MergeSortThread t = new MergeSortThread(0, vetor.length, vetor);
		t.start();
		
		try {
			t.join ();
		} catch (InterruptedException e){
			System.out.println("Erro ");
		}
		
		System.out.println("\n----------------------------------------");
		System.out.println("Vetor ordenado: ");
		for (int i : vetor) {
			System.out.println(i + " ");
		}
	}

}
