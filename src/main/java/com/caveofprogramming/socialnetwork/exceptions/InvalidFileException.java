package com.caveofprogramming.socialnetwork.exceptions;

public class InvalidFileException extends Exception {
    /**
     *
     * @param message
     */
    private static final long serialVersionUID = 1L;

    public InvalidFileException(String message){
        super(message);
    }
}
