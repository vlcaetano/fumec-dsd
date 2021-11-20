package _03;

public class Main {

	public static void main(String[] args) {
		ContadorLimitado cl = new ContadorLimitado(0, 50);

		new IncrementaThread(cl).start();
		new IncrementaThread(cl).start();
		new DecrementaThread(cl).start();
	}
}
