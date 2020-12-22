package com.hstmpl.net.error;

public class RequestError extends Exception {

    public RequestError() {
    }

    public RequestError(String message) {
        super(message);
    }
}
