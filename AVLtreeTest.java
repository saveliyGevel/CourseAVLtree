package com.company;

import org.junit.Test;


import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class AVLtreeTest {

    @Test
    public void size() throws Exception {
        AVLtree<Integer,Integer> tree = new AVLtree<>();
        assertEquals(0, tree.size());

        tree.put(4,5);
        tree.put(5,8);
        tree.put(6,1);
        tree.put(3,4);
        assertEquals(4, tree.size());

        tree.put(2,7);
        tree.put(4,10);
        tree.put(5,12);
        assertEquals(5, tree.size());
    }

    @Test
    public void isEmpty() throws Exception {
        AVLtree<Integer,Integer> tree = new AVLtree<>();
        assertThat(tree.isEmpty(), is(true));

        tree.put(1,12);
        tree.put(2,2);
        tree.put(3,5);
        assertFalse(tree.isEmpty());
    }

    @Test
    public void containsKey() throws Exception {
        AVLtree<Integer, Integer> tree = new AVLtree<>();

        tree.put(4,12);
        tree.put(7,14);
        tree.put(3,10);
        tree.put(8,12);
        tree.put(9,14);
        tree.put(12,10);

        assertTrue(tree.containsKey(4));
        assertTrue(tree.containsKey(3));
        assertTrue(tree.containsKey(7));
        assertTrue(tree.containsKey(8));
        assertFalse(tree.containsKey(10));
        assertFalse(tree.containsKey(13));
    }

    @Test
    public void containsValue() throws Exception {
        AVLtree<Integer, Integer> tree = new AVLtree<>();

        tree.put(4,12);
        tree.put(7,14);
        tree.put(3,10);
        tree.put(5,13);
        tree.put(8,9);
        tree.put(2,3);

        assertTrue(tree.containsValue(12));
        assertTrue(tree.containsValue(14));
        assertTrue(tree.containsValue(10));
        assertTrue(tree.containsValue(13));
        assertTrue(tree.containsValue(3));
        assertTrue(tree.containsValue(9));
    }

    @Test
    public void get() throws Exception {
        AVLtree<Integer, Integer> tree = new AVLtree<>();


        tree.put(27,11);
        tree.put(5,16);
        tree.put(9,12);
        tree.put(4,9);
        tree.put(13,17);
        tree.put(10,11);

        assertThat(tree.get(27) , is(11));
        assertThat(tree.get(5), is(16));
        assertThat(tree.get(9), is(12));
        assertThat(tree.get(4), is(9));
        assertThat(tree.get(13), is(17));
        assertThat(tree.get(10), is(11));

    }


    @Test
    public void clear() throws Exception {
        AVLtree<Integer,Integer> tree = new AVLtree<>();

        tree.put(1,4);
        tree.put(3,6);
        tree.put(2,11);
        tree.put(11,13);
        tree.put(8,1);
        tree.put(5,21);

        assertEquals(6, tree.size());

        tree.clear();

        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void add() throws Exception {
        AVLtree<Integer,Integer> tree = new AVLtree<>();

        tree.put(5,1);
        tree.put(2,13);
        tree.put(6,5);
        tree.put(7,3);
        tree.put(11,14);
        tree.put(9,2);
        tree.put(8,16);
        tree.put(14,13);
        tree.put(21,4);
        tree.put(19,19);
        tree.put(16,13);
        tree.put(1,31);

        assertTrue(tree.containsKey(2));
        assertTrue(tree.containsKey(6));
        assertTrue(tree.containsKey(7));
        assertTrue(tree.containsKey(11));
        assertTrue(tree.containsKey(9));
        assertFalse(tree.containsKey(31));
        assertFalse(tree.containsKey(43));
        assertFalse(tree.containsKey(41));
    }

    @Test
    public void remove() throws Exception {
        AVLtree<Integer,Integer> tree = new AVLtree<>();

        tree.put(10,1);
        tree.put(6,2);
        tree.put(14,3);
        tree.put(4,4);
        tree.put(8,5);
        tree.put(12,6);
        tree.put(16,7);
        tree.put(3,8);
        tree.put(5,2);
        tree.put(7,4);
        tree.put(13,3);
        tree.put(17,1);
        tree.put(18,5);


        assertTrue(tree.containsKey(12));
        assertEquals(13, tree.size());
        assertFalse(tree.isEmpty());

        //удаление узла с одним потомком
        tree.remove(12);
        assertEquals(12, tree.size());
        assertFalse(tree.containsKey(12));

        //удаление листа
        tree.remove(3);
        assertEquals(11, tree.size());
        assertFalse(tree.containsKey(3));

        //удаление узла с двумя потомками
        tree.remove(6);
        assertEquals(10, tree.size());
        assertFalse(tree.containsKey(6));

    }
}