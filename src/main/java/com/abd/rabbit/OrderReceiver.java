package com.abd.rabbit;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

/**
 *
 * @author : aaabdullah
 * @date : Apr 7, 2019
 */
@Component
public class OrderReceiver {

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		System.out.println("Order receiver received message <" + message + ">");
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}