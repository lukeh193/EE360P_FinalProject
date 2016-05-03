import java.util.Random;


public class BasicSkipList {

	private SkipListEntry head;
	private int height;
	
	/*
	 * make all nodes level 1
		j ← 1
		while the number of nodes at level j > 1 do
		  for each i'th node at level j do
		    if i is odd 
		      if i is not the last node at level j
		        randomly choose whether to promote it to level j+1
		      else
		        do not promote
		      end if
		    else if i is even and node i-1 was not promoted
		      promote it to level j+1
		    end if
		  repeat
		  j ← j + 1
		repeat
	 */
	public BasicSkipList(){
		height = 1;
		head = new SkipListEntry(Integer.MIN_VALUE);
		head.setDown(new SkipListEntry(null));
		head.setRight(new SkipListEntry(Integer.MAX_VALUE));
	}
	
	public void add(Integer addValue){
		//System.out.println("add " + addValue);
		if(!head.getRight().getValue().equals(Integer.MAX_VALUE)){
			SkipListEntry newHead = new SkipListEntry(Integer.MIN_VALUE);
			newHead.setDown(head);
			newHead.setRight(new SkipListEntry(Integer.MAX_VALUE));
			SkipListEntry tempEnd = head.getRight();
			tempEnd.setLeft(head);
			while (tempEnd.getValue() != Integer.MAX_VALUE){
				tempEnd = tempEnd.getRight();
			}
			newHead.getRight().setDown(tempEnd);
			height ++;
			head = newHead;
		}
		
		int myLevel = 1;
		Random random = new Random();
		while(random.nextBoolean() && myLevel <= height){
			myLevel++;
		}
		SkipListEntry currentEntry = head;
		SkipListEntry myRecentEntry = null;
		for(int i = 0; i< (height-myLevel); i++){
			currentEntry = currentEntry.getDown();
		}

		while(currentEntry != null && currentEntry.getRight() != null){
			while(currentEntry.getRight().getValue() < addValue){
				currentEntry = currentEntry.getRight();
			}
			if(currentEntry.getRight().getValue() > addValue){
				SkipListEntry toBeAdded = new SkipListEntry(addValue);
				SkipListEntry temp = currentEntry.getRight();
				currentEntry.setRight(toBeAdded);
				toBeAdded.setRight(temp);
				toBeAdded.setLeft(currentEntry);
				temp.setLeft(toBeAdded);
				if(myRecentEntry != null){
					myRecentEntry.setDown(toBeAdded);
				}
				myRecentEntry = toBeAdded;
				currentEntry = currentEntry.getDown();
			}
		}
	}
	
	public SkipListEntry get(int val) {
		SkipListEntry cur = head;
		while (cur != null) {
			while (cur.getRight().getValue() < val) {
				cur = cur.getRight();
			}
			if (cur.getRight().getValue() > val) {
				cur = cur.getDown();
			} else if (cur.getRight().getValue() == val) {
				cur = cur.getRight();
				return cur;
			}
		}
		return null;
	}
	
	public void delete(int val) { //do we need to return a bool if it works?
		SkipListEntry localVal = this.get(val);
		while (localVal != null) {
			SkipListEntry preVal = localVal.getLeft();
			SkipListEntry nextVal = localVal.getRight();
			preVal.setRight(nextVal);
			nextVal.setLeft(preVal);
			SkipListEntry oldLocal = localVal;
			localVal = localVal.getDown();
			oldLocal.setAllToNull();
		}
	}
	
	public StringBuilder print() {
		StringBuilder result = new StringBuilder();
		SkipListEntry current = head;
		SkipListEntry leftNode = head;
		while(leftNode.getValue() != null){
			while(current != null){
				result = result.append(current.getValue().toString() + " ");
				current = current.getRight();
			}
			leftNode = leftNode.getDown();
			current = leftNode;
			result.append("\n");
		}
		return result;
	}
}
