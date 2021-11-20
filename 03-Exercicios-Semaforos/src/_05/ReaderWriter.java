package _05;

import java.util.concurrent.Semaphore;

public class ReaderWriter {

	Semaphore in = new Semaphore(1);
	Semaphore out = new Semaphore(1);
	Semaphore writer = new Semaphore(0);
	int numActiveReaders = 0;
	boolean writerWaiting = false;

	public void startRead() {
		P(in);
		numActiveReaders++;
		V(in);
	}

	public void endRead() {
		P(out);
		numActiveReaders--;
		if (writerWaiting && numActiveReaders == 0) {
			V(writer);
		}
		V(out);
	}

	public void startWrite() {
		P(in);
		P(out);
		if (numActiveReaders == 0) {
			V(out);
		} else {
			writerWaiting = true;
			V(out);
			P(writer);
			writerWaiting = false;
		}
	}

	public void endWrite() {
		V(in);
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