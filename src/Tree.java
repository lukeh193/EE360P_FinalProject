
public class Tree {
	
	private TreeElement root;
	private int size;
	
	public Tree() {
		root = null;
		size = 0;
	}
	
	/**
	 * Inserts an element into the tree
	 * 	
	 * No duplicate elements will be allowed in tree
	 * 
	 * @param i - Int to be inserted
	 * @return  - Root of tree if valid insert
	 * 			- null if element already in tree
	 */
	public TreeElement insert(int i) {
		
		// Make TreeElement from object to be inserted
		TreeElement elem = new TreeElement(i);
		
		// Case 0: Tree is empty
		if(root == null) {
			root = elem;
			size = 1; 
			return root;
		} 
			
		// Traverse tree to see where to insert element
		TreeElement cur = root;
		TreeElement next = null;
		
		while(cur != null) {
			if(elem.getData() < cur.getData()) {
				// Check if left elem exists or not
				if(cur.getLeft() == null) {
					cur.setLeft(elem);
					elem.setParent(cur);
					return root;
				} else {
					cur = cur.getLeft();
				}
			} else if(elem.getData() > cur.getData()){
				if(cur.getRight() == null) {
					cur.setRight(elem);
					elem.setParent(cur);
					return root;
				} else {
					cur = cur.getRight();
				}
			} else { 
				return null;
			}
		}
		
		return root;
	}
	
	/**
	 * Removes element from tree
	 * 
	 * TODO: check if deleting root 
	 * 
	 * @param i - Element to be removed
	 * @return 	- true  if element removed successfully
	 * 			- false if element did not exist in tree
	 */
	public boolean remove(int i) {
		
		boolean isRoot = false;
		TreeElement parent = null;
		TreeElement cur = root;
		
		if(root.getData() == i) 
			isRoot = true;
		
		while(cur != null) {
			if(i < cur.getData()) {
				parent = cur;
				cur = cur.getLeft();
			} else if (i > cur.getData()) {
				parent = cur;
				cur = cur.getRight();
			} else { 
				
				// Case 1 : No children
				if( cur.numChildren() == 0 ) {
										
					// Figure out which edge of parent to delete
					if(i < parent.getData()) {
						parent.setLeft(null);
					} else {
						parent.setRight(null);
					}
					
				}
				
				// Case 2: One child
				else if( cur.numChildren() == 1 ) {
					
					// Figure out which child node has
					if(cur.getLeft() != null) {
						
						if(parent == null) {
							root = cur.getLeft();
							cur.getLeft().setParent(null);
						} else {
						
							// Figure out which edge of parent to change
							if( i < parent.getData() ) {
								parent.setLeft(cur.getLeft());
								cur.getLeft().setParent(parent);
							} else {
								parent.setRight(cur.getLeft());
								cur.getLeft().setParent(parent);
							}
							
						}
						
					} else {
						
						if(parent == null) {
							root = cur.getRight();
							cur.getRight().setParent(null);
						} else {
						
							// Figure out which edge of parent to change
							if( i < parent.getData() ) {
								parent.setLeft(cur.getRight());
								cur.getRight().setParent(parent);
							} else {
								parent.setRight(cur.getRight());
								cur.getRight().setParent(parent);
							}
						
						}
						
					}
					
				}
				
				// Case 3: Two children - traverse right and then as far to the left
				else if( cur.numChildren() == 2 ) {
					TreeElement prevSuccessor = cur;
					TreeElement successor = cur.getRight();
					while( successor.getLeft() != null ) {
						prevSuccessor = successor;
						successor = successor.getLeft();
					}
					
					// Change data of element to be removed
					cur.setData(successor.getData());
					
					// Remove 'leaf element'
					if(prevSuccessor.getLeft().getData() == i) {
						prevSuccessor.setLeft(null);
					} else {
						prevSuccessor.setRight(null);
					}
					
				}
				
				size--;
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Returns the element by value
	 * 
	 * @param i - element to get
	 * @return - TreeElement if found
	 * 		   - null if element not in tree
	 */
	public TreeElement get(int i) {
		
		TreeElement cur = root;
		
		while(cur != null) {
			if( i < cur.getData() ) {
				cur = cur.getLeft();
			} else if( i > cur.getData() ) {
				cur = cur.getRight();
			} else {
				return cur;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Returns the size of the tree
	 * @return - size 
	 */
	public int size() {
		return size;
	}
	
	public int getRoot() {
		return root.getData();
	}
	
	
	public void inOrderTraversal() {
		System.out.print("\t");
		inOrder(root);
		System.out.println();
	}
	
	private void inOrder(TreeElement e) {
		if(e != null) {
			inOrder(e.getLeft());
			System.out.print(e.getData() + " ");
			inOrder(e.getRight());
		}
	}
}
