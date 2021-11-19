
public interface Resource {
	public void acquire(int i) throws InterruptedException;
	public void release(int i);
}
