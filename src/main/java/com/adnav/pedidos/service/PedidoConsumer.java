package com.adnav.pedidos.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = "fila_pedidos")
    public void processarPedido(String mensagem){
        System.out.println("Pedido recebido da fila: " + mensagem);
    }
}
