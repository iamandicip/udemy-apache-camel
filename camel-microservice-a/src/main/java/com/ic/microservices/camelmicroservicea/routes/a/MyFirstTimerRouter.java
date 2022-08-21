package com.ic.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder{
	
	@Autowired 
	private GetCurrentTimeBean currentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;
	

	@Override
	public void configure() throws Exception {
		// timer
		// transformation
		// log
		// Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer") // create timer endpoint - could be a queue
		.log("${body}")
		.transform().constant("Time now is " + LocalDateTime.now()) // transformation
		.log("${body}")
		// Processing - does not change the body message
		// Transformation - changes the body message
		
		
		.bean(currentTimeBean, "getCurrentTime") // does transformation because it returns a value
		.log("${body}")
		.bean(loggingComponent) // does processing because it returns void
		.log("${body}")
		.process(new SimpleLoggingProcessor()) // processing
		.to("log:first-timer"); // timer and log are keywords - could be a database
	}

}


@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is " + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	
	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}

class SimpleLoggingProcessor implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);


	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
		
	}
	
}