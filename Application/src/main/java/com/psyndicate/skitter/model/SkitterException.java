package com.psyndicate.skitter.model;

public class SkitterException extends Exception {
    public SkitterException() {
        super();
    }

    public SkitterException(String msg) {
        super(msg);
    }

    public SkitterException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SkitterException(Throwable cause) {
        super(cause);
    }
};
