package dsaa.lab04;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E extends Comparable<E>> implements IList<E> {

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
		// add element e after this
		public void addAfter(Element elem) {
			elem.prev = this;
			elem.next = this.next;
			this.next.prev = elem;
			this.next = elem;
		}
		// assert it is NOT a sentinel
		public void remove() {
			if(this == sentinel) throw new IllegalStateException();
			this.next.prev = this.prev;
			this.prev.next = this.next;
		}
		E object;
		Element next=null;
		Element prev=null;
	}


	Element sentinel;
	int size;

	private class InnerIterator implements Iterator<E>{
		Element pos;

		public InnerIterator() {
			this.pos = sentinel.next;
		}
		@Override
		public boolean hasNext() {
			return pos != sentinel;
		}

		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			E value = this.pos.object;
			this.pos = this.pos.next;
			return value;
		}
	}

	private class InnerListIterator implements ListIterator<E>{
		Element pos;

		public InnerListIterator() {
			this.pos = sentinel.next;
		}
		@Override
		public boolean hasNext() {
			return this.pos != sentinel;
		}

		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			E value = this.pos.object;
			this.pos = this.pos.next;
			return value;
		}
		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean hasPrevious() {
			return this.pos.prev != sentinel;
		}
		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}
		@Override
		public E previous() {
			if(!hasPrevious()) throw new NoSuchElementException();
			this.pos = this.pos.prev;
			return this.pos.object;
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
		public void set(E arg0) {
			throw new UnsupportedOperationException();
		}
	}
	public TwoWayCycledOrderedListWithSentinel() {
		this.sentinel = new Element(null);
		this.sentinel.next = sentinel;
		this.sentinel.prev = sentinel;
	}

	//@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {
        Element newElement = new Element(e);
        Element current = sentinel.next;
        while (current != sentinel && current.object.compareTo(e) <= 0) {
            current = current.next;
        }
        current.prev.addAfter(newElement);
        size++;
        return true;
	}

	private Element getElement(int index) {
		if (index < 0 || index >= size) throw new NoSuchElementException();

		int i = 0;
		Element current = sentinel.next;
		if(index < size / 2) {
			while(current != sentinel && i < index) {
				current = current.next;
				i++;
			}
		} else {
			while(current != sentinel && i < size - index - 1) {
				current = current.prev;
				i++;
			}
		}
		return current;
	}

	private Element getElement(E obj) {
		Element current = sentinel.next;
		while(current != sentinel) {
			if(current.object.equals(obj)) return current;
			current = current.next;
		}
		return sentinel;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clear() {
		this.sentinel.next = sentinel;
		this.sentinel.prev = sentinel;
		this.size = 0;
	}

	@Override
	public boolean contains(E element) {
		return !this.getElement(element).equals(sentinel);
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size) throw new NoSuchElementException();
		Element el = this.getElement(index);

		if(el.equals(sentinel)) throw new NoSuchElementException();
		return el.object;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(E element) {
		int i = 0;
		Element current = sentinel.next;
		while(current != sentinel) {
			if(current.object.equals(element)) return i;
			current = current.next;
			i++;
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
		return new InnerListIterator();
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) throw new NoSuchElementException();

		Element el = getElement(index);
		E removedObject = el.object;

		el.prev.next = el.next;
		el.next.prev = el.prev;
		size--;

		return removedObject;
	}

	@Override
	public boolean remove(E e) {
		Element el = getElement(e);
		if (el == sentinel) return false;

		el.prev.next = el.next;
		el.next.prev = el.prev;
		size--;
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	//@SuppressWarnings("unchecked")
	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		if (this == other || other.isEmpty()) return;

		Element otherEl = other.sentinel;
		Element thisEl = sentinel;
		while (otherEl.object != sentinel && thisEl.next != sentinel) {
			if(thisEl.object.compareTo(otherEl.object) > 0) {
				thisEl.addAfter(otherEl);
				otherEl = otherEl.next;
			}
			thisEl = thisEl.next;

		}
		other.clear();
	}

	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAll(E e) {
		Element current = sentinel.next;
		while (current != sentinel) {
			if (current.object.equals(e)) {
				Element toRemove = current;
				current = current.next;

				toRemove.prev.next = toRemove.next;
				toRemove.next.prev = toRemove.prev;
				size--;
			} else {
				current = current.next;
			}
		}
	}

}

