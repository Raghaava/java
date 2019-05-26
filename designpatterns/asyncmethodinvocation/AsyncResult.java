package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

import java.util.concurrent.ExecutionException;

public interface AsyncResult<T> {
    boolean isCompleted();

    T getValue() throws ExecutionException;

    void await() throws InterruptedException;
}
