## Greatly inspired by

 * [Akka in Action](http://www.manning.com/roestenburg/) book; 
 * Akka [docs](http://doc.akka.io/docs/akka/snapshot/scala/testing.html).

---

## Contents

* Stopping/Starting Actor System;
* Sync testing;
* Async actor patterns;
* Altering application configuration;
* Fault tolerance testing;

---

## Stopping/Starting Actor System

    class MyActorTest 
        extends TestKit(ActorSystem("test-system")) 
        with FunSpecLike {
    
      override protected def afterAll(): Unit = {
        super.afterAll()
        system.shutdown()
        system.awaitTermination()
      }
    }

#### Becomes:

    class MyActorTest extends TestKit(ActorSystem("test-system")) with AkkaTestBase {
        ...
    }

    trait AkkaTestBase 
        extends BeforeAndAfterAll 
        with FunSpecLike { this: TestKit with Suite =>
    
      override protected def afterAll() {
        super.afterAll()
        system.shutdown()
        system.awaitTermination()
      }
    }

---

#### Alternatively:

    class MyActorTest extends AkkaTestBase {
            ...
    }

    abstract class AkkaTestBase 
        extends TestKit(ActorSystem("test-system")) 
        with FunSpecLike 
        with BeforeAndAfterAll {
    
      override protected def afterAll() {
        super.afterAll()
        system.shutdown()
      }
    
    }

---

## Actor specific things

* [Sync unit-testing](http://doc.akka.io/docs/akka/snapshot/scala/testing.html#synchronous-unit-testing-with-testactorref) via `TestActorRef` in a single thread;

* [Async unit-testing](http://doc.akka.io/docs/akka/snapshot/scala/testing.html#Asynchronous_Integration_Testing_with_TestKit) with TestKit / TestProbe using multiple threads;

---

### Sync unit-testing

Via TestActorRef.

* Works with `CallingThreadDispatcher`;
* Supports either message-sending style, or direct invocations.

---

### Async unit-testing

Test kit provides some helpers:

* `testActors`;
* Trait `ImplicitSender`;
* Bunch of assertions;
* Multiple `test actors` ([more](http://doc.akka.io/docs/akka/snapshot/scala/testing.html#Using_Multiple_Probe_Actors));
* Event filter.

---

## Test actors

    testActor // Immediately available
    
    ImplicitSender // Places test actor in scope of the test
     
    TestProbe() // When you need more of those

---

### expectMsg family

    expectMsg[T](d: Duration, msg: T): T
    
    expectMsgPF[T](d: Duration)(pf: PartialFunction[Any, T]): T
    
    expectMsgClass[T](d: Duration, c: Class[T]): T
    
    ...
    
    expectNoMsg(d: Duration)
    
---

### Fishing family
 
    receiveN(n: Int, d: Duration): Seq[AnyRef]
 
    fishForMessage(max: Duration, hint: String)(pf: PartialFunction[Any, Boolean]): Any
    
    receiveWhile[T](max: Duration, idle: Duration, messages: Int)(pf: PartialFunction[Any, T]): Seq[T]

---

### Awaits

    awaitCond(p: => Boolean, max: Duration, interval: Duration)
    awaitAssert(a: => Any, max: Duration, interval: Duration)

<br/>    
    
### Ignores
    
    // They are stateful !!!
    ignoreMsg(pf: PartialFunction[AnyRef, Boolean])
    ignoreNoMsg

---

## Event filter

    ConfigFactory.parseString("""akka.loggers = ["akka.testkit.TestEventListener"]""")
    
    ...
    
    EventFilter.info(
        message = "Hello World!",
        occurrences = 1
    ).intercept {
        myActor ! DoSomeStuff
    }

---

### Common patterns

* Sending actors:
    * Forwarding actor,
    * Filtering actor,
    * Sequencing actor.

* Silent actors:
    * Really silent;
    * Side-effecting.
    
<br/><br/>
<small>Let's do some coding here!</small>

---

## Timeouts

![Dilation](http://mathworld.wolfram.com/images/eps-gif/Dilation_900.gif "Dilation")


```
akka.test.single-expect-default = 8 seconds

akka.test.timefactor = 10

....

within(200 millis) {
    ...
}

```

---

## Settings tweaking

### Using extensions as settings storage

Described [here](http://doc.akka.io/docs/akka/snapshot/scala/extending-akka.html#Application_specific_settings).

* Type safe,
* Convenient,
* Easy to modify for tests.

---

## Fault tolerance testing


---

Slides and code are available @
[http://github.com/mkotsur/akka-smart-testing](http://github.com/mkotsur/akka-smart-testing)
