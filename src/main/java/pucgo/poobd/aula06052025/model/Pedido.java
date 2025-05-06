package pucgo.poobd.aula06052025.model;

import java.util.Map;

public class Pedido {
    private int id;
    private Map<Integer, Produto> itens;
    private float valorTotal;

    public Pedido() {
    }

    public Pedido(int id, Map<Integer, Produto> itens) {
        this.id = id;
        this.itens = itens;
        this.valorTotal = (float) itens.entrySet().stream().mapToDouble(
                custo -> custo.getKey() * custo.getValue().getValor()
        ).sum();
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Produto> getItens() {
        return itens;
    }

    public void setItens(Map<Integer, Produto> itens) {
        this.itens = itens;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
