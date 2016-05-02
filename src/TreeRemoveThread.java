
public class TreeRemoveThread implements Runnable {

	int value;
	
	public TreeRemoveThread(int i) {
		value = i;
	}

	@Override
	public void run() {
		TreeTester.concurrentTree.remove(value);
	}
	
}
