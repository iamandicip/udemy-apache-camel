package com.ic.microservices.camelmicroservicea.routes.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class EipPatternsRouter extends RouteBuilder {

	@Autowired
	private SplitterComponent splitterComponent;

	@Autowired
	private DynamicRouterBean dynamicRouterBean;

	@Override
	public void configure() throws Exception {
		// Pipeline - the default - pipeline()
		// Content based routing - choice()
		// Multicast - send the same message to multiple endpoints - multicast()
		// Splitter - split a single message into multiple messages - split()
		// Aggregate - combine multiple messages into a single message - aggregate()

		// log more information for debugging purposes
		getContext().setTracing(true);
		
		// whenever a message cannot be processed, save it somewhere so it doesn´t get lost
		errorHandler(deadLetterChannel("activemq:dead-letter-queue"));
		
//		from("timer:multicast?period=10000")
//		.multicast()
//		.to("log:something1", "log:something2");

//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
//		.to("activemq:split-queue");

		// message1,message2,message3
		from("file:files/csv").convertBodyTo(String.class)
//		.split(body(), ",")
				.split(method(splitterComponent)).to("activemq:split-queue");

		// Aggregation
		// Messages => Aggregate => Endpoint
		from("file:files/aggregate-json").unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
				.aggregate(simple("${body.to}"), new ArrayListAggregationStrategy()).completionSize(3)
//		.completionTimeout(HIGHEST)
				.to("log:aggregate-json");

		// routing slip

		String routingSlip = "direct:endpoint1,direct:endpoint2";
		


		from("direct:endpoint1")
		.wireTap("log:wireTap") // additional endpoint where the current message can be sent
		.to("{{endpoint-for-logging}}");
		
		from("timer:routingSlip?period=10000")
		.transform().constant("Hardcoded message")
		.routingSlip(simple(routingSlip));
		


		// Dynamic routing after each step, we decide which endpoints to execute
		// Step 1, Step 2, Step 3

//		from("timer:dynamicRoute?period={{timePeriod}}")
//		.transform().constant("HardcodedMessage")
//		.dynamicRouter(method(dynamicRouterBean));
		// Endpoint1
		// Endpoint2
		// Endpoint3
		
//		from("direct:endpoint1")
//		.to("{{endpoint-for-logging}}"); // load value from properties file

		from("direct:endpoint2")
		.to("log:directendpoint2");

		from("direct:endpoint3")
		.to("log:directendpoint3");

	}

}

@Component
class SplitterComponent {
	public List<String> splitInput(String body) {
		return List.of("ABC", "DEF", "GHI");
	}
}

@Component
class DynamicRouterBean {

	Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);

	
	public String decideTheNextEndpoint(@ExchangeProperties Map<String, Object> properties,
			@Headers Map<String, String> headers, @Body String body) {
		logger.info("{} {} {}", properties, headers, body);
		
		int invocations = ((Long)properties.get("CamelTimerCounter")).intValue();

		switch (invocations % 3) {
		case 0:
			return "direct:endpoint1";
		case 1:
			return "direct:endpoint2,direct:endpoint3";
		default:
			return "direct:endpoint2";
		}

	}
}
