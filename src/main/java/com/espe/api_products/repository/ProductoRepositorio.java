package com.espe.api_products.repository;

import com.espe.api_products.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas más adelante
}

