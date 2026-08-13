package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Categoria;
import java.util.List;

public interface CategoriaService {

    List<Categoria> getCategorias(boolean activos);

    Categoria getCategoria(Categoria categoria);

    void save(Categoria categoria);

    void delete(Categoria categoria);
}