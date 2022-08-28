package com.ic.microservices.camelmicroservicea.routes.patterns;

import java.util.ArrayList;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class ArrayListAggregationStrategy implements AggregationStrategy {

	// 1, 2, 3 -> sequence of messages incoming
	// null, 1 -> first call, these are the parameters, return result1
	// result1, 2 -> second call, return from previous call, and 2, return result2
	// result2, 3 -> third call, return from previous call, and 3
	
	@Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // null, 1 => [1]
		// [1], 2 => [1, 2]
		// [1, 2], 3 => [1, 2, 3]
		
		Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
    }
}