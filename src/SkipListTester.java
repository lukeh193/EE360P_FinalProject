
public class SkipListTester {

	public static void main(String[] args) {
		BasicSkipList basicList = new BasicSkipList();
		basicList.add(5);
		basicList.add(3);
		basicList.add(2);
		basicList.add(6);
		System.out.println(basicList.print());
	/*	if(basicList.get(4) == null){
			System.out.println("4: This was not in it");
		}
		else{
			System.out.println(basicList.get(4).getValue());
		}
		
		if(basicList.get(6) == null){
			System.out.println("6: This was not in it");
		}
		else{
			System.out.println(basicList.get(6).getValue());
		}*/
		basicList.delete(2);
		System.out.println(basicList.print());

		
		FineTunedSkipList lockedList = new FineTunedSkipList();
		lockedList.add(5);
		lockedList.add(3);
		lockedList.add(2);
		lockedList.add(6);
		System.out.println(lockedList.print());
		lockedList.delete(6);
		System.out.println(lockedList.print());
		
		if(lockedList.get(4) == null){
			System.out.println("4: This was not in it");
		}
		else{
			System.out.println(lockedList.get(4).getValue());
		}
		
		if(lockedList.get(6) == null){
			System.out.println("6: This was not in it");
		}
		else{
			System.out.println(lockedList.get(6).getValue());
		}
		
	}
}
