package guru.springframework.sfgjms.receiver;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloWorldReceiver {
    private final JmsTemplate jmsTemplate;


    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message){
//        System.out.println("I got a new Message");
//        System.out.println(helloWorldMessage);

    }
    @JmsListener(destination = JmsConfig.MY_SEND_OR_RECEIVE_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       javax.jms.Message message) throws JMSException {
        HelloWorldMessage worldMessage = HelloWorldMessage.builder().id(UUID.randomUUID()).message("World!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), worldMessage);

        System.out.println("I got a new Hello Message");
        System.out.println(helloWorldMessage);

    }

}
