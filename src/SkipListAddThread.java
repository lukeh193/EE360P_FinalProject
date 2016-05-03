
public class SkipListAddThread implements Runnable {
	int value;
	
	public SkipListAddThread(int i) {
		value = i;
	}
	
	@Override
	public void run() {
		SkipListTester.concurrentSkipList.add(value);
	}
}
