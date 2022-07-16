package com.cybersoft.demorabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybersoft.demorabbitmq.config.RabbitMQConfig;

@RestController
@RequestMapping("/home")
public class DemoController {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@GetMapping("")
	public void sendQueue() {
		rabbitTemplate.convertAndSend(RabbitMQConfig.ROUTING_KEY, "Hello Queue I love you");
	}
}
