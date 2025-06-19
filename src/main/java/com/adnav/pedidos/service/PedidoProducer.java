package com.adnav.pedidos.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    // Construtor
    public PedidoProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(String mensagem, String tipo){
        rabbitTemplate.convertAndSend("pedido.direct",tipo,mensagem);
    }

}
