package com.bridgelabz.fundooapi.configration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitmqConfig {
	
	@Autowired
	private ConnectionFactory rabbitConnectionFactory;

	/**
	 * Creates the object by taking exchange name, durability and auto delete option
	 * as input parameter.
	 * 
	 * @return {@link DirectExchange}
	 */
	@Bean
	public DirectExchange exchange() {
		return new DirectExchange("fundoo.rabbitmq.exchange", true, false);
	}

	/**
	 * Creates the object by taking exchange name, durability as input parameter.
	 * 
	 * @return {@link Queue}
	 */
	@Bean
	public Queue messageQueue() {
		return new Queue("fundoo.rabbitmq.queue", true);
	}

	@Bean
	public Binding exchangeBinding(DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with("fundoo.key");
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplet = new RabbitTemplate(rabbitConnectionFactory);
		rabbitTemplet.setConnectionFactory(rabbitConnectionFactory);
		rabbitTemplet.setExchange("fundoo.rabbitmq.queue");
		rabbitTemplet.setRoutingKey("fundoo.key");
		return rabbitTemplet;
	}

}
