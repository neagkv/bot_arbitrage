package co.codingnomads.bot.arbitrage;


import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.EmailSendTime;
import co.codingnomads.bot.arbitrage.model.detectionAction.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.model.detectionAction.DetectionLogAction;
import co.codingnomads.bot.arbitrage.model.exceptions.EmailLimitException;
import co.codingnomads.bot.arbitrage.model.exchange.*;
import co.codingnomads.bot.arbitrage.service.MapperService;
import co.codingnomads.bot.arbitrage.service.arbitrage.Arbitrage;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.io.IOException;
import java.util.ArrayList;



/**
 * Created by Thomas Leruth on 12/11/17
 *
 * Controller for running the arbitrage or detection bot
 */

@SpringBootApplication
public class Controller {

    @Autowired
    MapperService mapperService;
    public static void main(String[] args) throws IOException, InterruptedException, EmailLimitException {

        SpringApplication.run(Controller.class);




        ArrayList<ExchangeSpecs> ExchangeList = new ArrayList<>();

        ExchangeList.add(new KrakenSpecs()); // internal: good but slow
        ExchangeList.add(new GDAXSpecs()); // internal: good but waiting limit increase
//        ExchangeList.add(new BitfinexSpecs()); // internal: good but waiting limit increase
//        ExchangeList.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
//        ExchangeList.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)
        // ExchangeList.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)

////        start for arbitrage
           Arbitrage arbitrage = new Arbitrage();
           //ArbitrageActionSelection arbitrageActionSelection = new ArbitrageTradingAction(1.01,0.02);
//        // do not use anything else than Kraken or GDAX for Arbitrage up to now. Kraken min ETH is 0.02 and GDAX: 0.01
        ArbitrageActionSelection arbitrageActionSelection = new ArbitragePrintAction(1.01);
        ArbitrageActionSelection arbitrageActionSelection2 = new ArbitrageEmailAction(1.03, "neagkv@gmail.com");
//
//       arbitrage.run(
//                CurrencyPair.ETH_EUR,
//                ExchangeList,
//                arbitrageActionSelection2);
////        end for arbitrage

//        start for detection
       // Detection detection = new Detection();

        ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
        currencyPairList.add(CurrencyPair.BCH_EUR);
        currencyPairList.add(CurrencyPair.ETH_EUR);
        currencyPairList.add(CurrencyPair.BTC_EUR);

        EmailSendTime emailSendTime = new EmailSendTime();

        Controller controller = new Controller();
        controller.testEmail();

      //MapperService mapperService = new MapperService();
       // mapperService.insertTimeOfCall(emailSendTime);
       // mapperService.getALL();

        // DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
        //DetectionActionSelection detectionActionSelection = new DetectionLogAction(4000);

       // detection.run(currencyPairList, ExchangeList, detectionActionSelection);
//        end for detection

//        ExchangeList.add(new GDAXSpecs("qqq", "", "")); // internal: good but waiting limit increase
//        ExchangeList.add(new KrakenSpecs("bbbb", "dadad")); // internal: good but slow
//        ExchangeList.add(new BitfinexSpecs()); // internal: good but waiting limit increase
//        ExchangeList.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
//        ExchangeList.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)
//        ExchangeList.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)


//        start for detection
      //  Detection detection = new Detection();

       // ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
//        currencyPairList.add(CurrencyPair.BCH_EUR);
        //currencyPairList.add(CurrencyPair.ETH_EUR);
//        currencyPairList.add(CurrencyPair.BTC_EUR);

        //DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
//        DetectionActionSelection detectionActionSelection = new DetectionLogAction(4000);
//
//        detection.run(currencyPairList, ExchangeList, detectionActionSelection);
//        end for detection

    }

    public void testEmail(){
        EmailSendTime emailSendTime = new EmailSendTime();
        mapperService.insertTimeOfCall(emailSendTime);
    }

}

