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

    //Variável para armazenar um producer do RabbitMQ
    private final PedidoProducer pedidoProducer;

    //Injetando o producer via construtor (alternativa ao @autowired)
    public PedidoController(PedidoProducer pedidoProducer) {
        this.pedidoProducer = pedidoProducer;
    }

    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido) {
        pedido.setId((long) (pedidos.size() + 1));
        pedidos.add(pedido);

        //Enviando msg à fila_pedidos
        pedidoProducer.enviarPedido("Novo pedido criado: " + pedido.getDescricao());

        return pedido;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}
