package com.company;

/**
 * Created by admin on 26.06.17.
 */
public class NodeAlreadyExistsException extends Exception {

    public static final String MESSAGE = "A Node already exists that contains the int value: ";

    public NodeAlreadyExistsException() {

    }

    public NodeAlreadyExistsException(String message) {
        super(message);
    }
}