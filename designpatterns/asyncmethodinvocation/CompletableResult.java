package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

public class CompletableResult<T> implements AsyncResult<T> {
    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public T getValue() {
        return null;
    }

    @Override
    public void await() {

    }
}
