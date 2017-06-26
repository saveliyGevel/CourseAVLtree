package com.company;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 25.06.17.
 */
public class AVLtree<K extends Comparable<K>, V> implements Map<K, V> {

    private class Node {

        private K key;
        private V value;
        private int h;
        private Node left, right, parent;

        public Node(K key, V value, Node parent) {

            this.value = value;
            this.key = key;
            this.h = 1;
            this.left = this.right = null;
            this.parent = parent;
        }

        public int height() {
            if (left == null && right == null) return 0;
            else if (left == null) return right.h;
            else if (right == null) return left.h;
            else return Math.max(left.h, right.h);
        }

        public int balance() {
            if (left == null && right == null) return 0;
            else if (left == null) return right.h;
            else if (right == null) return -left.h;
            else return right.h - left.h;
        }
    }

    private Node root = null;
    private int size = 0;


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {

        return containsKey((K) key, root);
    }

    private boolean containsKey(K key, Node node) {
        if (key.compareTo(node.key) > 0) {
            return containsKey(node.right);
        } else if (key.compareTo(node.key) < 0) {
            return containsKey(node.left);
        } else {
            return true;
        }
    }


    @Override
    public boolean containsValue(Object value) {
        return containsValue((V) value, root);
    }

    private boolean containsValue(V value, Node node) {
        if (value == node.value) {
            return true;
        } else {
            containsValue(value, node.left);
            containsValue(value, node.right);
        }
        return false;
    }

    @Override
    public V get(Object key) {
        getValue(root, (K) key);
        return null;
    }

    private V getValue(Node node, K key) {
        int result = key.compareTo(node.key);
        if (result > 0) {
            return getValue(node.right, key);
        } else if (result < 0) {
            return getValue(node.right, key);
        } else {
            return node.value;
        }
    }

    @Override
    public V put(K key, V value) {
        add(root, key, value, null);
        return null;
    }

    private Node add(Node node, K key, V value, Node parent) {

        if (node == null) {
            Node newnode = new Node(key, value, parent);
            size++;
            return newnode;
        }

        int result = key.compareTo(node.key);
        if (result > 0) {
            node.right = add(node.right, key, value, node);
            node.h = node.height() + 1;
        } else if (result < 0) {
            node.left = add(node.left, key, value, node);
            node.h = node.height() + 1;
        } else {
            node.value = value;
        }

        rebalance(node);
        size++;

        return node;
    }

    @Override
    public V remove(Object key) {
        delete((K) key, root);
        return null;
    }

    private Node delete(K key, Node node) {

        if (key.compareTo(node.key) < 0) {
            delete(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            // The key to be deleted is in the right sub-tree
            delete(key, node.right);
        } else {
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.left != null && node.right == null) {
                node = node.left;
            } else if (node.left == null && node.right != null) {
                node = node.right;
            } else {
                Node child = findMin(node);
                node = child;
                remove(child.key);
                rebalance(node);
            }
        }

        if (root == null) {
            return root;
        }
        return null;
    }

    private void reHeight(Node node) {
        if (node != null) {
            node.h = 1 + Math.max(node.left.height(), node.right.height());
        }
    }

    private void rebalance(Node node) {
        int balance = node.balance();

        if (balance == -2) {
            if (node.left.left.height() >= node.left.right.height())
                node = rotateRight(node);
            else
                node = rotateLeftThenRight(node);

        } else if (balance == 2) {
            if (node.right.right.height() >= node.right.left.height())
                node = rotateLeft(node);
            else
                node = rotateRightThenLeft(node);
        }

        if (node.parent != null) {
            rebalance(node.parent);
        } else {
            root = node;
        }
    }

    private Node rotateLeft(Node a) {

        Node b = a.right;
        b.parent = a.parent;

        a.right = b.left;

        if (a.right != null)
            a.right.parent = a;

        b.left = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        return b;
    }

    private Node rotateRight(Node a) {

        Node b = a.left;
        b.parent = a.parent;

        a.left = b.right;

        if (a.left != null)
            a.left.parent = a;

        b.right = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        return b;
    }

    private Node rotateLeftThenRight(Node n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }

    private Node rotateRightThenLeft(Node n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }

    private Node findMax(Node node) {
        if (node.right == null) return node;
        return findMax(node.right);
    }

    public void findMin() {
        findMin(root);
    }

    public void findMax() {
        findMax(root);
    }

    private Node findMin(Node node) {
        if (node.left == null) return node;
        return findMin(node.left);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        clear(root);
    }

    private Node clear(Node node) {
        node = null;
        if (node.right != null) clear(node.left);
        if (node.left != null) clear(node.right);
        return null;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
