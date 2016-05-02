import java.util.Random;


public class FineTunedSkipList {

	private FineTunedSkipListEntry head;
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
	public FineTunedSkipList(){
		height = 1;
		head = new FineTunedSkipListEntry(Integer.MIN_VALUE);
		head.setDown(new FineTunedSkipListEntry(null));
		head.setRight(new FineTunedSkipListEntry(Integer.MAX_VALUE));
	}
	
	public void add(Integer addValue){
		head.lock();
		FineTunedSkipListEntry oldHead = head;
		if(!head.getRight().getValue().equals(Integer.MAX_VALUE)){
			FineTunedSkipListEntry newHead = new FineTunedSkipListEntry(Integer.MIN_VALUE);
			newHead.setDown(head);
			newHead.setRight(new FineTunedSkipListEntry(Integer.MAX_VALUE));
			FineTunedSkipListEntry tempEnd = head.getRight();
			tempEnd.lock();
			tempEnd.setLeft(head);
			FineTunedSkipListEntry prevEnd;
			while (tempEnd.getValue() != Integer.MAX_VALUE){
				prevEnd = tempEnd;
				tempEnd = tempEnd.getRight();
				tempEnd.lock();
				prevEnd.unlock();
			}
			newHead.getRight().setDown(tempEnd);
			tempEnd.unlock();
			height++;
			head = newHead;
		}
		oldHead.unlock();
		
		int myLevel = 1;
		Random random = new Random();
		myLevel = random.nextInt(height) + 1;
		/*while(random.nextBoolean() && myLevel < height){
			myLevel++;
		}*/
		FineTunedSkipListEntry currentEntry = head;
		FineTunedSkipListEntry myRecentEntry = null;
		FineTunedSkipListEntry tempEntry = null;
		currentEntry.lock();

		for(int i = 0; i < (height - myLevel); i++){
			tempEntry = currentEntry;
			currentEntry = currentEntry.getDown();
			currentEntry.lock();
			tempEntry.unlock();
		}
		
		while(currentEntry != null && currentEntry.getRight() != null){
			while(currentEntry.getRight().getValue() < addValue){
				tempEntry = currentEntry;
				currentEntry = currentEntry.getRight();
				currentEntry.lock();
				tempEntry.unlock();
			}
			tempEntry = currentEntry.getRight();
			tempEntry.lock();
			if(tempEntry.getValue() > addValue){
				FineTunedSkipListEntry toBeAdded = new FineTunedSkipListEntry(addValue);
				toBeAdded.lock();
				currentEntry.setRight(toBeAdded);
				toBeAdded.setRight(tempEntry);
				toBeAdded.setLeft(currentEntry);
				tempEntry.setLeft(toBeAdded);
				if(myRecentEntry != null){
					myRecentEntry.setDown(toBeAdded);
					myRecentEntry.unlock();
				}
				myRecentEntry = toBeAdded;
				tempEntry.unlock();
				tempEntry = currentEntry; //cur Entry already locked
				currentEntry = currentEntry.getDown();
				
				if (currentEntry != null) { //|| currentEntry.getValue() != null) {
					currentEntry.lock();
				}
				
				tempEntry.unlock();
			}
		}
		if (myRecentEntry != null)
			myRecentEntry.unlock();
	}
	
	public FineTunedSkipListEntry get(int val) {
		FineTunedSkipListEntry cur = head;
		cur.lock();
		while (cur != null) {
			while (cur.getRight().getValue() < val) {
				cur = cur.getRight();
				if(cur == null){
					return null;
				}
				cur.lock();
				if (cur.getLeft() != null) {
					cur.getLeft().unlock();
				}
			}
			FineTunedSkipListEntry temp;
			if (cur.getRight().getValue() > val) {
				temp = cur;
				cur = cur.getDown();
				if(cur == null){
					return null;
				}
				cur.lock();
				temp.unlock();
			} else if (cur.getRight().getValue() == val) {
				temp = cur;
				cur = cur.getRight();
				cur.lock();
				temp.unlock();
				break;
			}
		}
		cur.unlock();
		if(cur.getValue() != val){
			return null;
		}
		return cur;
	}
	
	public void delete(int val) { //do we need to return a bool if it works?
		FineTunedSkipListEntry localVal = this.get(val);
		while (localVal != null) {
			localVal.lock();
			FineTunedSkipListEntry preVal = localVal.getLeft();
			preVal.lock();
			FineTunedSkipListEntry nextVal = localVal.getRight();
			nextVal.lock();
			preVal.setRight(nextVal);
			nextVal.setLeft(preVal);
			FineTunedSkipListEntry oldLocal = localVal;
			localVal = localVal.getDown();
			preVal.unlock();
			nextVal.unlock();
			oldLocal.setAllToNull();
			oldLocal.unlock();
		}
	}
	
	public StringBuilder print() {
		StringBuilder result = new StringBuilder();
		FineTunedSkipListEntry current = head;
		FineTunedSkipListEntry leftNode = head;
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
