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
groovy:000>
```

#### JDBC

A [groovy facad over jdbc](http://docs.groovy-lang.org/latest/html/api/groovy/sql/Sql.html) is leveraged to make querying and inserting data easy.

```
groovy:000> sql.eachRow('''
groovy:001> select person.name, city.name as city from person inner join city on person.city_id = city.id
groovy:002> ''') { println it }
[NAME:Joe, CITY:New York]
[NAME:Bob, CITY:Boston]
===> null
groovy:000>
```