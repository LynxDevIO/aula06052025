package pucgo.poobd.aula06052025.model;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
    private int id;
    private List<ItemPedido> itens;
    private String observacao;
    private float valorTotal;
    private LocalDate dataPedido;
    private boolean status; // TRUE para ativo, FALSE para inativo

    public Pedido() {
    }

    public Pedido(int id, List<ItemPedido> itens, String observacao, float valorTotal, LocalDate dataPedido, boolean status) {
        this.id = id;
        this.itens = itens;
        this.observacao = observacao;
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.status = status;
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

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
