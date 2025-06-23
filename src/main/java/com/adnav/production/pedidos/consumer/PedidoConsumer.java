package com.adnav.production.pedidos.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = "fila_pedidos_cancelados")
    public void processarCancelamento(String mensagem) {
        System.out.println("❌ [Cancelamento] " + mensagem);
    }

    @RabbitListener(queues = "fila_pagamento")
    public void processarFinanceiro(String mensagem) {
        System.out.println("💰 [Financeiro] " + mensagem);
    }

    @RabbitListener(queues = "fila_pedidos_vip")
    public void processarVIP(String mensagem) {
        System.out.println("💎 [VIP] " + mensagem);
    }

    @RabbitListener(queues = "fila_urgentes")
    public void processarUrgentes(String mensagem) {
        System.out.println("🚨 [URGENTE] " + mensagem);
    }
}

