package com.abd.rabbit;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author : aaabdullah
 * @date : Apr 7, 2019
 */
@Component
public class Receiver {

	private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);
	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		LOG.info("Received <{}>", message);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}