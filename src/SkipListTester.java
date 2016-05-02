
public class SkipListTester {

	public static void main(String[] args) {
		BasicSkipList basicList = new BasicSkipList();
		basicList.add(5);
		basicList.add(3);
		basicList.add(2);
		basicList.add(6);
		
		FineTunedSkipList lockedList = new FineTunedSkipList();
		lockedList.add(5);
		lockedList.add(3);
		lockedList.add(2);
		lockedList.add(6);
	}
}
