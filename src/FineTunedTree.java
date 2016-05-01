import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

public class FineTunedTree {
	
	/* Methods to implement
	 * 
	 * add(Object o)
	 * 
	 * remove(Object o)
	 * 
	 * get(Object o)
	 * 
	 * size()
	 * 
	 */
	
	private FineTunedTreeElement root;
	private int size;
	
	private Lock sizeLock;
	private Lock rootLock;
	private Lock dummyLock;
	
	public FineTunedTree() {
		root = null;
		size = 0;
		sizeLock = new ReentrantLock();
		rootLock = new ReentrantLock();
		dummyLock = new ReentrantLock();
	}
	
	/**
	 * This method uses hand over hand locking to traverse the tree
	 * 
	 * Returns the element by value
	 * 
	 * @param i - element to get
	 * @return - TreeElement if found
	 * 		   - null if element not in tree
	 */
	public FineTunedTreeElement get(int i) {
		
		FineTunedTreeElement cur = root;
		FineTunedTreeElement parent = null;
				
		while(cur != null) {
			
			// Lock the node
			cur.lock.lock();
			
			// Unlock the parent node, if not null (ie at root node)
			if(parent != null) 
				parent.lock.unlock();
			
			if( i < cur.getData() ) {
				parent = cur;
				cur = cur.getLeft();
			} else if( i > cur.getData() ) {
				parent = cur;
				cur = cur.getRight();
			} else {
				cur.lock.unlock();
				return cur;
			}

		}
		
		// Release lock on last node visited if node not found
		if(parent != null)
			parent.lock.unlock();
		return null;
	}
	
	
	/**
	 * Uses hand over hand locking until the parent node of node to insert 
	 * is found, then, creates a new node and ses cur node pointer to it
	 * 
	 * Inserts an element into the tree
	 * 	
	 * No duplicate elements will be allowed in tree
	 * 
	 * @param i - Int to be inserted
	 * @return  - Root of tree if valid insert
	 * 			- null if element already in tree
	 */
	public FineTunedTreeElement insert(int i) {
		
		// Make TreeElement from object to be inserted
		FineTunedTreeElement elem = new FineTunedTreeElement(i);
		
		FineTunedTreeElement cur = root;
		FineTunedTreeElement parent = null;
		
		// Need to probs fix
		if(root == null) {
			rootLock.lock();
			root = elem;
			sizeLock.lock();
			size = 1;
			sizeLock.unlock();
			rootLock.unlock();
			return root;
		}
		
		while(cur != null) {
			
			// Lock the current node
			cur.lock.lock();
			
			// Unlock the parent node if its not null (ie root)
			if(parent != null) {
				parent.lock.unlock();
			}
			
			if(elem.getData() < cur.getData()) {
				// Check if elem exists or not
				if(cur.getLeft() == null) {
					elem.setParent(cur);
					cur.setLeft(elem);
					cur.lock.unlock();
					
					// Get lock for size
					sizeLock.lock();
					size++;
					sizeLock.unlock();

					return root;
				} else {
					parent = cur;
					cur = cur.getLeft();
				}
			} else if(elem.getData() > cur.getData()) {
				// Check if elem exists or not
				if(cur.getRight() == null) {
					elem.setParent(cur);
					cur.setRight(elem);
					cur.lock.unlock();
					
					// Get lock for size
					sizeLock.lock();
					size++;
					sizeLock.unlock();
					
					return root;
				} else {
					parent = cur;
					cur = cur.getRight();
				}
			} else {
				// Element is aready in tree => DONT ALLOW THIS!
				return null;
			}
			
		}
		// Shouldn't ever get to here ...
		return root;

	}
	
	
	
