package org.interview.preperation.java.designpatterns.asyncmethodinvocation;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger("App.class");

    public static void main(String args[]) throws InterruptedException {
        AsyncExecutor executor = new ThreadAsyncExecutor();

        AsyncResult<Integer> asyncResult1 = executor.startProcess(lazyVal(10, 500));
        AsyncResult<String> asyncResult2 = executor.startProcess(lazyVal("test", 300));
        AsyncResult<Long> asyncResult3 = executor.startProcess(lazyVal(50L, 700));

        AsyncResult<Integer> asyncResult4 = executor.startProcess(lazyVal(20, 400), callback("Callback result 4"));
        AsyncResult<String> asyncResult5 = executor.startProcess(lazyVal("callback", 600), callback("Callback result 5"));

        Thread.sleep(350);
        logger.info("Some hard work done");

        // wait for completion of the tasks
        Integer result1 = executor.endProcess(asyncResult1);
        String result2 = executor.endProcess(asyncResult2);
        Long result3 = executor.endProcess(asyncResult3);
        asyncResult4.await();
        asyncResult5.await();

        // log the results of the tasks, callbacks log immediately when complete
        logger.info("Result 1: " + result1);
        logger.info("Result 2: " + result2);
        logger.info("Result 3: " + result3);
    }

    static <T> Callable<T> lazyVal(T val, long waitMilliSecs) {
        return () -> {
            Thread.sleep(waitMilliSecs);
            logger.info("Task completed wit value " + val);
            return val;
        };
    }

    static <T> AsyncCallback<T> callback(String name) {
        return (value, ex) -> {
            if (ex.isPresent()) {
                logger.info(name + " failed: " + ex.map(Exception::getMessage).orElse(""));
            } else {
                logger.info(name + " : " + value);
            }
        };
    }
}
