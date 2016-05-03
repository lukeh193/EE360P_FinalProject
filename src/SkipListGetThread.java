
public class SkipListGetThread  implements Runnable {

	int value;
	
	public SkipListGetThread(int i) {
		value = i;
	}

	@Override
	public void run() {
		SkipListTester.concurrentSkipList.get(value);
	}
	
}
