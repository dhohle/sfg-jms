package guru.springframework.sfgjms.sender;


import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloWorldSender {

    private final JmsTemplate jmsTemplate;
// Note: use @RequiredArgsConstructor to autowire final beans;
    // instead of explicitly creating the constructor
//    public HelloWorldSender(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        System.out.println("I'm sending a message");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorldMessage);

        System.out.println("Message sent");
    }
}
