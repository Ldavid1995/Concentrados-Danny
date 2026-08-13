package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Marca;
import com.concentrados.Danny.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Marca> getMarcas(boolean activos) {
        if (activos) {
            return marcaRepository.findByActivo(1);
        }
        return marcaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Marca getMarca(Marca marca) {
        if (marca == null || marca.getIdMarca() == null) {
            return null;
        }
        return marcaRepository.findById(marca.getIdMarca()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Marca marca) {
        if (marca.getActivo() == null) {
            marca.setActivo(1);
        }
        marcaRepository.save(marca);
    }

    @Override
    @Transactional
    public void delete(Marca marca) {
        if (marca != null && marca.getIdMarca() != null) {
            marcaRepository.deleteById(marca.getIdMarca());
        }
    }
}