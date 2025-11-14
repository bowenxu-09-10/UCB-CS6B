package deque;

import java.util.Iterator;
import java.math.*;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst = 0;
    private int nextLast = 1;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    /**
     * Helper func of copying array.
     */
    private void copyArray(T[] newAlst) {
        // Copy the first n number util the end of the array.
        if (nextFirst < nextLast) {
            System.arraycopy(items, (nextFirst + 1) % items.length, newAlst, 0,
                    nextLast - nextFirst + 1);
            items = newAlst;
            return;
        }
        System.arraycopy(items, (nextFirst + 1) % items.length, newAlst, 0,
                (items.length - nextFirst - 1));
        // Copy the rest items at the front of the array.
        System.arraycopy(items, 0, newAlst, items.length - nextFirst - 1,
                (nextLast + items.length) % items.length);
        items = newAlst;
    }

    /**
     * Resize the size of the array.
     */
    private void resize() {
        int factor = 2;
        if (size > items.length / 2 && size > 6) {
            T[] newAlst = (T[]) new Object[items.length * factor];
            copyArray(newAlst);
            nextFirst = items.length - 1;
            nextLast = size;
        } else if (size < items.length / 4 && items.length > 8) {
            T[] newAlst = (T[]) new Object[items.length / factor];
            copyArray(newAlst);
            nextFirst = items.length - 1;
            nextLast = size;
        }
    }

    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item) {
        resize();
        items[nextFirst] = item;
        nextFirst = (items.length + nextFirst - 1) % items.length;
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        resize();
        items[nextLast] = item;
        nextLast = (items.length + nextLast + 1) % items.length;
        size++;
    }

    /**
     * Return true if deque is empty.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        for (int i = 0; i < size - 1; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println(get(size - 1));
    }

    /**
     * Removes and returns the item at the front of the deque.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        resize();
        T item = items[(nextFirst + 1) % items.length];
        items[(nextFirst + 1) % items.length] = null;
        nextFirst = (nextFirst + 1) % items.length;
        size--;
        return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        resize();
        T item = items[(nextLast + items.length -1) % items.length];
        items[(nextLast + items.length -1) % items.length] = null;
        nextLast = (nextLast + items.length -1) % items.length;
        size--;
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     */
    public T get(int index) {
        return items[(index + nextFirst + 1) % items.length];
    }

    /**
     * Return an iterator.
     */
    public Iterator<T> iterator() {
        return new ADequeIterator();
    }

    /**
     * Iterator
     */
    private class ADequeIterator implements Iterator<T> {
        private int pos;
        public ADequeIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T item = get(pos);
            pos++;
            return item;
        }
    }

    /**
     * Returns whether the parameter o is equal to the Deque.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass() || o == null) {
            return false;
        }
        ArrayDeque<T> other = (ArrayDeque<T>) o;
        if (this.size != other.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
}
