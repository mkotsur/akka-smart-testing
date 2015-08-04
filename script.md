# Smart testing of your Akka code

Greatly inspired by [Akka in Action](http://www.manning.com/roestenburg/) book and Akka [docs](http://doc.akka.io/docs/akka/snapshot/scala/testing.html).


## Stopping/Starting Actor System

* Should be done in a single place;

## Actor specific things

* [Sync unit-testing](http://doc.akka.io/docs/akka/snapshot/scala/testing.html#synchronous-unit-testing-with-testactorref) via `TestActorRef` in a single thread;

* [Async unit-testing](http://doc.akka.io/docs/akka/snapshot/scala/testing.html#Asynchronous_Integration_Testing_with_TestKit) with TestKit / TestProbe using multiple threads;

* Testing through logging ???


### Sync unit-testing

* Works with `CallingThreadDispatcher`;
* Supports either message-sending style, or direct invocations.


### Async unit-testing

There are several patterns:

* Silent actor,
* Sending actor:
    ** Forwarding actor,
    ** Filtering actor;
    ** Sequencing actor.
* Side-effecting actor.




## Timeouts
    
Magical word: dilation.

![Dilation](http://mathworld.wolfram.com/images/eps-gif/Dilation_900.gif "Dilation")
    

## Settings tweaking

### Using extensions as settings storage
    
Described [here](http://doc.akka.io/docs/akka/snapshot/scala/extending-akka.html#Application_specific_settings).
  
* Type safe,
* Convenient,
* Easy to modify for tests.
  
    