package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Especie;
import com.concentrados.Danny.repository.EspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EspecieServiceImpl implements EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Especie> getEspecies(boolean activos) {
        if (activos) {
            return especieRepository.findByActivo(1);
        }
        return especieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Especie getEspecie(Especie especie) {
        if (especie == null || especie.getIdEspecie() == null) {
            return null;
        }
        return especieRepository.findById(especie.getIdEspecie()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Especie especie) {
        if (especie.getActivo() == null) {
            especie.setActivo(1);
        }
        especieRepository.save(especie);
    }

    @Override
    @Transactional
    public void delete(Especie especie) {
        if (especie != null && especie.getIdEspecie() != null) {
            especieRepository.deleteById(especie.getIdEspecie());
        }
    }
}