package org.example.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfiguracaoJDBC {

    //ATRIBUTOS NECESSÁRIOS QUE PREENCHEMOS PARA REALIZAR A CONEXÃO COM O BANCO DE DADOS "H2"
    private String jdbcDriver;
    private String dbUrl;
    private String nomeUsuario;
    private String senha;

    //CONSTRUTOR COM OS PARAMETROS QUE SÃO NECESSÁRIOS PARA REALIZAR O CADASTRO NO BANCO DE DADOS
    public ConfiguracaoJDBC() {
        this.jdbcDriver = "org.h2.Driver";
        this.dbUrl = "jdbc:h2:mem:clinica;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'create.sql'";
        this.nomeUsuario = "sa";
        this.senha = "";
    }

    //MÉTODO QUE REALIZA A CONEXÃO NO BANCO
    public Connection conectarComBancoDeDados() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, nomeUsuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
