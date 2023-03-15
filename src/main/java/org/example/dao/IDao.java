package org.example.dao;

import java.util.List;

public interface IDao<T> {

    //MÉTODOS GENÉRICOS QUE AS CLASSES CRIADAS VÃO IMPLEMENTAR.
    T salvar(T t);
    List<T> buscarTodos();
    void excluir(int id);
    T buscarPorId(int id);
}
