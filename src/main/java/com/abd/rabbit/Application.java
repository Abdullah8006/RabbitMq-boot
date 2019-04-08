package com.abd.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author : aaabdullah
 * @date : Apr 7, 2019
 */
@SpringBootApplication
public class Application {

	static final String topicExchangeName = "spring-boot-exchange";
	static final String fanoutExchangeName = "spring-boot-exchange-fanout";

	static final String queueName = "spring-boot";
	static final String queueName2 = "spring-boot-2";

	@Bean
	Queue queueOne() {
		return new Queue(queueName, true);
	}

	@Bean
	Queue queueTwo() {
		return new Queue(queueName2, true);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(@Qualifier("queueOne") Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}

	@Bean
	FanoutExchange exchangeFanout() {
		return new FanoutExchange(fanoutExchangeName);
	}

	@Bean
	Binding bindingFanoutQueue1(@Qualifier("queueOne") Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

	@Bean
	Binding bindingFanoutQueue2(@Qualifier("queueTwo") Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName, queueName2);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args).close();
	}

}
