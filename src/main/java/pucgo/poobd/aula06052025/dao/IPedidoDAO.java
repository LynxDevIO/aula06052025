package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface IPedidoDAO {
    void criarTabela();
    int inserir(Pedido pedido);
    void alterarPorID(int id, Pedido pedido);
    void excluirPorID(int id);
    Optional<Pedido> buscarPorID(int id);
    List<Pedido> buscarTodos();
}
