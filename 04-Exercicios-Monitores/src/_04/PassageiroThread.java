package _04;

public class PassageiroThread extends Thread {
	int id;
	MontanhaRussa mr;

	public PassageiroThread(int id, MontanhaRussa mr) {
		this.id = id;
		this.mr = mr;
	}

	@Override
	public void run() {
		mr.embarcar(id);
		mr.desembarcar(id);
	}
}
