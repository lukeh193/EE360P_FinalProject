import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineTunedTreeElement {
	
	private int data;
	private FineTunedTreeElement parent;
	private FineTunedTreeElement left;
	private FineTunedTreeElement right;
	
	Lock lock;
	
	public FineTunedTreeElement(int o) {
		data = o;
		parent = null;
		left = null;
		right = null;
		lock = new ReentrantLock();
	}
	
	/* Getters */
	public int getData() {
		return data;
	}
	
	public FineTunedTreeElement getLeft() {
		return left;
	}
	
	public FineTunedTreeElement getRight() {
		return right;
	}
	
	public FineTunedTreeElement getParent() {
		return parent;
	}
		
	
	/* Setters */
	public void setLeft(FineTunedTreeElement e) {
		left= e;
	}
	
	public void setRight(FineTunedTreeElement e) {
		right = e;
	}
	
	public void setData(int i) {
		data = i;
	}
	
	public void setParent(FineTunedTreeElement e) {
		parent = e;
	}
	
	
	/* Other methods */
	public int numChildren() {
		int num = 0;
		if(left != null) 
			num++;
		if(right != null) 
			num++;
		
		return num;
	}
}
