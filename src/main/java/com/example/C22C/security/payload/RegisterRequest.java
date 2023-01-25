package com.example.C22C.security.payload;

import com.example.C22C.entity.RolUsuario;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class RegisterRequest {
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RolUsuario usuarioRol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getUsuarioRol() {
        return usuarioRol;
    }

    public void setUsuarioRol(RolUsuario usuarioRol) {
        this.usuarioRol = usuarioRol;
    }
}
