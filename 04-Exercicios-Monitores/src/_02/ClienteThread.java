package _02;

public class ClienteThread extends Thread {
	
	BarbeiroDorminhoco bd;
	int id;
	
	public ClienteThread(BarbeiroDorminhoco bd, int id) {
		this.bd = bd;
		this.id = id;
	}

	@Override
	public void run() {
		try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
		
		bd.receberNovoCliente(id);
	}
}
