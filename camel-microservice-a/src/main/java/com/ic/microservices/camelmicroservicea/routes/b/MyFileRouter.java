package com.ic.microservices.camelmicroservicea.routes.b;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFileRouter extends RouteBuilder {
	
	@Autowired
	private DeciderBean deciderBean;

	@Override
	public void configure() throws Exception {
		
		// Pipeline pattern
		
		from("file:files/input")
		.pipeline() // pipeline is the default pattern, not needed
		.routeId("Files-Input-Route")
		.transform()
		.body(String.class)
		.choice()
			.when(simple("${file:ext} == 'xml'"))
				.log("XML file")
			.when(method(deciderBean))
//			.when(simple("${body} contains 'USD'"))
				.log("Not an XML file but contains 'USD'")
			.otherwise()
				.log("Not an XML file")
		.end()
//		.to("direct://log-file-values")
		.to("file:files/output");
		
		// reusable route
		from("direct:log-file-values")
		.log("${messageHistory} ${file:absolute.path}")
		.log("${file:name} ${file:name.ext}")
		.log("${routeId} ${camelId} ${body}");
	}

}

@Component
class DeciderBean {
	Logger logger = LoggerFactory.getLogger(DeciderBean.class);
	public boolean isConditionMet(
			@Body String body,
			@Headers Map<String, String> headers,
			@ExchangeProperties Map<String, String> exchangeProperties
			) {
		logger.info("DeciderBean {} {} {}", body, headers, exchangeProperties);
		return true;
	}
}
