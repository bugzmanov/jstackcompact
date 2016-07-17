package ru.bugzmanov.jstackcompact

import org.scalatest._

class ThreadDumpMatchingTest extends FlatSpec with Matchers {

  "ThreadDumps" should " be considered same if state and traces are equals" in {

    val threadDump = ThreadStack(
      """"NGSession 22: (idle) read chunk thread (NGInputStream pool) (idle)" prio=5 tid=0x00007fea3b82b000 nid=0x5a03 waiting on condition [0x0000700001961000]
        |   java.lang.Thread.State: WAITING (parking)
        |	at sun.misc.Unsafe.park(Native Method)
        |	- parking to wait for  <0x00000007eeb30078> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
        |	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:186)
        |	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2043)
        |	at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
        |	at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1068)
        |	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
        |	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
        |	at java.lang.Thread.run(Thread.java:745)""".stripMargin)

    val secondDump = ThreadStack(
      """"NGSession 13: (idle) read chunk thread (NGInputStream pool) (idle)" prio=5 tid=0x00007fea3b07d800 nid=0x5c03 waiting on condition [0x0000700001a64000]
        |   java.lang.Thread.State: WAITING (parking)
        |	at sun.misc.Unsafe.park(Native Method)
        |	- parking to wait for  <0x00000007eeb30078> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
        |	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:186)
        |	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2043)
        |	at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
        |	at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1068)
        |	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
        |	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
        |	at java.lang.Thread.run(Thread.java:745)""".stripMargin)

    threadDump.sameState(secondDump) should be(true)
  }

  "ThreadDumps" should "not be considered same if there is no traces" in {
    val threadDump = ThreadStack("""Service Thread" daemon prio=5 tid=0x00007fea3d00a800 nid=0x4f03 runnable [0x0000000000000000]
                       |   java.lang.Thread.State: RUNNABLE""".stripMargin)

    val secondDump = ThreadStack(""""Signal Dispatcher" daemon prio=5 tid=0x00007fea3b027000 nid=0x3a0f runnable [0x0000000000000000]
                       |   java.lang.Thread.State: RUNNABLE""".stripMargin)

    threadDump.sameState(secondDump) should be(false)
  }

}