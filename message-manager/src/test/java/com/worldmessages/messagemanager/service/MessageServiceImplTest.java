package com.worldmessages.messagemanager.service;

import com.worldmessages.messagemanager.client.MessageEngineClient;
import com.worldmessages.messagemanager.client.dto.NewMessageInDTO;
import com.worldmessages.messagemanager.client.dto.NewMessageOutDTO;
import com.worldmessages.messagemanager.domain.ReceiverZone;
import com.worldmessages.messagemanager.payload.SendMessageRequest;
import com.worldmessages.messagemanager.service.impl.MessageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    private static final String MESSAGE = "MESSAGE";
    private static final String EXCHANGE_NAME = "exchangeName";
    private static final String EXPECTED_EU_ROUTING_KEY = "eu.union";
    private static final String EXPECTED_SPAIN_ROUTING_KEY = "eu.es";
    private static final String EXPECTED_ITALY_ROUTING_KEY = "eu.it";
    private static final String EXPECTED_JAPAN_ROUTING_KEY = "nounion.jp";
    private static final String EXPECTED_ALL_ROUTING_KEY = "";

    @Mock
    private DirectExchange directExchange;

    @Mock
    private TopicExchange topicExchange;

    @Mock
    private FanoutExchange fanoutExchange;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private MessageEngineClient messageEngineClient;

    @Captor
    private ArgumentCaptor<String> rabbitTemplateExchangeCaptor;

    @Captor
    private ArgumentCaptor<String> rabbitTemplateMessageCaptor;

    @Captor
    private ArgumentCaptor<String> rabbitTemplateRoutingKeyCaptor;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Before
    public void before() {
        when(messageEngineClient.createMessage(any(NewMessageInDTO.class))).thenReturn(new NewMessageOutDTO());
    }

    @Test
    public void should_send_message_to_eu_exchange() {
        testRabbitTemplateCall(directExchange, new SendMessageRequest(MESSAGE, ReceiverZone.EUROPEAN_UNION), EXPECTED_EU_ROUTING_KEY);
    }

    @Test
    public void should_send_message_to_spain_exchange() {
        testRabbitTemplateCall(topicExchange, new SendMessageRequest(MESSAGE, ReceiverZone.SPAIN), EXPECTED_SPAIN_ROUTING_KEY);
    }

    @Test
    public void should_send_message_to_italy_exchange() {
        testRabbitTemplateCall(topicExchange, new SendMessageRequest(MESSAGE, ReceiverZone.ITALY), EXPECTED_ITALY_ROUTING_KEY);
    }

    @Test
    public void should_send_message_to_japan_exchange() {
        testRabbitTemplateCall(directExchange, new SendMessageRequest(MESSAGE, ReceiverZone.JAPAN), EXPECTED_JAPAN_ROUTING_KEY);
    }

    @Test
    public void should_send_message_to_all_exchange() {
        testRabbitTemplateCall(fanoutExchange, new SendMessageRequest(MESSAGE, ReceiverZone.WORLD), EXPECTED_ALL_ROUTING_KEY);
    }

    private void testRabbitTemplateCall(final AbstractExchange abstractExchange, final SendMessageRequest sendMessageRequest, final String expectedRoutingKey) {
        //GIVEN
        when(abstractExchange.getName()).thenReturn(EXCHANGE_NAME);

        // WHEN
        messageService.sendMessage(sendMessageRequest);

        // THEN
        verify(rabbitTemplate).convertAndSend(rabbitTemplateExchangeCaptor.capture(), rabbitTemplateRoutingKeyCaptor.capture(), rabbitTemplateMessageCaptor.capture());
        assertEquals(EXCHANGE_NAME, rabbitTemplateExchangeCaptor.getValue());
        assertEquals(expectedRoutingKey, rabbitTemplateRoutingKeyCaptor.getValue());
        assertEquals(sendMessageRequest.getBody(), rabbitTemplateMessageCaptor.getValue());
    }

}
