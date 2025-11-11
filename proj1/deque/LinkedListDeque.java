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
            this.prev = node;
        }
    }

    public LinkedListDeque() {
        sentinel = new StuffNode(null, null);
        size = 0;
    }

    public LinkedListDeque(T item) {
        sentinel = new StuffNode(null, null);
        sentinel.next = new StuffNode(item, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /**
     * If the LLDeque is empty, and need to initialize the sentinel.
     */
    private void initializeSentinel(T item) {
        if (this.size == 0) {
            sentinel.next = new StuffNode(item, sentinel);
            sentinel.prev = sentinel.next;
            size++;
        }
    }

    /**
     * Adds an item of type T to the front of the deque.
     */
    @Override
    public void addFirst(T item) {
        if (this.size == 0) {
            initializeSentinel(item);
            return;
        }
        StuffNode tmp = sentinel.next;
        sentinel.next = new StuffNode(item, tmp);
        tmp.prev = sentinel.next;
        sentinel.next.prev = sentinel;
        size += 1;
        tmp = null;
    };

    /**
     * Adds an item of type T to the back of the deque.
     */
    @Override
    public void addLast(T item) {
        if (this.size == 0) {
            initializeSentinel(item);
            return;
        }
        StuffNode lstNode = sentinel.prev;
        lstNode.next = new StuffNode(item, sentinel);
        lstNode.next.prev = lstNode;
        lstNode.next.next = sentinel;
        sentinel.prev = lstNode.next;
        size += 1;
        lstNode = null;
    }

    /**
     * Return true if deque is empty.
     */
    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Prints the items in the duque from first to last, separated by a space.
     */
    @Override
    public void printDeque() {
        for (int i = 0; i < size - 1; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println(get(size - 1));
    }

    /**
     * Removes and returns the item at the front of the deque.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            System.out.println("There is no content in LLDeque.");
            return null;
        }
        T content = this.get(0);
        sentinel.next = sentinel.next.next;
        sentinel.next.prev.prev = null;
        sentinel.next.prev.next = null;
        sentinel.next.prev = sentinel;
        size--;
        return content;
    }

    /**
     * Removes and returns the item at the back of the deque.
     */
    @Override
    public T removeLast() {
        // Todo
        return null;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     */
    @Override
    public T get(int index) {
        StuffNode temp = this.sentinel;
        for (int i = 0; i <= index; i++) {
            temp = temp.next;
        }
        return temp.item;
    }

    /**
     * Return an iterator.
     */
    @Override
    public Iterator<T> iterator() {
        // Todo
        return null;
    }

    /**
     * Returns whether the parameter o is equal to the Deque.
     */
    @Override
    public boolean equals(Objects o) {
        // Todo
        return false;
    }

    public static void main(String[] args) {
//        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
//        lld1.addLast("I");
//        lld1.addLast("love");
//        lld1.addLast("Toby");
    }
}
