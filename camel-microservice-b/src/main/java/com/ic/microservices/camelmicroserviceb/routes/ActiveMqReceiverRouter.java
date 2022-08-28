package com.ic.microservices.camelmicroserviceb.routes;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ic.microservices.camelmicroserviceb.CurrencyExchange;


@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

	@Autowired
	private MyCurrencyExchangeTransformer currencyExchangeTransformer;
	
	@Autowired
	private MyCurrencyExchangeProcessor currencyExchangeProcessor;

	@Override
	public void configure() throws Exception {

		// JSON
		// CurrencyExchange
		
		from("activemq:my-activemq-queue")
//		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//		.bean(currencyExchangeProcessor)
//		.bean(currencyExchangeTransformer)
		.unmarshal(createEncryptor())
		.to("log:received-message-from-active-mq");

//		from("activemq:my-activemq-xml-queue")
//		.unmarshal().jacksonxml(CurrencyExchange.class)
//		.bean(currencyExchangeProcessor)
//		.bean(currencyExchangeTransformer)
//		.to("log:received-message-from-active-mq");
		
//		from("activemq:split-queue")
//		.to("log:received-message-from-active-mq");

	}
	
	private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException {
		KeyStore keyStore = KeyStore.getInstance("JCEKS");
		ClassLoader classLoader = getClass().getClassLoader();
		keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
		Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

		CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
		return sharedKeyCrypto;
	}
}


@Component
class MyCurrencyExchangeProcessor {
	
	private Log logger = LogFactory.getLog(MyCurrencyExchangeTransformer.class);
	
	// implement some processing logic
	public void processMessage(CurrencyExchange currencyExchange) {
		logger.info("Currency exchange is: " + currencyExchange.getConversionMultiple());
	}
}


@Component
class MyCurrencyExchangeTransformer {
	
	// implement some transformation logic
	public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {
		BigDecimal newValue = currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN);
		currencyExchange.setConversionMultiple(newValue);
		return currencyExchange;
	}
}