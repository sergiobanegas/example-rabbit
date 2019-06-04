package com.worldmessages.messagemanager.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RabbitConfigTest {

    private static final String DIRECT_EXCHANGE_NAME = "DIRECT_EXCHANGE_NAME";
    private static final String TOPIC_EXCHANGE_NAME = "TOPIC_EXCHANGE_NAME";
    private static final String FANOUT_EXCHANGE_NAME = "FANOUT_EXCHANGE_NAME";
    private static final String EXPECTED_EU_DIRECT_BINDING_KEY = "eu.union";
    private static final String EXPECTED_EU_TOPIC_BINDING_KEY = "eu.*";
    private static final String EXPECTED_FANOUT_BINDING_KEY = "";
    private static final String EXPECTED_JAPAN_BINDING_KEY = "nounion.jp";
    private static final String EXPECTED_SPAIN_BINDING_KEY = "eu.es";
    private static final String EXPECTED_ITALY_BINDING_KEY = "eu.it";
    private static final String QUEUE_NAME = "QUEUE_NAME";
    private static final String EU_BINDING_KEY_DIRECT_EXCHANGE_PROPERTY = "euBindingKeyDirectExchange";
    private static final String JAPAN_BINDING_KEY_PROPERTY = "japanBindingKey";
    private static final String SPAIN_BINDING_KEY_PROPERTY = "spainBindingKey";
    private static final String ITALY_BINDING_KEY_PROPERTY = "italyBindingKey";
    private static final String EU_BINDING_KEY_TOPIC_EXCHANGE_PROPERTY = "euBindingKeyTopicExchange";

    @InjectMocks
    private RabbitConfig underTest;

    @Mock
    private DirectExchange directExchange;

    @Mock
    private TopicExchange topicExchange;

    @Mock
    private FanoutExchange fanoutExchange;

    @Test
    public void should_bind_eu_to_direct_exchange() {
        // GIVEN
        mockDirectExchange();
        ReflectionTestUtils.setField(underTest, EU_BINDING_KEY_DIRECT_EXCHANGE_PROPERTY, EXPECTED_EU_DIRECT_BINDING_KEY);

        // WHEN
        final Binding binding = underTest.bindDirectExchangeEuropeanUnion(directExchange, buildQueue());

        // THEN
        testBinding(binding, EXPECTED_EU_DIRECT_BINDING_KEY, directExchange);
    }

    @Test
    public void should_bind_japan_to_direct_exchange() {
        // GIVEN
        mockDirectExchange();
        ReflectionTestUtils.setField(underTest, JAPAN_BINDING_KEY_PROPERTY, EXPECTED_JAPAN_BINDING_KEY);

        // WHEN
        final Binding binding = underTest.bindDirectExchangeJapan(directExchange, buildQueue());

        //THEN
        testBinding(binding, EXPECTED_JAPAN_BINDING_KEY, directExchange);
    }

    @Test
    public void should_bind_spain_to_topic_exchange() {
        // GIVEN
        mockTopicExchange();
        ReflectionTestUtils.setField(underTest, SPAIN_BINDING_KEY_PROPERTY, EXPECTED_SPAIN_BINDING_KEY);

        // WHEN
        final Binding binding = underTest.bindTopicExchangeSpain(topicExchange, buildQueue());

        // THEN
        testBinding(binding, EXPECTED_SPAIN_BINDING_KEY, topicExchange);
    }

    @Test
    public void should_bind_italy_to_topic_exchange() {
        // GIVEN
        mockTopicExchange();
        ReflectionTestUtils.setField(underTest, ITALY_BINDING_KEY_PROPERTY, EXPECTED_ITALY_BINDING_KEY);
        // WHEN
        final Binding binding = underTest.bindTopicExchangeItaly(topicExchange, buildQueue());

        //THEN
        testBinding(binding, EXPECTED_ITALY_BINDING_KEY, topicExchange);
    }

    @Test
    public void should_bind_EU_to_topic_exchange() {
        // GIVEN
        mockTopicExchange();
        ReflectionTestUtils.setField(underTest, EU_BINDING_KEY_TOPIC_EXCHANGE_PROPERTY, EXPECTED_EU_TOPIC_BINDING_KEY);

        // WHEN
        final Binding binding = underTest.bindTopicExchangeEuropeanUnion(topicExchange, buildQueue());

        // THEN
        testBinding(binding, EXPECTED_EU_TOPIC_BINDING_KEY, topicExchange);
    }

    @Test
    public void should_bind_EU_to_fanout_exchange() {
        // GIVEN
        mockFanoutExchange();

        // WHEN
        final Binding binding = underTest.bindFanoutExchangeEuropeanUnion(fanoutExchange, buildQueue());

        // THEN
        testBinding(binding, EXPECTED_FANOUT_BINDING_KEY, fanoutExchange);
    }

    @Test
    public void should_bind_spain_to_fanout_exchange() {
        // GIVEN
        mockFanoutExchange();

        // WHEN
        final Binding binding = underTest.bindFanoutExchangeSpain(fanoutExchange, buildQueue());
        testBinding(binding, EXPECTED_FANOUT_BINDING_KEY, fanoutExchange);
    }

    @Test
    public void should_bind_italy_to_fanout_exchange() {
        // GIVEN
        mockFanoutExchange();

        // WHEN
        final Binding binding = underTest.bindFanoutExchangeItaly(fanoutExchange, buildQueue());

        // THEN
        testBinding(binding, EXPECTED_FANOUT_BINDING_KEY, fanoutExchange);
    }

    @Test
    public void should_bind_japan_to_fanout_exchange() {
        // GIVEN
        mockFanoutExchange();

        // WHEN
        final Binding binding = underTest.bindFanoutExchangeJapan(fanoutExchange, buildQueue());

        // THEN
        testBinding(binding, EXPECTED_FANOUT_BINDING_KEY, fanoutExchange);
    }

    private void mockDirectExchange() {
        when(directExchange.getName()).thenReturn(DIRECT_EXCHANGE_NAME);
    }

    private void mockTopicExchange() {
        when(topicExchange.getName()).thenReturn(TOPIC_EXCHANGE_NAME);
    }

    private void mockFanoutExchange() {
        when(fanoutExchange.getName()).thenReturn(FANOUT_EXCHANGE_NAME);
    }

    private Queue buildQueue() {
        return new Queue(QUEUE_NAME);
    }

    private void testBinding(final Binding binding, final String expectedBindingKey, final AbstractExchange expectedExchange) {
        assertEquals(expectedBindingKey, binding.getRoutingKey());
        assertEquals(QUEUE_NAME, binding.getDestination());
        assertEquals(expectedExchange.getName(), binding.getExchange());
    }

}
