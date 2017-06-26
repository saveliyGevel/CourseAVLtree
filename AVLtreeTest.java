package com.company;

/**
 * Created by admin on 26.06.17.
 */
public class AVLtreeTest {

    // declare constants
    public static final String TESTING_INSERT = "Testing put()";
    public static final String INSERTING = "Inserting ";

    public static final String TESTING_FIND = "Testing containsValue()";
    public static final String FINDING = "Finding ";

    public static final String TESTING_DELETE = "Testing remove()";
    public static final String DELETE = "Delete ";

    public static final String SUCCESS = "SUCCESS!";
    public static final String FAILED = "FAILED!";

    public static final String CORRECT = "CORRECT!";
    public static final String INCORRECT = "INCORRECT!";

    public static final String ELLIPSIS = "...";

    public AVLtreeTest() {

    }

    public static void print(String msg, boolean newLine) {
        if (newLine) {
            System.out.println(msg);
        } else {
            System.out.print(msg);
        }
    }

    public static void print(int value, boolean newLine) {
        if (newLine) {
            System.out.println(value);
        } else {
            System.out.print(value);
        }
    }
}