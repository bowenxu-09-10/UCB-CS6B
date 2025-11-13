package deque;

import java.util.Iterator;
import java.util.Objects;
import java.math.*;

public class ArrayDeque<T> implements Deque<T>{
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
    public void resize() {
        int factor = 2;
        if (size > items.length / 2 && size > 7) {
            T[] newAlst = (T[]) new Object[items.length * factor];
            copyArray(newAlst);
            nextFirst = items.length - 1;
            nextLast = size;
        } else if (size < items.length / 4 && size > 7) {
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
