package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    int size = 0;

    /** Root of the BST. */
    private BSTNode root;

    /** Represents one node in the Binary Search Tree that stores the key-value
     *  pairs in the dictionary. */
    public class BSTNode {

        /** Stores the key of the key-value pair of this node in BSTMap. */
        K key;
        /** Stores the value of the key-value pair of this node in BSTMap. */
        V val;
        /** Stores the left node of the BSTNode. */
        BSTMap.BSTNode left;
        /** Stores the right node of the BSTNode. */
        BSTMap.BSTNode right;

        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        BSTNode(K k, V v) {
            key = k;
            val = v;
        }

        /** Return the BSTNode in this BSTMap of key-value pairs whose key is
         * equal to KEY, or null if no such key exists. */
        BSTNode get(BSTNode node, K k) {
            if (k == null)    return null;
            if (node == null) return null;
            int cmp = k.compareTo(node.key);
            if      (cmp > 0) return get(node.right, k);
            else if (cmp < 0) return get(node.left, k);
            else              return this;
        }
    }


    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }


    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (root == null) return false;
        return get(key) != null;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        BSTNode target = root.get(root, key);
        if (target == null) {
            return null;
        }
        return target.val;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        put(root, key, value);
        size++;
    }

    private void put(BSTNode node, K key, V value) {
        if (root == null) {
            root = new BSTNode(key, value);
        } else {
            int cmp = key.compareTo(node.key);
            if (cmp > 0) {
                if (node.right == null) {
                    BSTNode newNode = new BSTNode(key, value);
                    node.right = newNode;
                } else {
                    put(node.right, key, value);
                }
            } else if (cmp < 0) {
                if (node.left == null) {
                    BSTNode newNode = new BSTNode(key, value);
                    node.left = newNode;
                } else {
                    put(node.left, key, value);
                }
            } else {
                node.val = value;
            }
        }
    }

    /** Prints out BSTMap in order of increasing Key. */
    public void printInOrder() {
        // Todo
        throw new UnsupportedOperationException();
    }


    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
