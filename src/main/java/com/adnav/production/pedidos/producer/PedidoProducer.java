package com.adnav.production.pedidos.producer;

import com.adnav.production.pedidos.model.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(Pedido pedido) {
        String mensagem = gerarMensagem(pedido);

        switch(pedido.getTipo()){
            case NOVO:
                rabbitTemplate.convertAndSend("pedido.topic", "pedido.novo", mensagem);
                break;
            case CANCELADO:
                rabbitTemplate.convertAndSend("pedido.topic", pedido.isVip() ? "pedido.cancelado.vip" : "pedido.cancelado", mensagem);
                break;
            case URGENTE:
                // broadcast via fanout
                rabbitTemplate.convertAndSend("pedido.fanout", "", mensagem);
                break;
            case DEVOLUCAO:
                rabbitTemplate.convertAndSend("pedido.direct", "pagamento", mensagem);
                break;
            default:
                System.out.println("Tipo de pedido desconhecido: " + pedido.getTipo());
        }

        // Extra: se for VIP manda um extra para exchange VIP
        if(pedido.isVip()){
            rabbitTemplate.convertAndSend("pedido.direct", "vip", mensagem);
        }

    }

    private String gerarMensagem(Pedido pedido) {
        return "[Pedido] Cliente: " + pedido.getCliente() +
                " | Tipo: " + pedido.getTipo() +
                " | VIP: " + (pedido.isVip() ? "Sim" : "Não") +
                " | " + pedido.getDescricao();
    }
}
