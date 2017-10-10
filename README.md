## Apache Ignite playground

This project is intended to provide a way to try out key features of Ignite.

It takes advantage of the groovysh repl in order to do so.

### Building

```
./gradlew build
```

### Running

```
cd build/distributions
tar xf ignite-playground.tar
ignite-playground/bin/ignite-playground
```

### Functionality

#### Initialization

The [init.groovy](src/main/dist/conf/init.groovy) script is executed during startup, initializing
various parts of the framework.

#### JCache Compatible

There is an "ExpensiveValueLoader" that simulates doing some work before returning "Value for $key".

It should be obvious that the work isn't performed a second time. :smile:

```
groovy:000> expensiveCache.get('myKey')
[2017-10-08 17:48:55,606] INFO - Doing expensive load for key: myKey
[2017-10-08 17:48:55,808] INFO - Still working on it... hold on...
[2017-10-08 17:48:56,008] INFO - Still working on it... hold on...
[2017-10-08 17:48:56,209] INFO - Still working on it... hold on...
[2017-10-08 17:48:56,409] INFO - Still working on it... hold on...
[2017-10-08 17:48:56,610] INFO - Still working on it... hold on...
===> Value for myKey
groovy:000> expensiveCache.get('myKey')
===> Value for myKey
```

#### JDBC

A [groovy facad over jdbc](http://docs.groovy-lang.org/latest/html/api/groovy/sql/Sql.html) is leveraged to make querying and inserting data easy.

```
groovy:000> sql.eachRow('''
groovy:001> select person.name, city.name as city from person inner join city on person.city_id = city.id
groovy:002> ''') { println it }
[NAME:Joe, CITY:New York]
[NAME:Bob, CITY:Boston]
```

#### Compute

##### Async

Repl version of the [compute async example](https://github.com/apache/ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/computegrid/ComputeAsyncExample.java)

```
groovy:000> "Print words using runnable".split(" ").collect{word -> return compute.runAsync{System.out.println(">>> Printing $word")}}.each{it.get()}
>>> Printing Print
>>> Printing words
>>> Printing using
>>> Printing runnable
```

##### Broadcast

Repl version of the [compute broadcast example](https://github.com/apache/ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/computegrid/ComputeBroadcastExample.java)

```
groovy:000> compute.broadcast({println '>>> Hello Node! :)'} as org.apache.ignite.lang.IgniteRunnable)
>>> Hello Node! :)
```

##### Compute Callable

Repl version of the [compute callable example](https://github.com/apache/ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/computegrid/ComputeCallableExample.java)

```
groovy:000> "Count characters using callable".split(" ").collect{word -> return {word.length()}}
===> [groovysh_evaluate$_run_closure1$_closure2@77c7ed8e, groovysh_evaluate$_run_closure1$_closure2@453d496b, groovysh_evaluate$_run_closure1$_closure2@66bacdbc, groovysh_evaluate$_run_closure1$_closure2@2c6ee758]
groovy:000> compute.call("Count characters using callable".split(" ").collect{word -> return {word.length()}})
===> [5, 5, 10, 8]
```

##### Compute Closure

Repl version of the [compute closure example](https://github.com/apache/ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/computegrid/ComputeClosureExample.java)

```
groovy:000> compute.apply({String it ->it.length()}, "Count characters using closure".split(" ").toList()).sum()
===> 27
```

##### Compute Runnable

Repl version of the [compute runnable example](https://github.com/apache/ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/computegrid/ComputeRunnableExample.java)

```
groovy:000> "Print words using runnable".split(" ").each{word -> compute.run{println ">>> Printing $word"}}
>>> Printing Print
>>> Printing words
>>> Printing using
>>> Printing runnable

```

##### Executor Service

Repl version of the [executor service example](https://github.com/apache/ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/datastructures/IgniteExecutorServiceExample.java)

```
groovy:000> "Print words using runnable".split(" ").each{word -> exec.submit{println ">>> Printing $word"}}
>>> Printing Print
>>> Printing words
>>> Printing using
>>> Printing runnable

```

