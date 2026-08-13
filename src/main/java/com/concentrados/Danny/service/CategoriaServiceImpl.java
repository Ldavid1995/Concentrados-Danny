package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Categoria;
import com.concentrados.Danny.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activos) {
        if (activos) {
            return categoriaRepository.findByActivo(1);
        }
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {
        if (categoria == null || categoria.getIdCategoria() == null) {
            return null;
        }
        return categoriaRepository.findById(categoria.getIdCategoria()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Categoria categoria) {
        if (categoria.getActivo() == null) {
            categoria.setActivo(1);
        }
        categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public void delete(Categoria categoria) {
        if (categoria != null && categoria.getIdCategoria() != null) {
            categoriaRepository.deleteById(categoria.getIdCategoria());
        }
    }
}