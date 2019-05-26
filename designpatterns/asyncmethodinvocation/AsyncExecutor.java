package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public interface AsyncExecutor {

    <T> AsyncResult<T> startProcess(Callable<T> block);

    <T> AsyncResult<T> startProcess(Callable<T> block, AsyncCallback<T> callback);

    <T> T endProcess(AsyncResult<T> asyncResult) throws InterruptedException, ExecutionException;
}
