package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {

    List<Especie> findByActivo(Integer activo);
}