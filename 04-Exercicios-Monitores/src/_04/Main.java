package _04;

public class Main {

	public static void main(String[] args) {
		MontanhaRussa mr = new MontanhaRussa(5);
		
		new CarroThread(mr).start();
		for (int i = 0; i < 20; i++) {
			new PassageiroThread(i, mr).start();
		}
	}
}
