import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class NestedLists {
	
	//List: {1, 2, {3, 4, {{5, 6}, 7, 8}, 9}, 10}
	public static void main(String[] args) {
		List<Object> list = new ArrayList<>();
		List<Object> innerlist = new ArrayList<>();
		list.add(1);
		list.add(2);
		innerlist.add(3);
		innerlist.add(4);
		List<Object> anotherlist = new ArrayList<>();
		anotherlist.add(5);
		anotherlist.add(6);
		List<Object> otherlist = new ArrayList<>();
		otherlist.add(anotherlist);
		otherlist.add(7);
		otherlist.add(8);
		innerlist.add(otherlist);
		innerlist.add(9);
		list.add(innerlist);
		list.add(10);
		
		Iterator<Object> iterator = iterator(list);
		while(iterator.hasNext()) {
			System.out.print(iterator.next() + " ");
		}
		System.out.println();
	}
	
	public static Iterator<Object> iterator(List<Object> list) {
		return new EmbeddedListIterator(list);
	}
}

class EmbeddedListIterator implements Iterator<Object> {
	List<Object> list;
	Iterator<Object> iterator;
	
	int currentPos = 0;
	
	public EmbeddedListIterator(List<Object> list) {
		this.list = list;
	}	

	public boolean hasNext() {
		return currentPos < list.size() || iterator != null;
	}
	
	public Object next() {
		if(!hasNext()) 
			throw new NoSuchElementException();
		
		if(iterator != null) {
			if(iterator.hasNext()) {
				Object item = iterator.next();
				if(!iterator.hasNext())
					iterator = null;
				return item;
			}
		}
		Object item = list.get(currentPos++);
		if(item instanceof List){
			iterator = new EmbeddedListIterator((List<Object>) item);
			item = iterator.next();
			if(!iterator.hasNext())
				iterator = null;
			return item;
		}
		return item;
	}
}
