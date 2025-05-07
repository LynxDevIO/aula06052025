package pucgo.poobd.aula06052025.model;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    private int id;
    private String nome;
    private float valor;

    public Produto() {
    }

    public Produto(int id, String nome, float valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
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

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Valor: R$" + valor;
    }
}
