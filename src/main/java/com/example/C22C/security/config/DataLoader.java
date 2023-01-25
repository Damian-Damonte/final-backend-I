package com.example.C22C.security.config;

import com.example.C22C.entity.RolUsuario;
import com.example.C22C.entity.Usuario;
import com.example.C22C.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final UsuarioRepository usuarioRepository;
    @Autowired
    public DataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordHash = passwordEncoder.encode("admin");
        Usuario usuario = new Usuario("admin", passwordHash, RolUsuario.ROLE_ADMIN);
        usuarioRepository.save(usuario);
    }
}
