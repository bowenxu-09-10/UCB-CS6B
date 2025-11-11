package deque;

import java.util.Iterator;
import java.util.Objects;

public class LinkedListDeque<T> implements Deque<T> {

    // Return the size of current LLDeque.
    private int size;
    private StuffNode sentinel;
    /**
     * Node that store some magical information.
     */
    private class StuffNode {
        public T item;
        public StuffNode next;
        public StuffNode prev;

        public StuffNode(T item, StuffNode node) {
            this.item = item;
            this.next = node;
        }
    }

    public LinkedListDeque() {
        sentinel = new StuffNode(null, null);
        size = 0;
    }

    public LinkedListDeque(T item) {
        sentinel = new StuffNode(null, null);
        sentinel.next = new StuffNode(item, sentinel);
    }

    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item) {};

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {};

    /**
     * Return true if deque is empty.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return this.size;
    }

    /**
     * Prints the items in the duque from first to last, separated by a space.
     */
    public void printDeque() {

    }

    /**
     * Removes and returns the item at the front of the deque.
     */
    public T removeFirst() {
        return null;
    }

    /**
     * Removes and returns the item at the back of the deque.
     */
    public T removeLast() {
        return null;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     */
    public T get(int index) {
        return null;
    }

    /**
     * Return an iterator.
     */
    public Iterator<T> iterator() {
        return null;
    }

    /**
     * Returns whether the parameter o is equal to the Deque.
     */
    public boolean equals(Objects o) {
        return false;
    }
}
