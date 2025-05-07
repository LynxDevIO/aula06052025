package pucgo.poobd.aula06052025.model;

import java.util.List;

public class Pedido {
    private int id;
    private List<ItemPedido> itens;
    private String observacao;
    private float valorTotal;

    public Pedido() {
    }

    public Pedido(int id, List<ItemPedido> itens, String observacao, float valorTotal) {
        this.id = id;
        this.itens = itens;
        this.observacao = observacao;
        this.valorTotal = valorTotal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
