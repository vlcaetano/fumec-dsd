package _05;

import java.util.concurrent.Semaphore;

public class ReaderWriterStarvation {

	int numReaders = 0;
	int numReadersWaiting = 0;
	int numWriters = 0;
	int numWritersWaiting = 0;
	Semaphore mutex = new Semaphore(1);
	Semaphore wlock = new Semaphore(1);

	public void startRead() {
		P(mutex);
		numReaders++;
		if (numReaders == 1) {
			P(wlock);
		}
		V(mutex);
	}

	public void endRead() {
		P(mutex);
		numReaders--;
		if (numReaders == 0) {
			V(wlock);
		}
		V(mutex);
	}
	
	public void startWrite() {
		P(wlock);
	}
	
	public void endWrite() {
		V(wlock);
	}
	
	private void P(Semaphore s) {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	private void V(Semaphore s) {
		s.release();
	}
}