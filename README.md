# Cryptocurrency arbitrage and detection bot.

A bot designed to detect arbitrage opportunities for cryptocurrencies by checking the price on various exchanges for the purpose of trading and information gathering and logging.

**Warning:**  Any liabilities or damages
induced by the use of this tool is the sole responsibility of the user of this tool and not the developers.

## Before you start


## Description

This is a bot that utilizes the XChange Java library <url>http://knowm.org/open-source/xchange/)</url> to interact with the apis
of over 60 leading cryptocurrency exchanges. The bot has two primary functions, arbitrage and detection. For the the detection function,
the user must enter the exchanges and currency pairs they would like to check as well as an action selection. The detection action selections
are print and log. The bot will then either print the currency pair and exchanges with the greatest difference in price to the console or log
the highest ask price, lowest ask price and price difference for each request to a database repeatedly at a desired time interval, depending on
the action request from the user. For the arbitrage function there are three actions,print, email and trading. For each of the actions the user
must enter a currency pair a list of exchanges a desired arbitrage margin and an action. The print action will print the best arbitrage opportunity
found for the given pair and exchanges to the console. The email action will additionally require an email address that is verified by amazon aws in
the call. The email address will send an arbitrage update with the best arbitrage opportunity to the given email address. For the final arbitrage action
(trading) the user must give a trade value base (a trade value in the base currency selected) along with the call. The bot will then trade on the exchange
with the lowest ask and highest bid to capitalize on the arbitrage, and insert information related to the trade into a database.


## Exchange List and API Documentation

Exchanges:

```
* GDAX (https://docs.gdax.com/)

* Kraken (https://www.kraken.com/help/api)

* Poloniex (https://poloniex.com/support/api/) no fiat currencies

* Gemini (https://docs.gemini.com/rest-api/)

* Bittrex (https://bittrex.com/home/api)

* Bitstamp (https://www.bitstamp.net/api/)

* Bitfinex (https://docs.bitfinex.com/docs) non US residents only

* Binance (https://support.binance.com/hc/en-us/articles/115003235691-Binance-API-Trading-Rules)

```


## Getting Started

requires a [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) to be
installed on the machine you are going to use to run the program.

you must have an active account on amazon aws and the required credentials (https://docs.aws.amazon.com/ses/latest/DeveloperGuide/smtp-credentials.html)


Required:
* [JDK 8](ttp://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](http://maven.apache.org/download.cgi)

Clone the project on your desired location

```
$ git clone https://github.com/Thleruth/bot_arbitrage
$ cd bot_arbitrage
$ mvn clean package
$ mvn spring-boot:run

```

### Maven
Add the following dependencies in your [`pom.xml`](https://github.com/Thleruth/bot_arbitrage/blob/master/pom.xml)

#### Dependencies
```xml

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.6</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk</artifactId>
            <version>1.11.106</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-core</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-gdax</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-kraken</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-binance</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-bitstamp</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-poloniex</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-bitfinex</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-bittrex</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.knowm.xchange</groupId>
            <artifactId>xchange-gemini</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.5</version>
        </dependency>
```

#### Plugin
``` xml
    <plugin>
         <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-maven-plugin</artifactId>
     </plugin>
```

## Configuration

WIP

## Contributors

* [Thomas Leruth](https://github.com/Thleruth)
* [Kevin Neag](https://github.com/neagkv)


## Acknowledgements
*


