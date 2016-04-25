
public class TreeElement {

	private int data;
	private TreeElement parent;
	private TreeElement left;
	private TreeElement right;
	
	public TreeElement(int o) {
		data = o;
		parent = null;
		left = null;
		right = null;
	}
	
	/* Getters */
	public int getData() {
		return data;
	}
	
	public TreeElement getLeft() {
		return left;
	}
	
	public TreeElement getRight() {
		return right;
	}
	
	public TreeElement getParent() {
		return parent;
	}
		
	
	/* Setters */
	public void setLeft(TreeElement e) {
		left= e;
	}
	
	public void setRight(TreeElement e) {
		right = e;
	}
	
	public void setData(int i) {
		data = i;
	}
	
	public void setParent(TreeElement e) {
		parent = e;
	}
	
	
	/* Other methods */
	public int numChildren() {
		int num = 0;
		if(left != null) 
			num++;
		if(right != null) 
			num++;
		
		return num;
	}
}
