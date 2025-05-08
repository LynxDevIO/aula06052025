package pucgo.poobd.aula06052025.dao;

import pucgo.poobd.aula06052025.model.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAO implements IProdutoDAO {
    private final Connection conexao;

    public ProdutoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS produtos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "valor NUMERIC NOT NULL CHECK (valor = ROUND(valor, 2)), " +
                "estoque INTEGER NOT NULL DEFAULT 0" +
                ")";
        try (Statement st = conexao.createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, valor, estoque) VALUES (?, ?, ?)";
        BigDecimal valorArredondado = new BigDecimal(String.valueOf(produto.getValor()))
                .setScale(2, RoundingMode.HALF_UP);

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setBigDecimal(2, valorArredondado);
            ps.setInt(3, produto.getEstoque());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void alterarPorID(int id, Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, valor = ?, SET estoque = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getValor());
            ps.setInt(3, produto.getEstoque());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void alterarEstoquePorID(int id, int estoqueNovo) {
        String sql = "UPDATE produtos SET estoque = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, estoqueNovo);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluirPorID(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Produto> buscarPorID(int id) {
        Optional<Produto> produto = Optional.empty();
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getFloat("valor"));
                p.setEstoque(rs.getInt("estoque"));
                produto = Optional.of(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    @Override
    public List<Produto> buscarTodos() {
        String sql = "SELECT * FROM produtos";
        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getFloat("valor"));
                p.setEstoque(rs.getInt("estoque"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os produtos. Mensagem: " + e.getMessage());
        }

        return produtos;
    }
}
