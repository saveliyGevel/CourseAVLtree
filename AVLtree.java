package com.company;

import javax.xml.soap.Node;
import java.util.*;
import java.util.ArrayList;

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
            else return 1 + Math.max(left.h, right.h);
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
        if (size == 0) return true;
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        K Key = (K) key;
        Node closest = find(Key);
        if (closest.key == key) return true;
        else return false;
    }

    private Node find(K key) {
        if (root == null) return null;
        return find(root, key);
    }

    private Node find(Node start, K key) {
        int comparison = key.compareTo(start.key);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, key);
        } else {
            if (start.right == null) return start;
            return find(start.right, key);
        }
    }


    @Override
    public boolean containsValue(Object value) {
        V Value = (V) value;
        Node closest = containsValue(root, Value);
        if(closest != null){
            return true;
        } else {
            return false;
        }
    }

    private Node containsValue(Node node, V value){
        Queue<Node> queue = new LinkedList<Node>();
        do{
            queue.add(node);
            if(node.value == value) return node;
            if(node.left != null) queue.add(node.left);
            if(node.right != null) queue.add(node.right);
            if(!queue.isEmpty()) node = queue.poll();
        } while(!queue.isEmpty());
        return null;
    }

    @Override
    public V get(Object key) {
        K Key = (K) key;
        if (Key == null) throw new NullPointerException();
        if (root == null) throw new NullPointerException();
        if (containsKey(key)) {
            return getValue(root, Key);
        } else throw new NullPointerException();
    }

    private V getValue(Node node, K key) {
        int result = key.compareTo(node.key);
        if (result > 0) {
            return getValue(node.right, key);
        } else if (result < 0) {
            return getValue(node.left, key);
        } else {
            return node.value;
        }
    }

    @Override
    public V put(K key, V value) {
        if (key == null) throw new NullPointerException("Wrong key");
        if (root == null) {
            root = new Node(key, value, null);
            size++;
            return value;
        }
        put(root, key, value, null);
        return value;
    }

    private Node put(Node node, K key, V value, Node parent) {

        if (node == null) {
            if (key.compareTo(parent.key) > 0) {
                parent.right = new Node(key, value, parent);
            } else if (key.compareTo(parent.key) < 0) {
                parent.left = new Node(key, value, parent);
            }
            size++;
            rebalance(parent);
            return node;
        } else {
            if (key.compareTo(node.key) > 0) {
                return put(node.right, key, value, node);
            } else if (key.compareTo(node.key) < 0) {
                return put(node.left, key, value, node);
            } else if (key.compareTo(node.key) == 0) {
                node.value = value;
                return node;
            }
        }
        return node;
    }

    @Override
    public V remove(Object key) {
        if (key == null) throw new NullPointerException("Wron key");
        if (root == null) throw new NullPointerException();
        if (containsKey(key)) {
            delete((K) key, root);
            return (V) key;
        } else {
            throw new NullPointerException();
        }
    }

    private Node delete(K key, Node node) {
        if (key.compareTo(node.key) > 0) {
            delete(key, node.right);
        } else if (key.compareTo(node.key) < 0) {
            delete(key, node.left);
        } else {
            if (node.left == null && node.right == null) {
                if (node.key.compareTo(node.parent.key) > 0) {
                    node.parent.right = null;
                } else if (node.key.compareTo(node.parent.key) < 0) {
                    node.parent.left = null;
                }

            } else if (node.left != null && node.right == null) {
                if (node.key.compareTo(node.parent.key) > 0) {
                    node.parent.right = node.left;
                } else if (node.key.compareTo(node.parent.key) < 0) {
                    node.parent.left = node.left;
                }
            } else if (node.left == null && node.right != null) {
                if (node.key.compareTo(node.parent.key) > 0) {
                    node.parent.right = node.right;
                } else if (node.key.compareTo(node.parent.key) < 0) {
                    node.parent.left = node.right;
                }
            } else {
                Node child = findMin(node.right);
                node.key = child.key;
                node.value = child.value;
                child = null;
            }
            size--;
            rebalance(node);
        }
        return null;
    }

    private void rebalance(Node node) {
        node.height();
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

    private Node findMin(Node node) {
        if (node.left == null) return node;
        return findMin(node.left);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
    }


    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        int size = size();
        return keySet(root, size);
    }

    private Set<K> keySet(Node node, int size){
        List<K> keysList = new ArrayList<K>();
        do {
            keysList.add(node.key);
            if (node.left != null) {
                size--;
                keysList.add(node.left.key);
            }
            if(node.right != null){
                size--;
                keysList.add(node.right.key);
            }
        }while(size != 0);
        return (Set<K>) keysList;
    }


    @Override
    public Collection<V> values() {
        int size = size();
        return values(root, size);
    }

    private Collection<V> values(Node node,int size){
        List<V> keysList = new ArrayList<V>();
        do {
            keysList.add(node.value);
            if (node.left != null) {
                size--;
                keysList.add(node.left.value);
            }
            if(node.right != null){
                size--;
                keysList.add(node.right.value);
            }
        }while(size != 0);
        return (Collection<V>) keysList;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

}



