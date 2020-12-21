package com.hstmpl.network.error;

public class RequestError extends Exception {

    public RequestError() {
    }

    public RequestError(String message) {
        super(message);
    }
}
