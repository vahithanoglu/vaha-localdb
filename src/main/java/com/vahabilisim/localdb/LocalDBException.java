package com.vahabilisim.localdb;

public class LocalDBException extends Exception {

    public LocalDBException(String message) {
        super(message);
    }

    public LocalDBException(Throwable clause) {
        super(clause);
    }

    public LocalDBException(String message, Throwable clause) {
        super(message, clause);
    }

}
