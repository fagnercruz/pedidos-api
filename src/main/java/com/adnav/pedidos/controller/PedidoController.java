package com.adnav.pedidos.controller;

import com.adnav.pedidos.model.Pedido;
import com.adnav.pedidos.service.PedidoProducer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final List<Pedido> pedidos = new ArrayList<>();

    // Producer do RabbitMQ
    private final PedidoProducer pedidoProducer;

    // Construtor
    public PedidoController(PedidoProducer pedidoProducer) {
        this.pedidoProducer = pedidoProducer;
    }

    @PostMapping("/{tipo}")
    public Pedido criarPedido(@RequestBody Pedido pedido, @PathVariable String tipo) {
        pedido.setId((long) (pedidos.size() + 1));
        pedidos.add(pedido);

        //Enviando msg à fila
        pedidoProducer.enviarPedido("Pedido criado: " + pedido.getDescricao(),tipo);

        return pedido;
    }

    @PostMapping("/broadcast")
    public String enviarBroadcast(@RequestBody String mensagem) {
        pedidoProducer.broadcastPedido(mensagem);
        return "Broadcast enviado com sucesso";
    }

    @PostMapping("/topico")
    public String enviarTopico(@RequestParam String mensagem, @RequestParam String routingKey) {
        pedidoProducer.enviarComRoutingKey(mensagem, routingKey);
        return "Mensagem enviada com routing key: " + routingKey;
    }


    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}