	/**
	 * Removes element from tree
	 * 
	 * Use hand over hand locking to find point where new element should be inserted
	 * Need to have lock on 'cur' node, and the 'next' node (node to be deleted)
	 * 
	 * 	Case 1:	No children
	 * 		- Keep lock on parent, modify 'next' node's pointer
	 * 		- Release both locks
	 * 	Case 2: 1 Child
	 * 		- Replace node to be deleted with its child
	 * 	Case 3: 2 Children
	 * 		- Replace node with its successor
	 * 
	 * TODO: check if deleting root 
	 * 
	 * @param i - Element to be removed
	 * @return 	- true  if element removed successfully
	 * 			- false if element did not exist in tree
	 */
	public boolean remove(int i) {
		
		FineTunedTreeElement cur = null;
		FineTunedTreeElement next = null;
		
		// Obtain dummy node lock
		rootLock.lock();
		next = root;
		if(next == null) {
			rootLock.unlock();
			return false;
		} else {
			next.lock.lock();
			rootLock.unlock();
		}
		
		// Traverse tree, looking 'ahead' to find node to delete
		while(next != null) {
			
			// At node to delete
			if(next.getData() == i) {
				
				// Check number of children
				if(next.numChildren() == 0) {
					// Trying to delete root node
					if(cur == null) {
						sizeLock.lock();
						root = null;
						size--;
						sizeLock.unlock();
						return true;
					} else {
					
						// Find out which node of parent to delete
						if(next.getData() < cur.getData()) {
							// Obtain size lock
							sizeLock.lock();
							cur.setLeft(null);
							size--;
							sizeLock.unlock();
							// Unlock next lock then cur lock
							next.lock.unlock();
							cur.lock.unlock();
							return true;
						} else if(next.getData() > cur.getData()) {
							// Obtain size lock
							sizeLock.lock();
							cur.setRight(null);
							size--;
							sizeLock.unlock();
							// Unlock nodes
							next.lock.unlock();
							cur.lock.unlock();
							return true;
						}
					
					}
					
				// One child
				} else if(next.numChildren() == 1) {
					// Check which child node to replace with
					if(next.getLeft() != null) { // Has left child
						if(cur == null) {
							sizeLock.lock();
							root = next.getLeft();
							size--;
							sizeLock.unlock();
						} else {
							if(next.getData() < cur.getData()) {
								sizeLock.lock();
								cur.setLeft(next.getLeft());
								size--;
								sizeLock.unlock();
							} else {
								sizeLock.lock();
								cur.setRight(next.getLeft());
								size--;
								sizeLock.unlock();
							}
						}
					} else { // Has right child
						if(cur == null) { // Deleting root node
							sizeLock.lock();
							root = next.getRight();
							size--;
							sizeLock.unlock();
						} else {
							if(next.getData() < cur.getData()) {
								sizeLock.lock();
								cur.setLeft(next.getRight());
								size--;
								sizeLock.unlock();
							} else {
								sizeLock.lock();
								cur.setRight(next.getRight());
								size--;
								sizeLock.unlock();
							}	
						}
					}
					if(cur != null)
						cur.lock.unlock();
					return true;
				} 
				// Two Children
				else if(next.numChildren() == 2) {
					if(cur == null) { // Deleting root
						int succVal = findSuccessor(next);
						next.setData(succVal);
					} else {
						cur.lock.unlock();
						int succVal = findSuccessor(next);
						next.setData(succVal);
						next.lock.unlock();
					}
					return true;
				}
			} else { // Continue traversing tree
				if(i < next.getData()) {
					next.getLeft().lock.lock();
					cur = next;
					next = next.getLeft();
				} else {
					next.getRight().lock.lock();
					cur = next;
					next = next.getRight();
				}
			}
			
		}
		
		// Shouldn't get here !!!
		return false;
		
	}
	
	
	
	public void inOrderTraversal() {
		System.out.print("\t");
		inOrder(root);
		System.out.println();
	}
	
	private void inOrder(FineTunedTreeElement e) {
		if(e != null) {
			inOrder(e.getLeft());
			System.out.print(e.getData() + " ");
			inOrder(e.getRight());
		}
	}
	
	private int findSuccessor(FineTunedTreeElement e) {
		
		ArrayList<FineTunedTreeElement> locked = new ArrayList<FineTunedTreeElement> ();
		
		e.getRight().lock.lock();
		FineTunedTreeElement localPrev = null;
		FineTunedTreeElement localNext = e.getRight();
		locked.add(localNext);
		
		while(localNext.getLeft() != null) {
			localNext.getLeft().lock.lock();
			locked.add(localNext.getLeft());
			localPrev = localNext;
			localNext = localNext.getLeft();
		}
		
		// Found replacement
		int retVal = localNext.getData();
		if(localNext.numChildren() == 1) { 
			sizeLock.lock();
			localPrev.setLeft(localNext.getRight());
			size--;
			sizeLock.unlock();
		} else {
			if(localPrev == null) { // Deletion node's subtree only one element
				sizeLock.lock();
				e.setData(localNext.getData());
				e.setRight(null);
				size--;
				sizeLock.unlock();
			} else {
				sizeLock.lock();
				//localPrev.setData(localNext.getData());
				localPrev.setLeft(null);
				size--;
				sizeLock.unlock();
			}
		}
		
		unlockAll(locked);
		
		return retVal;
	}
	
	private void unlockAll(ArrayList<FineTunedTreeElement> list) {
		for(int i = list.size() - 1; i >= 0; i--) {
			list.get(i).lock.unlock();
		}
	}
	
	public int getRoot() {
		return root.getData();
	}
	

}
