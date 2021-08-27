import java.util.List;

public class BuscaThread extends Thread {

	private List<Integer> lista;
	private Integer numero;
	private char sentido;
	private boolean numeroEncontrado = false;
	private BuscaThread outraThread;
	
	public BuscaThread(List<Integer> lista, Integer numero, char sentido) {
		this.lista = lista;
		this.numero = numero;
		this.sentido = sentido;
	}
	
	public boolean isNumeroEncontrado() {
		return numeroEncontrado;
	}

	public void setOutraThread(BuscaThread outraThread) {
		this.outraThread = outraThread;
	}
	
	@Override
	public void run() {
		if (sentido == 'c') {
			buscaCrescente();
		} else {
			buscaDescrescente();
		}
	}

	private void buscaCrescente() {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).equals(numero)) {
				System.out.println("Thread Crescente: Encontrou");
				numeroEncontrado = true;
				outraThread.interrupt();
				break;
			}
			
			if (this.isInterrupted()) {
				System.out.println("Thread Crescente: Interrompida");
				break;
			}
		}
	}
	
	private void buscaDescrescente() {
		for (int i = lista.size()-1; i > 0; i--) {
			if (lista.get(i).equals(numero)) {
				System.out.println("Thread Decrescente: Encontrou");
				numeroEncontrado = true;
				outraThread.interrupt();
				break;
			}
			
			if (this.isInterrupted()) {
				System.out.println("Thread Decrescente: Interrompida");
				break;
			}
		}
	}
}
