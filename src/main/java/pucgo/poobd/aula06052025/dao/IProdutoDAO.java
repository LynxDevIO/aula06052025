package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.model.Produto;

import java.util.List;
import java.util.Optional;

public interface IProdutoDAO {
    void criarTabela();
    void inserir(Produto produto);
    void alterarPorID(int id, Produto produto);
    void alterarEstoquePorID(int id, int estoqueNovo);
    void excluirPorID(int id);
    Optional<Produto> buscarPorID(int id);
    List<Produto> buscarTodos();
}
