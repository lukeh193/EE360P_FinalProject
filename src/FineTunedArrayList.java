import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
 * add(Object o) 
 * add(int index, Object o)
 * 
 * remove(int index)
 * remove(Object o)
 * 
 * get(int index)
 * 
 * size()
 * 
 */

public class FineTunedArrayList<E>{
	private static int SIZE = 10;
	private ArrayElement[] array;
	private int size;
	Lock sizelock;
	
	public FineTunedArrayList(){
		array = new ArrayElement[SIZE];
		size = 0;
		this.sizelock = new ReentrantLock();
	}
	
	public int size(){
		sizelock.lock();
		int myCurrSize = size;
		sizelock.unlock();
		return myCurrSize;
	}
	
	public void add(ArrayElement<E> e){
		if(size == array.length){
			ArrayElement<E>[] temp = new ArrayElement[size * 2];
			for(int i = 0; i<array.length; i++){
				temp[i] = array[i];
			}
			array = temp;
		}
		array[size] = e;
		sizelock.lock();
		size++;
		sizelock.unlock();
	}
	
	public boolean add(int i, ArrayElement<E> e){
		if(i< 0 || i> size){
			return false;
		}
		if(size == array.length){
			int offset = 0;
			ArrayElement[] temp = new ArrayElement[size * 2];
			for(int j = 0; i<=array.length; j++){
				if(j == i){
					temp[j] = e;
					offset = 1;
				}
				temp[i + offset] = array[i];
			}
			array = temp;
			sizelock.lock();
			size++;
			sizelock.unlock();
			return true;
		}
		for(int j = size; j>i; j--){
			array[j] = array[j-1];
		}
		array[i] = e;
		sizelock.lock();
		size++;
		sizelock.unlock();
		return true;
	}	
	
	public boolean remove(ArrayElement e){
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
		sizelock.lock();
		size--;
		sizelock.unlock();
		return true;
		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayElement get(int i){
		return (ArrayElement) array[i];
	}
}
