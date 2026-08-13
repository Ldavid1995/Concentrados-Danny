package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Marca;
import java.util.List;

public interface MarcaService {

    List<Marca> getMarcas(boolean activos);

    Marca getMarca(Marca marca);

    void save(Marca marca);

    void delete(Marca marca);
}