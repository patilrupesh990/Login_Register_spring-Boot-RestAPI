package com.bridgelabz.fundooapi.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundooapi.response.MailResponse;

@Component
public class RabbitMQUtil {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("fundoo.rabbitmq.exchange")
	private String exchange;

	@Value("fundoo.key")
	private String routingkey;

	public boolean send(MailResponse response) {
		rabbitTemplate.convertAndSend(exchange, routingkey, response);
		return true;
	}
}


