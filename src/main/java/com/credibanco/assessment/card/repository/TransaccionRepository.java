package com.credibanco.assessment.card.repository;

import com.credibanco.assessment.card.model.Transaccion;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long>{
    public Transaccion findByNumReferenciaAndTotalCompra(String numReferencia, BigDecimal totalCompra);
    
    @Query("SELECT t FROM Transaccion t WHERE t.tarjeta.idTarjeta = ?1")
    List<Transaccion> findByIdTarjeta(Long idTarjeta);
}
