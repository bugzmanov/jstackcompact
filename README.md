# jstackcompact

Simple app to compact thread dumps produced by jstack: collapses all threads in exact same state into one.
(most common case: to reduce noise of threads being parked in thread pool) 

Example

Before:

```
> jstack 3265

"Attach Listener" daemon prio=5 tid=0x00007fcafcd5b000 nid=0x5707 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"NGSession 2: 127.0.0.1: compile-server read chunk thread (NGInputStream pool) (idle)" prio=5 tid=0x00007fcafe9a4800 nid=0x3c07 waiting on condition [0x0000700001a64000]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000007c20d9a68> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:186)
	at java.lang.Thread.run(Thread.java:745)

"NGSession 2: 127.0.0.1: compile-server read stream thread (NGInputStream pool) (idle)" prio=5 tid=0x00007fcafb845800 nid=0x4107 waiting on condition [0x0000700001555000]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000007c20d9a68> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:186)
	at java.lang.Thread.run(Thread.java:745)

```

After
```
>jstack 3265 | java -jar target/scala-2.11/jstackcompact.jar

"Attach Listener" daemon prio=5 tid=0x00007fcafcd5b000 nid=0x5707 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

Threads:[
  "NGSession 2: 127.0.0.1: compile-server read chunk thread (NGInputStream pool) (idle)" prio=5 tid=0x00007fcafe9a4800 nid=0x3c07 waiting on condition [0x0000700001a64000]
  "NGSession 2: 127.0.0.1: compile-server read stream thread (NGInputStream pool) (idle)" prio=5 tid=0x00007fcafb845800 nid=0x4107 waiting on condition [0x0000700001555000]
]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000007c20d9a68> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:186)
	at java.lang.Thread.run(Thread.java:745)

```

## Usage

```
git clone https://github.com/bugzmanov/jstackcompact.git
cd jstackcompact
sbt assembly

java -jar target/scala-2.11/jstackcompact.jar -h
```
