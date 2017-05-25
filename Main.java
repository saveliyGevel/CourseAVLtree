package com.company;
import com.company.AVLtree;
import com.company.AVLtree.AVLNode;

public class Main {

    public static void main(String[] args) {
        AVLtree<Integer,Integer> t = new AVLtree<>();
        t.add(13,14);
        t.print();
    }
}


