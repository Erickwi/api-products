package com.espe.api_products.service;

import com.espe.api_products.model.Usuario;
import com.espe.api_products.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    public Usuario guardarOActualizar(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepositorio.findByGithubId(usuario.getGithubId());
        if (existente.isPresent()) {
            Usuario user = existente.get();
            user.setNombre(usuario.getNombre());
            user.setEmail(usuario.getEmail());
            user.setAvatarUrl(usuario.getAvatarUrl());
            return usuarioRepositorio.save(user);
        }
        return usuarioRepositorio.save(usuario);
    }
    
    public Optional<Usuario> buscarPorGithubId(String githubId) {
        return usuarioRepositorio.findByGithubId(githubId);
    }
}
