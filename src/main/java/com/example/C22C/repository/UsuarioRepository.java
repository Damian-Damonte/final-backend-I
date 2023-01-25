package com.example.C22C.repository;

import com.example.C22C.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String email);
    Boolean existsByUsername(String email);
    @Query("select o from Usuario o where o.username like %?1%")
    List<Usuario> findByUsername(String username);
}
