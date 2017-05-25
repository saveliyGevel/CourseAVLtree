package com.company;


import java.util.LinkedList;
import java.util.Queue;

public class AVLtree<Key extends Comparable<Key>,Value> {


    public class AVLNode {

        private int h;
        private int balance;
        Key key;
        Value value;
        private AVLNode left, right, parent;


        public AVLNode(Key key, Value value, AVLNode parent) {

            this.value = value;
            this.left = this.right = null;
            this.parent = parent;
            this.h = 1;
            this.balance = 0;

        }
    }

    private AVLNode root = null;

        private int height(AVLNode left, AVLNode right) {
            if (left == null && right == null) return 0;
            else if (left == null) return right.h;
            else if (right == null) return right.h;
            else return Math.max(left.h, right.h);
        }

        private int balance(AVLNode left, AVLNode right) {
            if (left == null && right == null) return 0;
            else if (left == null) return right.h;
            else if (right == null) return left.h;
            else return right.h - left.h;
        }


        private AVLNode leftRotation(AVLNode node) {

            if (node.right.right == null && node.right.left != null) {
                node.right = rightRotation(node.right);
                node = leftRotation(node);
            } else {
                AVLNode subsidiaryNode = node.right;
                node.right = subsidiaryNode.left;
                subsidiaryNode.left = node;
            }
            return node;
        }

        private AVLNode rightRotation(AVLNode node) {

            if (node.left.right == null && node.left.left != null) {
                node.left = leftRotation(node.left);
                node = rightRotation(node);
            } else {
                AVLNode helpNode = node.left;
                node.left = helpNode.right;
                helpNode.right = node;
            }
            return node;
        }


        private AVLNode add(AVLNode node, Key key, Value value, AVLNode parent) {

            if (node == null) {
                AVLNode newnode = new AVLNode(key, value, parent);
                return newnode;
            }

            int result = key.compareTo(node.key);
            if (result > 0) {
                node.right = add(node.right, key, value, node);
                node.h = height(node.left, node.right) + 1;
            } else if (result < 0) {
                node.left = add(node.left, key, value, node);
                node.h = height(node.left, node.right) + 1;
            } else {
                node.value = value;
            }

            node.balance = balance(node.left, node.right);
            while (node.balance != 0){
                if (node.balance == 2) {
                    node = rightRotation(node);
                } else if (node.balance == -2) {
                    node = leftRotation(node);
                }
                node = node.parent;
                node.balance = balance(node.left, node.right);
            }
            return node;
        }

        public void add(Key key, Value value){

            root = add(root, key, value, null);
        }


        private AVLNode min(AVLNode node){

            if(node.left == null && node.right == null) return node;
            return min(node.left);
        }


        private AVLNode max(AVLNode node){

            if(node.right == null) return node;
            return max(node.right);
        }


        private AVLNode delete(AVLNode node, Key key) {

            if (node == null) return null;
            int result = key.compareTo(node.key);
            if (result < 0) {
                node.left = delete(node.left, key);
            } else if (result > 0) {
                node.right = delete(node.right, key);
            } else {
                if(node.right == null && node.left == null){
                    node = null;
                } else if(node.right == null && node.left != null){
                    node = node.left;
                    node.left = null;
                } else  if(node.left == null && node.right != null){
                    node = node.right;
                    node.right = null;
                } else {
                    AVLNode subsidiaryNode = min(node.right);
                    if(subsidiaryNode.right != null){
                        subsidiaryNode = subsidiaryNode.right;
                    }
                    node.key = subsidiaryNode.key;
                    node.value = subsidiaryNode.value;
                    delete(subsidiaryNode, subsidiaryNode.key);
                }
            }
            if(node != null){
                node.h = height(node.left, node.right);
                node.balance = balance(node.left, node.right);
                while(Math.abs(node.balance) != 1){
                    if(node.balance == 2){
                        node = rightRotation(node);
                    }else if(node.balance == -2){
                        node = leftRotation(node);
                    }
                    if(node.parent == null) return node;
                    node = node.parent;
                    node.balance = balance(node.left, node.right);
                }
            }
            return node;
        }

        public void deleteNode(Key key){

            root = delete(root, key);
        }


        private Value getValue(AVLNode node, Key key){

            int result = key.compareTo(node.key);
            if(result > 0){
                return getValue(node.left, key);
            } else if(result < 0){
                return getValue(node.right, key);
            } else {
                return node.value;
            }
        }

        /*private AVLNode bypass(AVLNode node){
            Queue<AVLNode> queue = new LinkedList<>();
            queue.add(node);
            while(!queue.isEmpty()){
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
        }*/

        private void print(AVLNode node, int level) {
            if (node != null) {
                print(node.right,level+1);
                for (int i=0;i<level;i++) {
                    System.out.print("\t");
                }
                System.out.println(node.key + "->" + node.value+" h="+node.h+" balance="+node.balance);
                print(node.left,level+1);
            }
        }

        public void print() {
            print(root,0);
        }
    }





