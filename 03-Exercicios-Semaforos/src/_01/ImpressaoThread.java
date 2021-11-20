package _01;

public class ImpressaoThread extends Thread {
	char letra;
	Impressao impressao;

	public ImpressaoThread(char letra, Impressao impressao) {
		this.letra = letra;
		this.impressao = impressao;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
				impressao.imprimir(letra);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}
