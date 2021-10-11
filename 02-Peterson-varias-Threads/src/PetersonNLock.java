
public class PetersonNLock implements Lock {
	private volatile int level[];
	private volatile int waiting[];
	private volatile int N;
	

	public PetersonNLock(int N) {
		this.N = N;
		this.level = new int[N];
		this.waiting = new int[N];
		
		for (int i = 0; i < N; i++) {
			this.level[i] = -1;
			this.waiting[i] = -1;
		}
	}

	@Override
	public void requestCS(int i) {
		for (int m = 0; m < N; m++) {
			level[i] = m;
			waiting[m] = i;
			
			for (int k = 0; k < N; k++) {
				if (k != i) {
					while (waiting[m] == i && level[k] >= m);
				}
			}
		}
	}

	@Override
	public void releaseCS(int i) {
		level[i] = -1;
	}

}
