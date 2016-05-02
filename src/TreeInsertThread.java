
public class TreeInsertThread implements Runnable {

	int value;
	
	public TreeInsertThread(int i) {
		value = i;
	}
	
	@Override
	public void run() {
		TreeTester.concurrentTree.insert(value);
	}

}
