package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

import java.util.concurrent.Callable;

public interface AsyncExecutor {

    <T> AsyncResult<T> startProcess(Callable<T> block);

    <T> AsyncResult<T> startProcess(Callable<T> block, AsyncCallback<T> callback);

    <T> T endProcess(AsyncResult<T> asyncResult);
}
