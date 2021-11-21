package _01;

public class Impressao {

	int contAeB;
	int contC;

	public Impressao() {
		contAeB = 0;
		contC = 0;
	}

	public synchronized void imprimir(char letra) {
		if (letra != 'C') {
			contAeB++;
			System.out.println(letra);
		} else if (contAeB > contC) {
			contC++;
			System.out.println(letra);
		}
	}
}
