package dsaa.lab03;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E>{

	private class Element{
		public Element(E e) {
			this.object = e;
			this.next = null;
			this.prev = null;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.next = next;
			this.prev = prev;
		}
		E object;
		Element next=null;
		Element prev=null;
	}

	Element head;
	Element tail;
	// can be realization with the field size or without
	int size;

	private class InnerIterator implements Iterator<E>{
		Element pos;

		public InnerIterator() {
			this.pos = head;
		}
		@Override
		public boolean hasNext() {
			return this.pos != null;
		}

		@Override
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			E value = this.pos.object;
			this.pos = this.pos.next;
			return value;
		}
	}


	private class InnerListIterator implements ListIterator<E> {
        Element p = head;

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return p != null && p.next != null;
        }

        @Override
        public boolean hasPrevious() {
            return p != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
			E nextValue = p.object;
            p = p.next;
            return nextValue;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
			E prevValue = p.object;
            p = p.prev;
            return prevValue;
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            if (p == null) throw new IllegalStateException();
            p.object = e;
        }
    }

	public TwoWayUnorderedListWithHeadAndTail() {
		// make a head and a tail
		head=null;
		tail=null;
		size = 0;
	}

	@Override
	public boolean add(E e) {
		Element newElement = new Element(e);
		if (head == null) {
			head = tail = newElement;
		} else {
			tail.next = newElement;
			newElement.prev = tail;
			tail = newElement;
		}
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) throw new NoSuchElementException();
		Element newElement = new Element(element);
		if (index == 0) {
			newElement.next = head;
			if (head != null) head.prev = newElement;
			head = newElement;
			if (size == 0) tail = newElement;
		} else {
			Element current = head;
			for (int i = 0; i < index - 1; i++) current = current.next;
			newElement.next = current.next;
			newElement.prev = current;
			if (current.next != null) current.next.prev = newElement;
			current.next = newElement;
			if (index == size) tail = newElement;
		}
		size++;
	}

	@Override
	public void clear() {
		head = tail = null;
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		Element current = head;
		while (current != null) {
			if (current.object.equals(element)) return true;
			current = current.next;
		}
		return false;
	}

	// @Override
	// public E get(int index) {
	// 	if (index < 0 || index >= size) throw new NoSuchElementException();
	// 	Element current = null;
	// 	if(size / 2 <= index) {
	// 		current = head;
	// 		for(int i = 0; i < index; i++) current = current.next;
	// 	}
	// 	if(size / 2 > index) {
	// 		current = tail;
	// 		for(int i = size-1; i > 0; i--) current = current.prev;
	// 	}
	// 	return current.object;
	// }

	@Override
	public E get(int index) {
		if (index < 0 || index >= size) throw new NoSuchElementException();
		Element current = head;
		for (int i = 0; i < index; i++) current = current.next;
		return current.object;
	}

	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= size) throw new NoSuchElementException();
		Element current = head;
		for (int i = 0; i < index; i++) current = current.next;
		E oldValue = current.object;
		current.object = element;
		return oldValue;
	}


	@Override
	public int indexOf(E element) {
		Element current = head;
		int index = 0;
		while (current != null) {
			if (current.object.equals(element)) return index;
			current = current.next;
			index++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) throw new NoSuchElementException();
		Element current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		if (current.prev != null) current.prev.next = current.next;
		if (current.next != null) current.next.prev = current.prev;
		if (current == head) head = current.next;
		if (current == tail) tail = current.prev;
		size--;
		return current.object;
	}

	@Override
	public boolean remove(E e) {
		int index = indexOf(e);
		if (index == -1) return false;
		remove(index);
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	public String toStringReverse() {
		ListIterator<E> iter=new InnerListIterator();
		while(iter.hasNext())
			iter.next();

		String retStr="";
		if (isEmpty()) return "";

        while (iter.hasPrevious()) {
			if (iter.hasPrevious()) {
                retStr += "\n";
            }
            retStr +=iter.previous().toString();
        }
		return retStr;
	}

	public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
		if (other.equals(this) || other.isEmpty())return;

		if (this.isEmpty()) {
			this.head = other.head;
			this.tail = other.tail;
		} else {
			this.tail.next = other.head;
			other.head.prev = this.tail;
			this.tail = other.tail;
		}

		this.size += other.size;

		other.head = null;
		other.tail = null;
	}
}

