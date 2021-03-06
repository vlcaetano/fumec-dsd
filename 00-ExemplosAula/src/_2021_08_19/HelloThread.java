package _2021_08_19;

public class HelloThread extends Thread {
	
    public void run() {
    	for(int i = 0; i < 1000; i++) {
    		if(this.isInterrupted()) {
    			System.out.println("Thread secundária interrompida");
    			break;
    		}
    		System.out.println("Hello World Thread: " + i);
//    		try {
//				Thread.sleep(2);
//			} catch (InterruptedException e) {}
    	}
    }

    public static void main(String[] args) {
        HelloThread t = new HelloThread();
//        t.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Prioridade da thread principal: " + Thread.currentThread().getPriority());
        System.out.println("Prioridade da thread secundária: " + t.getPriority());
        t.start();
    	for(int i = 0; i < 1000; i++) {
    		System.out.println("Thread principal: " + i);
    		if(i == 200) {
    			System.out.println("Interrompendo a thread secundária");
    			t.interrupt();
    		}
//    		try {
//				Thread.sleep(2);
//			} catch (InterruptedException e) {}
    	}
    }
}