package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

public interface AsyncResult<T> {
    boolean isCompleted();

    T getValue();

    void await();
}
