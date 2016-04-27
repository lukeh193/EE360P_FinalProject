
public class TreeTester {

	public static void main(String[] args) {
		
		FineTunedTree tree = new FineTunedTree();
		
		// Testing Insert
		System.out.println("Testing insert (1 - 8) :");
		tree.insert(5);
		tree.insert(3);
		tree.insert(2);
		tree.insert(1);
		tree.insert(8);
		tree.insert(6);
		tree.insert(7);
		tree.insert(4);
		tree.inOrderTraversal();
		
		/*
		// Test removing leaf
		System.out.println("Testing removing leaf (7) :");
		tree.remove(7);
		System.out.println("Root = " + tree.getRoot());
		tree.inOrderTraversal();
		
		// Test removing node with one child
		System.out.println("Testing removing node with one child (8) :");
		tree.remove(8);
		System.out.println("Root = " + tree.getRoot());
		tree.inOrderTraversal();
		
		// Test removing node with two children
		System.out.println("Testing removing node with two children (3) :");
		tree.remove(3);
		System.out.println("Root = " + tree.getRoot());
		tree.inOrderTraversal();
		
		// Test removing root node
		System.out.println("Testing removing root node (5) :");
		tree.remove(5);
		System.out.println("Root = " + tree.getRoot());
		tree.inOrderTraversal();
		*/
	}

}
