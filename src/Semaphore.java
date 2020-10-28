import java.util.LinkedList;
import java.util.Queue;

public class Semaphore {

	private boolean sem;
	private Queue<Thread> blockedQueue;
	
	public Semaphore() {
		sem = true;
		blockedQueue = new LinkedList<Thread>();
	}
	public boolean semWait(Thread t) {
		boolean out = true;
		if(!sem) {
			blockedQueue.add(t);
			out = false;
		}
		sem=false;
		return out;
	}
	
	public Thread semPost() {
		sem=true;
		return blockedQueue.poll();
	}
}
