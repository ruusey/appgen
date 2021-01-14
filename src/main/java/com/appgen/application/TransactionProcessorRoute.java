package com.appgen.application;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import com.appgen.messaging.StoreTransactionReceiver;
import com.appgen.models.Transaction;

@Component
public class TransactionProcessorRoute extends SpringRouteBuilder{

	@Override
	public void configure() throws Exception {
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Transaction.class);

		from("direct:rest-json-input")
			.id("transaction-route")
			.marshal(jsonDataFormat)
			.to("rabbitmq:gpc-transactions?exchangeType=topic&autoDelete=false")
			.bean(StoreTransactionReceiver.class, "receiveMessage")
		.end();
		
	}

}
