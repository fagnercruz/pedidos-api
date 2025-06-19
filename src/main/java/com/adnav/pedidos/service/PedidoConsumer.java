package com.adnav.pedidos.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = "fila_pagamento")
    public void processarPagamento(String mensagem){
        System.out.println("[PAGAMENTO] " + mensagem);
    }

    @RabbitListener(queues = "fila_entrega")
    public void processarEntrega(String mensagem){
        System.out.println("[ENTREGA] " + mensagem);
    }

    @RabbitListener(queues = "fila_financeiro")
    public void receberFinanceiro(String mensagem){
        System.out.println("[FINANCEIRO] " + mensagem);
    }

    @RabbitListener(queues = "fila_auditoria")
    public void receberAuditoria(String mensagem){
        System.out.println("[AUDITORIA] " + mensagem);
    }
}
