package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

import java.util.Optional;

public interface AsyncCallback<T> {
    void onComplete(T t, Optional<Exception> ex);
}
