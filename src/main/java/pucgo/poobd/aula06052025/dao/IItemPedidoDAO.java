package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.model.ItemPedido;

import java.util.List;
import java.util.Optional;

public interface IItemPedidoDAO {
    void criarTabela();
    void inserir(ItemPedido itemPedido);
    void alterarPorID(int id, ItemPedido itemPedido);
    void excluirPorID(int id);
    Optional<ItemPedido> buscarPorID(int id);
    List<ItemPedido> buscarTodos();
    List<ItemPedido> buscarPorIDPedido(int idPedido);
}
