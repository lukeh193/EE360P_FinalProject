import java.util.concurrent.locks.ReentrantLock;


public class FineTunedSkipListEntry {

	private Integer value;
	private FineTunedSkipListEntry down;
	private FineTunedSkipListEntry right;
	private FineTunedSkipListEntry left;
	private ReentrantLock lock;
	
	
	public FineTunedSkipListEntry(Integer value){
		this.value = value;
		down = null;
		right = null;
		left = null;
		lock = new ReentrantLock();
	}
	
	public FineTunedSkipListEntry getDown() {
		return down;
	}
	public void setDown(FineTunedSkipListEntry down) {
		this.down = down;
	}
	public FineTunedSkipListEntry getLeft() {
		return left;
	}
	public void setLeft(FineTunedSkipListEntry left) {
		this.left = left;
	}
	
	public FineTunedSkipListEntry getRight() {
		return right;
	}
	public void setRight(FineTunedSkipListEntry right) {
		this.right = right;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public void lock() {
		lock.lock();
	}
	
	public void unlock() {
		lock.unlock();
	}
	
	public void setAllToNull() {
		this.left = null;
		this.right = null;
		this.down = null;
	}

}
