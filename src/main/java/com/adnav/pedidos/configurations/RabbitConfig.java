package com.adnav.pedidos.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

//    @Bean
//    public Queue queue(){
//        return new Queue("fila_pedidos",true);
//    }


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

}
