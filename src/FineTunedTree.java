import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	
	public FineTunedTree() {
		root = null;
		size = 0;
		sizeLock = new ReentrantLock();
		rootLock = new ReentrantLock();
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
			
			// Unlock the parent node if its not null (ie parent)
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
	

}
