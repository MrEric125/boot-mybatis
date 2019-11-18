package com.concurrent.locks;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author louis
 * <p>
 * Date: 2019/9/5
 * Description:
 */
@Slf4j
public class ReentrantLockTest {
    private static int totalcount = 5000;
    //    private static AtomicInteger sum = new AtomicInteger();
    private static int sum = 0;
    public static void main(String[] args) {
//        Semaphore semaphore = new Semaphore(totalcount,true);
        ReentrantLock reentrantLock = new ReentrantLock();
//        ExecutorService executorService = Executors.newCachedThreadPool();
        Runnable runnable = () -> {

            try {


                log.info("thread-name:{}", Thread.currentThread().getName());
                reentrantLock.lock();
                sum();
//                 semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        };
        for (int i = 0; i < 30; i++) {
//            executorService.execute(runnable);
             new Thread(runnable).start();
        }
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {

        }

//        log.info("sum值为：{}", sum.longValue());
        log.info("sum值为：{}", sum);
//        executorService.shutdown();
    }

    private static void sum() {
//        sum.getAndIncrement();
        sum++;

    }

}
