package com.yuzhongyangwang.messagemqdemo;

import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class MessageMqDemoApplication {

	private static final String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()){

			 channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			 System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			 DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				 String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				 System.out.println(" [x] Received '" + message + "'");
			 };
			 channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		}
		SpringApplication.run(MessageMqDemoApplication.class, args);
	}

}
