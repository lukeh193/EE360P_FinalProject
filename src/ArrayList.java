
public class ArrayList<E> {
	private static int SIZE = 10;
	private Object[] array;
	private int size;
	
	public ArrayList(){
		array = new Object[SIZE];
		size = 0;
	}
	
	public int size(){
		return size;
	}
	
	public void add(E e){
		if(size == array.length){
			Object[] temp = new Object[size * 2];
			for(int i = 0; i<array.length; i++){
				temp[i] = array[i];
			}
			array = temp;
		}
		array[size] = e;
		size++;
	}
	
	public boolean add(int i, E e){
		if(i< 0 || i> size){
			return false;
		}
		if(size == array.length){
			int offset = 0;
			Object[] temp = new Object[size * 2];
			for(int j = 0; i<=array.length; j++){
				if(j == i){
					temp[j] = e;
					offset = 1;
				}
				temp[i + offset] = array[i];
			}
			array = temp;
			size++;
			return true;
		}
		for(int j = size; j>i; j--){
			array[j] = array[j-1];
		}
		array[i] = e;
		size++;
		return true;
	}	
	
	public boolean remove(E e){
		boolean removed = false;
		for(int i  = 0; i<size; i++){
			if(removed){
				array[i-1] = array[i];
			}
			if(array[i].equals(e)){
				removed = true;
			}
			
		}
		if(removed)
			size--;
		return removed;
	}
	
	public boolean remove(int i){
		boolean removed = false;
		if(i<0 || i>= size)
			return false;
		
		for(int j  = 0; j<size; j++){
			if(removed){
				array[j-1] = array[j];
			}
			if(i==j){
				removed = true;
			}
			
		}
		size--;
		return true;
		
	}
	
	@SuppressWarnings("unchecked")
	public E get(int i){
		return (E) array[i];
	}
}
