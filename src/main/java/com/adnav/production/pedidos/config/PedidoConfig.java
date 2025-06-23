package com.adnav.production.pedidos.config;

import org.springframework.amqp.core.*;
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

    // Filas
    @Bean
    public Queue filaCancelamentos() {
        return new Queue("fila_pedidos_cancelados");
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
}

