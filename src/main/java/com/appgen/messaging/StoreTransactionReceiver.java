package com.appgen.messaging;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreTransactionReceiver {
	private static final Logger LOG = LoggerFactory.getLogger(StoreTransactionReceiver.class);
	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		LOG.info("Received posted transaction: [{}]", message);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}