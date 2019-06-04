package com.worldmessages.messagemanager.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${message.directexchange.name}")
    private String directExchangeName;

    @Value("${message.fanoutexchange.name}")
    private String fanoutExchangeName;

    @Value("${message.topicexchange.name}")
    private String topicExchangeName;

    @Value("${message.spain.queue.name}")
    private String spainQueueName;

    @Value("${message.eu.queue.name}")
    private String euuQueue;

    @Value("${message.italy.queue.name}")
    private String italyQueueName;

    @Value("${message.japan.queue.name}")
    private String japanQueueName;

    @Value("${message.eu.bindingKey.directExchange}")
    private String euBindingKeyDirectExchange;

    @Value("${message.eu.bindingKey.topicExchange}")
    private String euBindingKeyTopicExchange;

    @Value("${message.spain.bindingKey}")
    private String spainBindingKey;

    @Value("${message.italy.bindingKey}")
    private String italyBindingKey;

    @Value("${message.japan.bindingKey}")
    private String japanBindingKey;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchangeName);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean("spainQueue")
    public Queue spainQueue() {
        return new Queue(spainQueueName);
    }

    @Bean("italyQueue")
    public Queue italyQueue() {
        return new Queue(italyQueueName);
    }

    @Bean("japanQueue")
    public Queue japanQueue() {
        return new Queue(japanQueueName);
    }

    @Bean("euQueue")
    public Queue europeanUnionQueue() {
        return new Queue(euuQueue);
    }

    @Bean
    public Binding bindTopicExchangeSpain(TopicExchange exchange, @Qualifier("spainQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(spainBindingKey);
    }

    @Bean
    public Binding bindTopicExchangeItaly(TopicExchange exchange, @Qualifier("italyQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(italyBindingKey);
    }

    @Bean
    public Binding bindTopicExchangeEuropeanUnion(TopicExchange exchange, @Qualifier("euQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(euBindingKeyTopicExchange);
    }

    @Bean
    public Binding bindDirectExchangeJapan(DirectExchange exchange, @Qualifier("japanQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(japanBindingKey);
    }

    @Bean
    public Binding bindDirectExchangeEuropeanUnion(DirectExchange exchange, @Qualifier("euQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(euBindingKeyDirectExchange);
    }

    @Bean
    public Binding bindFanoutExchangeSpain(FanoutExchange exchange, @Qualifier("spainQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFanoutExchangeItaly(FanoutExchange exchange, @Qualifier("italyQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFanoutExchangeJapan(FanoutExchange exchange, @Qualifier("japanQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFanoutExchangeEuropeanUnion(FanoutExchange exchange, @Qualifier("euQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
