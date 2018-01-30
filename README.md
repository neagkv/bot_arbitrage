# Cryptocurrency arbitrage and detection bot.

### A bot designed to detect arbitrage opportunities for cryptocurrencies by checking the price on various exchanges for the purpose of trading and information gathering and logging.

**Warning:**  Any liabilities or damages
induced by the use of this tool is the sole responsibility of the user of this tool and not the developers.

## Description

This is a bot that utilizes the [XChange Java library](http://knowm.org/open-source/xchange/) to interact with the apis
of leading cryptocurrency exchanges. The bot has two primary functions, arbitrage and detection. For the the detection function,
the user must enter the exchanges and currency pairs they would like to check as well as an action selection. The detection action selections
are print and log. The bot will then either print the currency pair and exchanges with the greatest difference in price to the console or log
the highest ask price, lowest ask price and price difference for each request to a database repeatedly at a desired time interval. For the arbitrage
function there are three actions,print, email and trading. For each of the actions the user must enter a currency pair a list of exchanges a desired
arbitrage margin and an action. The print action will print the best arbitrage opportunity found for the given pair and exchanges to the console.
The email action will additionally require an email address that is verified by amazon aws in the call. The email address will send an arbitrage update
with the best arbitrage opportunity to the given email address. For the final arbitrage action (trading) the user must give a trade value base (a trade value
in the base currency selected) along with the call. The bot will then trade on the exchange with the lowest ask and highest bid to capitalize on the arbitrage,
and insert information related to the trade into a database.


## Exchange List and API Documentation

Exchanges:


* [GDAX](https://docs.gdax.com/)

* [Kraken](https://www.kraken.com/help/api)

* [Poloniex](https://poloniex.com/support/api/) no fiat currencies

* [Gemini](https://docs.gemini.com/rest-api/)

* [Bittrex](https://bittrex.com/home/api)

* [Bitstamp](https://www.bitstamp.net/api/)

* [Bitfinex](https://docs.bitfinex.com/docs) non US residents only

* [Binance](https://support.binance.com/hc/en-us/articles/115003235691-Binance-API-Trading-Rules)


## Exchange Notes:

* For best use, make sure that the currency pair or pairs you are using are supported by the selected exchanges.

* For arbitrage trade action, you will need to create an account on each exchange you would like to use and obtain the api key, api secret key
and any other needed api specifications.

* For each currency pair the first currency is the base currency and the second is the counter currency. For example for the currency pair ETH_BTC,
ETH (ethereum) is the base currency and BTC (bitcoin) is the counter currency.

* You must set the tradeValueBase or amount of base currency you would like to trade in the arbitrage trade action method. Different exchanges have different
minimums on the amount of each currency they will allow you to trade. Find the limits of each exchange before you trade.

* To be able to complete an arbitrage trade you must make sure that you have at least enough base currency on the wallets of each exchange as the tradeValueBase set in the method.
additionally you will need enough counter currency to purchase the amount of baseCurrency set in the method. For example for the CurrencyPair ETH_BTC, if you set the
tradeValue base as 0.02 in the method, it means you wish to trade 0.02 ETH, you must make sure that you have at least 0.02 ETH on each exchange you are selecting and that
you have enough BTC to purchase 0.02 ETH on each exchange as well.

* Make sure to check fee information on the exchanges you are using, and make sure that the arbitrage margin you set is enough to cover the cost of them.



## Getting Started

* Requires a [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) to be
installed on the machine you are going to use to run the program.

* For arbitrage email action you must have an active account on amazon [aws](https://docs.aws.amazon.com/ses/latest/DeveloperGuide/smtp-credentials.html) and the required credentials.

* Make sure you set region=us-east-1 as your region.

* You must have a valid email address verified by aws that you can hardcode into the From variable in the [Email class](https://github.com/Thleruth/bot_arbitrage/tree/master/src/main/java/co/codingnomads/bot/arbitrage/model/email)
  in replace of cryptoarbitragebot25@gmail.com.

* Once you have one email address verified you can verify [additional email addresses](https://console.aws.amazon.com/ses/home?region=us-east-1#verified-senders-email) and set them in the [controller](https://github.com/Thleruth/bot_arbitrage/blob/master/src/main/java/co/codingnomads/bot/arbitrage/Controller.java)
  in arbitrageEmailAction method in replace of "your-email-address" to have an arbitrage update sent to that address.

* For arbitrage trade action, arbitrage email action and detection log action, requires [MySQL](https://dev.mysql.com/downloads/mysql/) version 5.7 or higher to be installed on the machine and running.


Required:

* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](http://maven.apache.org/download.cgi)
* [MySQL](https://dev.mysql.com/downloads/mysql/)

Clone the project to your desired location

```
$ git clone https://github.com/Thleruth/bot_arbitrage
```

Set up aws credentials:

obtain [aws credentials](https://docs.aws.amazon.com/ses/latest/DeveloperGuide/smtp-credentials.html)

copy credentials

```
$ cd /~
$ mkdir .aws
$ cd .aws
$ touch credentials
$ vim credentials
```

paste credentials and save


Set up database:

```
 $ mysql -u root -p
 > mysql CREATE DATABASE bot.arbitrage;
 > mysql USE bot.arbitrage;
 > mysql SOURCE /../bot_arbitrage/src/main/mysql/bot.arbitrage.sql;
```

configure and run:

### choose 1 of the following actions to run, (default is set to arbitrage print action). You may uncomment the examples of the actions to use them.

* Arbitrage Print Action - prints the lowest ask, highest bid and the exchanges for each to console.

* Arbitrage Email Action - emails arbitrage update of lowest ask, highest bid and exchanges. Enters email info into database.

* Arbitrage Trade Action - trades counter currency for base currency on the exchanges with the lowest ask and highest bid. Enters trade history into database.

* Detection print Action - Checks multiple exchanges for multiple currency pairs and prints the exchanges with the greatest difference to the console.

* Detection log Action - logs the exchanges and currency pairs with the greatest differences to the database at a given time interval.

### enter desired parameters see ACTION EXAMPLES below for more information

once you are all set up

```
$ cd  bot_arbitrage
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

# Action Examples

### To set Actions and parameters

```
$ cd  bot_arbitrage/src/main/java/co/codingnomads/bot/arbitrage
open Controller.java
```
* manually set the following actions to your desired specifications

* For best use only use one of the following actions

* For all actions Arbitrage & Detection, set the exchanges you would like to use, keep in mind that the exchanges should support the currency pair you plan on selecting

![Exchange List Example](https://user-images.githubusercontent.com/21184509/35549904-d36ef312-0555-11e8-8bc2-df4b42400d6f.png)

## Arbitrage

* Optionally if you would like the Arbitrage Action to repeate you can set the loopIterations (how many times the action will run) &
the TimeIntervalRepeater(amount of time before action repeats in milliseconds) must be at least 5000 ms.

![Repeating Actions](https://user-images.githubusercontent.com/21184509/35549976-271804ae-0556-11e8-92d1-6ceb2d4d264a.png)

* Then choose your action

### Arbitrage Print Action example(default)

* set arbitrage margin (percentage of base difference you would like to search for). For a difference of 3% set 0.03

* set the currencyPair you would like to check

![arbitragePrintAction](https://user-images.githubusercontent.com/21184509/35549974-1e826c62-0556-11e8-9d18-90082280bb46.png)

### Arbitrage Email Action

* In addition to the arbitrage margin and currencyPair, you must set the email address you would like to receive emails.

![arbitrageEmailAction](https://user-images.githubusercontent.com/21184509/35549986-2f116ea2-0556-11e8-8786-e593f3c8f04f.png)

### Arbitrage Trade Action

* You must first enter the exchange specifications for the exchanges you wish to use

![exchangeSpecifications](https://user-images.githubusercontent.com/21184509/35549930-eb750104-0555-11e8-9f13-735a54517ebd.png)

* You must also set the volume of base currency you would like to trade

![arbitrageTradeAction](https://user-images.githubusercontent.com/21184509/35549967-11d78088-0556-11e8-961b-5ad2b88dcc7c.png)

## Detection

* For all detection actions set the currency pairs you would like to find the best price differences for.

![currencyPairs](https://user-images.githubusercontent.com/21184509/35549962-085deaf6-0556-11e8-9c52-5cc39ca0c600.png)

### Detection Print Action

![detectionPrintAction](https://user-images.githubusercontent.com/21184509/35549936-f80d6000-0555-11e8-8282-5e9c5978aec6.png)

### Detection Log Action

* You must set the time interval you would like the detection log action to repeatedly enter the best price difference into the database. Must be at least 6000 ms.

![detectionLogAction](https://user-images.githubusercontent.com/21184509/35550014-5c5585ce-0556-11e8-83d3-7fc4a47036c7.png)

## Contributors

* [Thomas Leruth](https://github.com/Thleruth)

* [Kevin Neag](https://github.com/neagkv)

## Acknowledgements

* special thanks to the team working on the [XChange project](https://github.com/timmolter/XChange) for providing a beautifully streamlined exchange api library.

* thanks to [CodingNomads](http://codingnomads.co) for help and supportg


