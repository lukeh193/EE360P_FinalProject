
public class SkipListDeleteThread implements Runnable{
	int value;
	
	public SkipListDeleteThread(int i) {
		value = i;
	}

	@Override
	public void run() {
		SkipListTester.concurrentSkipList.delete(value);
	}
}
