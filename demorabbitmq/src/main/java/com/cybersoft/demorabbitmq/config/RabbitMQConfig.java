package com.cybersoft.demorabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cybersoft.demorabbitmq.message.Receiver;

@Configuration
public class RabbitMQConfig {
	public static String QUEUE_NAME = "demo_queue";
	public static String EXCHANGE_NAME = "demo_exchange_queue";
	public static String ROUTING_KEY = "route_demo";
	
	// Tạo ra Queue
	@Bean
	Queue queue () {
		return new Queue(RabbitMQConfig.QUEUE_NAME, false);
		
	}
	
	// Tạo ra Exchange
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(RabbitMQConfig.EXCHANGE_NAME);
	}
	
	// Liên kết Exchange với Queue và định nghĩa Route cho Exchange
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(RabbitMQConfig.ROUTING_KEY);
	}
	
	// Tạo ra con consumer lấy dữ liệu từ Queue
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, 
			MessageListenerAdapter messageListenerAdapter) {
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(RabbitMQConfig.QUEUE_NAME);
		
		return container;
	}
	
	@Bean
	MessageListenerAdapter messageListenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
