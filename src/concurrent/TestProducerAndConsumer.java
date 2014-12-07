package concurrent;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
 * Lock/Conditon
 * 实现线程之间的通信
 * **/
public class TestProducerAndConsumer {
 private final LinkedList<Object> myList = new LinkedList<Object>();
 private final int MAX = 10;
 private final Lock lock = new ReentrantLock();
 private final Condition full = lock.newCondition();
 private final Condition empty = lock.newCondition();
 
 public void start() {
  new Producer().start();
  new Consumer().start();
 }
 
 public static void main(String[] args) throws Exception {
  TestProducerAndConsumer t = new TestProducerAndConsumer();
  t.start();
 }
 
 class Producer extends Thread {
  @Override
  public void run() {
   while (true) {
    lock.lock();
    try {
     while (myList.size() == MAX) {
      System.out.println("warning: it's full!");
      full.await();
     }
     Object o = new Object();
     if (myList.add(o)) {
      System.out.println("Producer: " + o);
      empty.signal();
     }
    }
    catch (InterruptedException ie) {
     System.out.println("producer is interrupted!");
    }
    finally {
     lock.unlock();
    }
   }
  }
 }
 
 class Consumer extends Thread {
  @Override
  public void run() {
   while (true) {
    lock.lock();
    try {
     while (myList.size() == 0) {
      System.out.println("warning: it's empty!");
      empty.await();
     }
     Object o = myList.removeLast();
     System.out.println("Consumer: " + o);
     full.signal();
    }
    catch (InterruptedException ie) {
     System.out.println("consumer is interrupted!");
    }
    finally {
     lock.unlock();
    }
   }
  }
 }
}