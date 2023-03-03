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
			 Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World2!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
			System.out.println(" [x] Sent '" + message + "'");
		}
		SpringApplication.run(MessageMqDemoApplication.class, args);
	}

}
