package com.espe.api_products.service;

import com.espe.api_products.model.Producto;

import java.util.List;
import java.util.Optional;


public interface ProductoService {
    
    List<Producto> listarTodos();
    Optional<Producto> obtenerPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);

}
