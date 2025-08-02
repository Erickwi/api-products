package com.espe.api_products.controller;

import com.espe.api_products.model.Usuario;
import com.espe.api_products.service.JwtService;
import com.espe.api_products.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/github/callback")
    public Object githubCallback(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            String html = """
                <html><body style='font-family:sans-serif;'>
                <h2>No autenticado</h2>
                <p>No se pudo obtener la información de usuario.<br>
                Por favor, inicia sesión desde <a href='/login'>/login</a>.<br>
                Si ya iniciaste sesión, vuelve a intentarlo desde el inicio.</p>
                </body></html>
            """;
            return ResponseEntity.status(401).header("Content-Type", "text/html").body(html);
        }
        String githubId = principal.getAttribute("id").toString();
        String nombre = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String avatarUrl = principal.getAttribute("avatar_url");
        // Crear o actualizar usuario
        Usuario usuario = new Usuario(githubId, nombre, email, avatarUrl);
        usuarioService.guardarOActualizar(usuario);
        // Generar token JWT
        String token = jwtService.generateToken(githubId, nombre, email);
        // Redirigir al frontend con el token como parámetro
        String frontendUrl = "http://127.0.0.1:3000/index.html?token=" + token;
        RedirectView rv = new RedirectView(frontendUrl);
        rv.setExposeModelAttributes(false);
        return rv;
    }
    
    @GetMapping("/user")
    public ResponseEntity<Usuario> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        
        String githubId = principal.getAttribute("id").toString();
        return usuarioService.buscarPorGithubId(githubId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
