package com.forezp.rabbtimq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author gongxb
 * @date 2018/9/9
 * @desc
 * @return
 */
@Component
public class RabbitMqConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }
    @Bean
    public Queue DMessage() {
        return new Queue("topic.D");
    }

    @Bean
    public Queue EMessage() {
        return new Queue("topic.E");
    }



    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingTopicExchange(Queue DMessage, TopicExchange topicExchange){
        return BindingBuilder.bind(DMessage).to(topicExchange).with("topic.#");
    }
    @Bean
    Binding bindingTopicExchange2(Queue EMessage, TopicExchange topicExchange){
        return BindingBuilder.bind(EMessage).to(topicExchange).with("topic.message");
    }


    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }
}
