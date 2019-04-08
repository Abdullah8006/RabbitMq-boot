package com.abd.rabbit;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author : aaabdullah
 * @date : Apr 7, 2019
 */
@Component
public class Runner implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(Runner.class);
	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("Sending message...");
		for (int i = 0; i < 100; i++) {
			rabbitTemplate.convertAndSend(Application.fanoutExchangeName, null, "Fanout exchange seems beautiful.");
			Thread.sleep(2000);
		}
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}

}
