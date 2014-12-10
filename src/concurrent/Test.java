package concurrent;
import java.util.Date;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

import java.util.concurrent.Semaphore;

import java.util.concurrent.TimeUnit;
/*
 * 
 * 流控程序，用于控制请求的次数
 * */
public class Test {

    final static int MAX_QPS = 10;

    final static Semaphore semaphore = new Semaphore(MAX_QPS);

    public static void main (String ... args) throws Exception {

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {

            @Override

            public void run() {

                semaphore.release(MAX_QPS/2);

            }

        }, 1000, 500, TimeUnit.MILLISECONDS);

        //lots of concurrent calls:100 * 1000
        ExecutorService pool = Executors.newFixedThreadPool(100);

        for (int i=100;i>0;i--) {

            final int x = i;

            pool.submit(new Runnable() {

                @Override

                public void run() {

                    for (int j=1000;j>0;j--) {

                        semaphore.acquireUninterruptibly(1);
                        remoteCall(x, j);

                    }

                }

            });

        }

        pool.shutdown();

        pool.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("DONE");
    }

    private static void remoteCall(int i, int j) {
        System.out.println(String.format("%s - %s: %d %d",new Date(),
            Thread.currentThread(), i, j));
    }

}