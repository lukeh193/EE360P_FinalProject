public class SkipListEntry {

	private Integer value;
	private SkipListEntry down;
	private SkipListEntry right;
	private SkipListEntry left;
	
	
	public SkipListEntry(Integer value){
		this.value = value;
		down = null;
		right = null;
		left = null;
	}
	
	public SkipListEntry getDown() {
		return down;
	}
	public void setDown(SkipListEntry down) {
		this.down = down;
	}
	public SkipListEntry getLeft() {
		return left;
	}
	public void setLeft(SkipListEntry left) {
		this.left = left;
	}
	
	public SkipListEntry getRight() {
		return right;
	}
	public void setRight(SkipListEntry right) {
		this.right = right;
	}
	public Integer getValue() {
		return value;
	}
	
	public void setAllToNull() {
		this.left = null;
		this.right = null;
		this.down = null;
	}

}
