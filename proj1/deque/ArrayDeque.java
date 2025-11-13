package deque;

import java.util.Iterator;
import java.util.Objects;
import java.math.*;

public class ArrayDeque<T> implements Deque<T>{
    private T[] items;
    private int size;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;

    }

    /**
     * Helper function, move the rest items to the right.
     */
    private void moveItems() {
        for (int i = size; i > 0; i--) {
            items[i] = items[i-1];
        }
    }

    /**
     * Resize the size of the array.
     */
    public void resize() {
        float factor = (float) 1.5;
        if (size > items.length / 2) {
            T[] newAlst = (T[]) new Object[Math.round(factor * items.length)];
            System.arraycopy(items, 0,newAlst,0,size);
            items = newAlst;
        } else if (size < items.length / 2 && size > 4) {
            T[] newAlst = (T[]) new Object[Math.round(items.length / factor)];
            System.arraycopy(items, 0,newAlst,0,size);
            items = newAlst;
        }
    }

    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item) {
        resize();
        moveItems();
        items[0] = item;
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        // Todo
        return;
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
     * Prints the items in the duque from first to last, separated by a space.
     */
    public void printDeque() {
        // Todo
        return;
    }

    /**
     * Removes and returns the item at the front of the deque.
     */
    public T removeFirst() {
        // Todo
        return null;
    }

    /**
     * Removes and returns the item at the back of the deque.
     */
    public T removeLast() {
        // Todo
        return null;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     */
    public T get(int index) {
        // Todo
        return null;
    }

    /**
     * Return an iterator.
     */
    public Iterator<T> iterator() {
        // Todo
        return null;
    }

    /**
     * Returns whether the parameter o is equal to the Deque.
     */
    public boolean equals(Objects o) {
        // Todo
        return true;
    }
}
