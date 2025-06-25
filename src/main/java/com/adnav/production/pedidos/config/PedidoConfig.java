package com.adnav.production.pedidos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfig {

    // Exchanges
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("pedido.topic");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("pedido.fanout");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("pedido.direct");
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("pedido.dlq");
    }

    // Filas
    @Bean
    public Queue filaCancelamentos() {
        return QueueBuilder.durable("fila_pedidos_cancelados")
                .withArgument("x-dead-letter-exchange", "pedido.dlq")
                .withArgument("x-dead-letter-routing-key", "pedido.cancelado.dlq")
                .build();
    }

    @Bean
    public Queue filaCancelamentosDLQ() {
        return new  Queue("fila_pedidos_cancelados_dlq");
    }

    @Bean
    public Queue filaPagamento() {
        return new Queue("fila_pagamento");
    }

    @Bean
    public Queue filaVIP() {
        return new Queue("fila_pedidos_vip");
    }

    @Bean
    public Queue filaUrgentes() {
        return new Queue("fila_urgentes");
    }

    // Bindings — Topic
    @Bean
    public Binding bindingCancelados(Queue filaCancelamentos, TopicExchange topicExchange) {
        return BindingBuilder.bind(filaCancelamentos).to(topicExchange).with("pedido.cancelado.#");
    }

    @Bean
    public Binding bindingVIPTopic(Queue filaVIP, TopicExchange topicExchange) {
        return BindingBuilder.bind(filaVIP).to(topicExchange).with("pedido.*.vip");
    }

    // Bindings — Direct
    @Bean
    public Binding bindingPagamento(Queue filaPagamento, DirectExchange directExchange) {
        return BindingBuilder.bind(filaPagamento).to(directExchange).with("pagamento");
    }

    @Bean
    public Binding bindingVIPDirect(Queue filaVIP, DirectExchange directExchange) {
        return BindingBuilder.bind(filaVIP).to(directExchange).with("vip");
    }

    // Binding — Fanout
    @Bean
    public Binding bindingUrgente(FanoutExchange fanoutExchange, Queue filaUrgentes) {
        return BindingBuilder.bind(filaUrgentes).to(fanoutExchange);
    }

    // Binding - DLQ
    @Bean
    public Binding bindingDLQ(Queue filaCancelamentosDLQ, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(filaCancelamentosDLQ)
                .to(deadLetterExchange)
                .with("pedido.cancelado.dlq");
    }

    //Ajuste para corriguir o loop causado pelo runtimeexception
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        factory.setDefaultRequeueRejected(false); // <- ESSENCIAL: não reencaminha a msg à fila!
        return factory;
    }

}

