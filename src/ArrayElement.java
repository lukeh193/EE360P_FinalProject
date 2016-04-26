import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayElement<E> {
	 Lock read;
	 Lock write;
	 Object element;
	 
	 ArrayElement(E item) {
	      this.element = item;
	      this.read = new ReentrantLock();
	      this.write = new ReentrantLock();
	 }
	 
	    void readlock() {read.lock();}
	    void readunlock() {read.unlock();}
	    void writelock() {write.lock();}
	    void writeunlock() {write.unlock();}

}
