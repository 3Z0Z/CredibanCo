package com.credibanco.assessment.card.repository;

import com.credibanco.assessment.card.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long>{
    public Tarjeta findByPan(String pan);
}
