package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Especie;
import java.util.List;

public interface EspecieService {

    List<Especie> getEspecies(boolean activos);

    Especie getEspecie(Especie especie);

    void save(Especie especie);

    void delete(Especie especie);
}