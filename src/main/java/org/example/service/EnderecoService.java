package org.example.service;

import org.example.dao.IDao;
import org.example.model.Endereco;
import java.util.List;

public class EnderecoService {

    private IDao<Endereco> enderecoIDao;

    public EnderecoService(IDao<Endereco> enderecoIDao) {
        this.enderecoIDao = enderecoIDao;
    }

    public Endereco salvar(Endereco endereco) {
        enderecoIDao.salvar(endereco);
        return endereco;
    }

    public Endereco buscarPorId(int id) {
        return enderecoIDao.buscarPorId(id);
    }

    public List<Endereco> buscarTodos() {
        return enderecoIDao.buscarTodos();
    }

    public void excluir (int id) {
        enderecoIDao.excluir(id);
    }

}
