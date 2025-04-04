package dsaa.lab02;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E> {
    private class Element {
        E object;
        Element next = null;

        public Element(E e) {
            this.object = e;
        }
    }

    private Element sentinel;

	public OneWayLinkedList() {
        this.sentinel = new Element(null);
    }

    private class InnerIterator implements Iterator<E> {
        Element current;

        public InnerIterator() {
            this.current = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E data = current.object;
            current = current.next;
            return data;
        }
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
    public boolean add(E e) {
        Element currentEl = sentinel;
        while (currentEl.next != null) {
            currentEl = currentEl.next;
        }
        currentEl.next = new Element(e);
        return true;
    }

    @Override
    public void add(int index, E el) {
        if (index > size() || index < 0) throw new NoSuchElementException();
        Element newEl = new Element(el);
        Element prevEl = sentinel;
        for (int i = 0; i < index; i++) {
            prevEl = prevEl.next;
        }
        newEl.next = prevEl.next;
        prevEl.next = newEl;
    }

    @Override
    public void clear() {
        sentinel.next = null;
    }

    @Override
    public boolean contains(E element) {
        if (element == null) return false;
        Element currEl = sentinel.next;
        while (currEl != null) {
            if (currEl.object.equals(element)) return true;
            currEl = currEl.next;
        }
        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size()) throw new NoSuchElementException();
        Element currEl = sentinel.next;
        for (int i = 0; i < index; i++) {
            currEl = currEl.next;
        }
        return currEl.object;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size()) throw new NoSuchElementException();
        Element currEl = sentinel.next;
        for (int i = 0; i < index; i++) {
            currEl = currEl.next;
        }
        E oldObject = currEl.object;
        currEl.object = element;
        return oldObject;
    }

    @Override
    public int indexOf(E element) {
        Element currEl = sentinel.next;
        int index = 0;
        while (currEl != null) {
            if (currEl.object.equals(element)) return index;
            currEl = currEl.next;
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == null;
    }

    @Override
    public E remove(int index) {
        if (sentinel.next == null || index < 0 || index >= size()) throw new NoSuchElementException();
        Element prevEl = sentinel;
        for (int i = 0; i < index; i++) {
            prevEl = prevEl.next;
        }
        Element toRemove = prevEl.next;
        prevEl.next = toRemove.next;
        return toRemove.object;
    }

    @Override
	public boolean remove(E e) {
		Element prevEl = sentinel;
		while (prevEl.next != null) {
			if (prevEl.next.object.equals(e)) {
				prevEl.next = prevEl.next.next;
				return true;
			}
			prevEl = prevEl.next;
		}
		return false;
	}

    @Override
    public int size() {
        Element currEl = sentinel.next;
        int count = 0;
        while (currEl != null) {
            count++;
            currEl = currEl.next;
        }
        return count;
    }
}