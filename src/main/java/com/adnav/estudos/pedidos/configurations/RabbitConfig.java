package com.adnav.estudos.pedidos.configurations;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitConfig {

//    @Bean
//    public Queue queue(){
//        return new Queue("fila_pedidos",true);
//    }


    /*
    *  DIRECT EXCHANGE
    *
    * */

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("pedido.direct");
    }
    @Bean
    public Queue filaPagamento() {
        return new Queue("fila_pagamento");
    }
    @Bean
    public Queue filaEntrega() {
        return new Queue("fila_entrega");
    }

    @Bean
    public Binding bindingPagamento(Queue filaPagamento, DirectExchange directExchange) {
        return BindingBuilder.bind(filaPagamento).to(directExchange).with("pagamento");
    }

    @Bean
    public Binding bindingEntrega(Queue filaEntrega, DirectExchange directExchange){
        return BindingBuilder.bind(filaEntrega).to(directExchange).with("entrega");
    }

    /*
     *  FANOUT EXCHANGE
     *
     * */

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("pedido.fanout");
    }

    @Bean
    public Queue filaFinanceiro() {
        return new Queue("fila_financeiro");
    }

    @Bean
    public Queue filaAuditoria(){
        return new Queue("fila_auditoria");
    }

    @Bean
    public Binding bindingFinanceiro(Queue filaFinanceiro, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(filaFinanceiro).to(fanoutExchange);
    }

    @Bean
    public Binding bindingAuditoria(Queue filaAuditoria, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(filaAuditoria).to(fanoutExchange);
    }

    /*
     *  TOPIC EXCHANGE
     *
     * */

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("pedido.topic");
    }

    @Bean
    public Queue filaPedidosNovos() {
        return new Queue("fila_pedidos_novos");
    }

    @Bean
    public Queue filaCancelamentos() {
        return new Queue("fila_pedidos_cancelados");
    }

    @Bean
    public Queue filaVIP() {
        return new Queue("fila_pedidos_vip");
    }

    // Bindings com padrões
    @Bean
    public Binding bindingNovos(Queue filaPedidosNovos, TopicExchange topicExchange) {
        return BindingBuilder.bind(filaPedidosNovos).to(topicExchange).with("pedido.novo");
    }

    @Bean
    public Binding bindingCancelados(Queue filaCancelamentos, TopicExchange topicExchange) {
        return BindingBuilder.bind(filaCancelamentos).to(topicExchange).with("pedido.cancelado.#");
    }

    @Bean
    public Binding bindingVIP(Queue filaVIP, TopicExchange topicExchange) {
        return BindingBuilder.bind(filaVIP).to(topicExchange).with("pedido.*.vip");
    }

}
