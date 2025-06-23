package com.adnav.production.pedidos.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Pedido {

    private Long id;
    private String cliente;
    private TipoPedido tipo;
    private boolean vip;
    private String descricao;

    public Pedido() {
    }

    public Pedido(Long id, String cliente, TipoPedido tipo, boolean vip,  String descricao) {
        this.id = id;
        this.cliente = cliente;
        this.tipo = tipo;
        this.vip = vip;
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return vip == pedido.vip && Objects.equals(id, pedido.id) && Objects.equals(cliente, pedido.cliente) && Objects.equals(tipo, pedido.tipo) && Objects.equals(descricao, pedido.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, tipo, vip, descricao);
    }
}
