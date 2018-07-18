# Simple Exchange Matcher
Simple Scala application for processing securities exchanges between clients.

## Overview
This application works with two text files: clients and orders. After finishing it creates a result file with all processed clients. You can read about matching strategies in [the following section](#exchange-matchers).

### Clients file
Clients file has the following fields:
 * Client name
 * Client balance in dollars
 * Security 'A' amount
 * Security 'B' amount
 * Security 'C' amount
 * Security 'D' amount
 
You can find the example file [here](src/main/resources/clients.txt).

### Orders file
Orders file has the following fields:
 * Client name, who placed this order
 * Operation symbol: 's' for sel, 'b' for buy
 * Security name
 * Order price (for one security)
 * Amount of buying or selling securities
 
You can find the example file [here](src/main/resources/orders.txt).

### Exchange matchers
There are two possible matcher strategies: simple and partial.

[Simple matcher](src/main/scala/com/github/apanevin/matcher/service/SimpleExchangeMatcher.scala) creates an exchange by joining on security amount and price.
[Partial matcher](src/main/scala/com/github/apanevin/matcher/service/PartialExchangeMatcher.scala) creates an exchange by joining only on security price and it may take needed amount from different orders.

Necessary strategy can be chosen by overriding `exchange.matcher.class` property.

## Configuring
You can specify list of parameters:
 * `file.input.clients.path` path to [clients file](#clients-file)
 * `file.input.orders.path` path to [orders file](#orders-file)
 * `file.output.clients.name` output file name
 * `file.delimiter` input and output files delimiter
 * `exchange.matcher.class` full class name, which extends [ExchangeMatcher](src/main/scala/com/github/apanevin/matcher/service/ExchangeMatcher.scala) trait and matches clients offers

Default application parameters can be changed by editing [properties file](src/main/resources/application.properties) or by passing them to JVM while running (e.g. `java -Dname=value -jar`).

### Building
Application uses [Maven](https://maven.apache.org) for compiling, building and testing. You can build it using:
```sh
mvn clean package
```

This command will create jar with all compiled code and jar with code and all necessary dependencies. You can find jars at `target/` folder.

### Running
Running without overriding parameters:
```sh
java -jar ${simple-exchange-matcher-directory}/target/simple-exchange-matcher-<version>-jar-with-dependencies.jar
```

Running with parameters specified:
```sh
java \
-Dfile.output.clients.name=processed_clients_with_simple_strategy.txt \
-Dexchange.matcher.class=com.github.apanevin.matcher.service.SimpleExchangeMatcher \
-jar ${simple-exchange-matcher-directory}/target/simple-exchange-matcher-<version>-jar-with-dependencies.jar
```

### Testing
[Scalatest](http://www.scalatest.org) is used for unit testing. You can run test with Maven command:
```sh
mvn test
```

Note, that `mvn package` also runs all unit tests.
