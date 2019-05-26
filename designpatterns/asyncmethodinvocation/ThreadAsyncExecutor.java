package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadAsyncExecutor<T> implements AsyncExecutor {

    private AtomicInteger integer = new AtomicInteger(0);

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> block) {
        return startProcess(block, null);
    }

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> block, AsyncCallback<T> callback) {
        CompletableResult result = new CompletableResult(callback);
        new Thread(() -> {
            try {
                result.setValue(block.call());
            } catch (Exception e) {
                result.setException(e);
            }
        }, "executor-" + integer.incrementAndGet()).start();

        return result;
    }

    @Override
    public <T> T endProcess(AsyncResult<T> asyncResult) throws InterruptedException, ExecutionException {
        if (!asyncResult.isCompleted()) {
            asyncResult.await();
        }
        return asyncResult.getValue();
    }

    static class CompletableResult<T> implements AsyncResult<T> {

        private static final int RUNNING = 0;
        private static final int COMPLETED = 1;
        private static final int FAILED = 2;

        private T value;
        private Exception exception;

        private volatile int state = RUNNING;
        private Optional<AsyncCallback<T>> callback = null;
        private Object lock;

        CompletableResult(AsyncCallback<T> callback) {
            this.callback = Optional.ofNullable(callback);
            this.lock = new Object();
        }

        @Override
        public boolean isCompleted() {
            return state > RUNNING;
        }

        @Override
        public T getValue() throws ExecutionException {
            if (state == COMPLETED) {
                return value;
            }
            if (state == FAILED) {
                throw new ExecutionException(exception);
            }
            throw new IllegalStateException("Execution not completed yet.");
        }

        @Override
        public void await() throws InterruptedException {
            synchronized (lock) {
                while (!isCompleted()) {
                    lock.wait();
                }
            }
        }

        public void setValue(T value) {
            this.value = value;
            this.state = COMPLETED;
            callback.ifPresent(ac -> ac.onComplete(value, Optional.<Exception>empty()));
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        public void setException(Exception ex) {
            this.exception = ex;
            this.state = FAILED;
            this.callback.ifPresent(ac -> ac.onComplete(this.value, Optional.of(this.exception)));
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }

}
