package pucgo.poobd.aula06052025.model;

public class Produto {
    private int id;
    private String nome;
    private float valor;
    private int estoque;

    public Produto() {
    }

    public Produto(int id, String nome, float valor, int estoque) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.estoque = estoque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Valor: R$ %.2f".formatted(valor);
    }
}
