package com.credibanco.assessment.card.repository;

import com.credibanco.assessment.card.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
