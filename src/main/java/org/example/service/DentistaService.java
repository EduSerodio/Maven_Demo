package org.example.service;

import org.example.dao.IDao;
import org.example.model.Dentista;
import org.example.model.Endereco;

import java.util.List;

public class DentistaService {

    private IDao<Dentista> dentistaIDao;

    public DentistaService(IDao<Dentista> dentistaIDao) {
        this.dentistaIDao = dentistaIDao;
    }

    public Dentista salvar(Dentista dentista) {
        dentistaIDao.salvar(dentista);
        return dentista;
    }

    public Dentista buscarPorId(int id) {
        return dentistaIDao.buscarPorId(id);
    }

    public List<Dentista> buscarTodos() {
        return dentistaIDao.buscarTodos();
    }

    public void excluir (int id) {
        dentistaIDao.excluir(id);
    }

}
