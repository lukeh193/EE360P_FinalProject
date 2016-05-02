
public class TreeGetThread implements Runnable {

	int value;
	
	public TreeGetThread(int i) {
		value = i;
	}

	@Override
	public void run() {
		TreeTester.concurrentTree.get(value);
	}
	
}
