package guru.springframework.sfgjms.sender;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloWorldSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
// Note: use @RequiredArgsConstructor to autowire final beans;
    // instead of explicitly creating the constructor
//    public HelloWorldSender(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorldMessage);

    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("I'm sending a message");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();
        final Message messageBack = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_OR_RECEIVE_QUEUE, new MessageCreator() {
            @SneakyThrows
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(helloWorldMessage));
                helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                System.out.println("Sending Hello");
                return helloMessage;
            }
        });

        System.out.println(messageBack.getBody(String.class));
    }
}
