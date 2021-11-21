package _01;

public class Main {

	public static void main(String[] args) {
		Impressao impressao = new Impressao();
		ImpressaoThread a = new ImpressaoThread('A', impressao);
		ImpressaoThread b = new ImpressaoThread('B', impressao);
		ImpressaoThread c = new ImpressaoThread('C', impressao);
		
		a.start();
		b.start();
		c.start();
	}

}
