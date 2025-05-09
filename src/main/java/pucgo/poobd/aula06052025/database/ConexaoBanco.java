package pucgo.poobd.aula06052025.database;

import pucgo.poobd.aula06052025.util.Alerta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static Connection conexao = buscarConexao();
    private static volatile ConexaoBanco instancia;

    private ConexaoBanco() {
    }

    private static Connection buscarConexao() {
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost/restaurante", "postgres", "postgres");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados! Mensagem: " + e.getMessage());
            Alerta.erro("Erro ao conectar com o banco de dados! Verificar log do sistema.");
        }
        return conexao;
    }

    public Connection getConexao() {
        return conexao;
    }

    public static ConexaoBanco getInstancia() {
        if (instancia == null) {
            synchronized (ConexaoBanco.class) {
                if (instancia == null) {
                    instancia = new ConexaoBanco();
                }
            }
        }
        return instancia;
    }
}
