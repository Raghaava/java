package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

import java.util.concurrent.Callable;

public class ThreadAsyncExecutor<T> implements AsyncExecutor {

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> block) {
        return startProcess(block,null);
    }

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> block, AsyncCallback<T> callback) {
        return null;
    }

    @Override
    public <T> T endProcess(AsyncResult<T> asyncResult) {
        return null;
    }
}
