package io.ohjongsung.support;

/**
 * Created by ohjongsung on 2017-05-07. ResourceNotFoundException
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
