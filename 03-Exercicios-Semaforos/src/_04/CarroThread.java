package _04;

public class CarroThread extends Thread {
	MontanhaRussa mr;

	public CarroThread(MontanhaRussa mr) {
		this.mr = mr;
	}

	@Override
	public void run() {
		while (true) {
			mr.carregar();
			mr.andar();
			mr.descarregar();
		}
	}
}
