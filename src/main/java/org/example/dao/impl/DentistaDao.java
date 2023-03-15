package org.example.dao.impl;

import org.apache.log4j.Logger;
import org.example.config.ConfiguracaoJDBC;
import org.example.dao.IDao;
import org.example.model.Dentista;
import org.example.model.Endereco;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DentistaDao implements IDao<Dentista> {


    //INSTANCIANDO A CONFIGURAÇÃO DO BANCO "H2"
    private ConfiguracaoJDBC configuracaoJDBC;

    //DECLARANDO O LOGGER
    private final static Logger logger = Logger.getLogger(DentistaDao.class);

    //INICIANDO UMA NOVA CONEXÃO DA CLASSE DENTISTA COM O BANCO
    public DentistaDao() {
        this.configuracaoJDBC = new ConfiguracaoJDBC();
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" PARA SALVAR'
    @Override
    public Dentista salvar(Dentista dentista) {
        logger.debug("Salvando novo Dentista: " + dentista.toString());
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = String.format("INSERT INTO dentista (nome, sobrenome, matricula) VALUES " +
                "('%s', '%s', '%s')", dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                dentista.setId(generatedKeys.getInt(1));
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentista;
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" DE BUSCAR TODOS
    @Override
    public List<Dentista> buscarTodos() {
        logger.debug("Buscando dentistas cadastrados...");
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = "SELECT * FROM dentista";
        List<Dentista> dentistas = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                dentistas.add(criarDentista(resultSet));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentistas;
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" DE EXCLUIR
    @Override
    public void excluir(int id) {
        logger.debug("Excluindo dentista com id: " + id);
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = String.format("DELETE FROM dentista WHERE id = '%s'", id);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MÉTODO IMPLEMENTADO DA INTERFACE "IDAO" DE BUSCAR POR ID
    @Override
    public Dentista buscarPorId(int id) {
        logger.debug("Buscando dentista com id: " + id);
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement statement = null;
        String query = String.format("SELECT * FROM dentista WHERE id = '%s'", id);
        Dentista dentista = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                dentista = criarDentista(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentista;
    }

    // MÉTODO CRIADO PARA CRIAR DENTISTA PARA ADICIONAR NO ARRAYLIST
    public Dentista criarDentista(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        String sobrenome = resultSet.getString("sobrenome");
        String matricula = resultSet.getString("matricula");
        Dentista dentista = new Dentista(nome, sobrenome, matricula);
        return dentista;
    }

}
