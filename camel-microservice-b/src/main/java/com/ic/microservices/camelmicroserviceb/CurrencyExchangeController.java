package com.ic.microservices.camelmicroserviceb;
import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	@GetMapping("/currencyExchange/from/{from}/to/{to}")
	@ResponseBody
	public CurrencyExchange findConversionValue(
			@PathVariable String from, 
			@PathVariable String to) {
		return new CurrencyExchange(1001L, from, to, BigDecimal.TEN);
	}
}
