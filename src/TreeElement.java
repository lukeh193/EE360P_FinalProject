
public class TreeElement {

	private int data;
	private TreeElement left;
	private TreeElement right;
	
	public TreeElement(int o) {
		data = o;
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
