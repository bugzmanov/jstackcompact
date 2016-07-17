package ru.bugzmanov.jstackcompact

import org.scalatest._

class ThreadDumpParsingTest extends FlatSpec with Matchers {

  "A ThreadDump parser" should "parse valid stacks" in {
    val stackStr = """"Reference Handler" daemon prio=5 tid=0x00007fea3a008800 nid=0x3603 in Object.wait() [0x0000700000c37000]
                  |   java.lang.Thread.State: WAITING (on object monitor)
                  |	at java.lang.Object.wait(Native Method)
                  |	- waiting on <0x00000007eeb20290> (a java.lang.ref.Reference$Lock)
                  |	at java.lang.Object.wait(Object.java:503)
                  |	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:133)
                  |	- locked <0x00000007eeb20290> (a java.lang.ref.Reference$Lock)""".stripMargin

    val stack = ThreadStack(stackStr)

    stack.threadName should be("\"Reference Handler\" daemon prio=5 tid=0x00007fea3a008800 nid=0x3603 in Object.wait() [0x0000700000c37000]")
    stack.state should be (Some("   java.lang.Thread.State: WAITING (on object monitor)"))
    stack.traceElements should have size 5
  }

  it should "parse stacks without traces" in {
    val stackStr = """"DestroyJavaVM" prio=5 tid=0x00007fea3b07c800 nid=0x1703 waiting on condition [0x0000000000000000]
                     |   java.lang.Thread.State: RUNNABLE""".stripMargin

    val stack = ThreadStack(stackStr)

    stack.threadName should be("\"DestroyJavaVM\" prio=5 tid=0x00007fea3b07c800 nid=0x1703 waiting on condition [0x0000000000000000]")
    stack.state should be (Some("   java.lang.Thread.State: RUNNABLE"))
    stack.traceElements should have size 0
  }

  it should "parse thread dump without state and trace" in {
    val stackStr = "\"GC task thread#0 (ParallelGC)\" prio=5 tid=0x00007fea3a801000 nid=0x2403 runnable"
    val stack = ThreadStack(stackStr)

    stack.threadName should be("\"GC task thread#0 (ParallelGC)\" prio=5 tid=0x00007fea3a801000 nid=0x2403 runnable")
    stack.state should be(None)
    stack.traceElements should have size 0
  }
}