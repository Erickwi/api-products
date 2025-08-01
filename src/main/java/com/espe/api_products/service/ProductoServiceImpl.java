package com.espe.api_products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import com.espe.api_products.model.Producto;
import com.espe.api_products.repository.ProductoRepositorio;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> listarTodos() {
        return productoRepositorio.findAll();
    }

    @Override
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepositorio.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public void eliminar(Long id) {
        productoRepositorio.deleteById(id);
    }

}
