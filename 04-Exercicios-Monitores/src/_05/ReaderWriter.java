package _05;

public class ReaderWriter {
	
	int numActiveReaders = 0;
	boolean in = true;	
	boolean writerWaiting = false;
	boolean writerReleased = false;

	public synchronized void startRead() {
		while(!in) {
			myWait(this);
		}
		
		numActiveReaders++;
	}

	public synchronized void endRead() {
		numActiveReaders--;
		
		if (writerWaiting && numActiveReaders == 0) {
			writerReleased = true;
			notifyAll();
		}
	}

	public synchronized void startWrite() {
		while(!in) {
			myWait(this);
		}
		in = false;
		
		if (numActiveReaders != 0) {
			writerWaiting = true;
			while(!writerReleased) {
				myWait(this);
			}
			writerReleased = false;
			writerWaiting = false;
		}
	}

	public synchronized void endWrite() {
		in = true;
		notifyAll();
	}

	private void myWait(Object obj) {
		try {
			obj.wait();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}