
import java.util.concurrent.locks.*;

public class Element {
	
	Object item;
	
	Lock writeLock;
	Lock readLock;
	
	public Element(Object o) {
		item = o;
		writeLock = new ReentrantLock();
		readLock = new ReentrantLock();
	}

}
