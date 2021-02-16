package com.project.primenumbersservice.exception;

public class FailedRequestException extends RuntimeException {

    public FailedRequestException(String s, Throwable t) { super(s, t); }
}
