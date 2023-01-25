package com.example.C22C.service;

import com.example.C22C.entity.RolUsuario;
import com.example.C22C.entity.Usuario;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.repository.UsuarioRepository;
import com.example.C22C.security.jwt.JwtTokenUtil;
import com.example.C22C.security.payload.LoginRequest;
import com.example.C22C.security.payload.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;


    public AuthService(AuthenticationManager authManager, JwtTokenUtil jwtTokenUtil, UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public Map<String, String> login(LoginRequest loginRequest){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);
        return Map.of("jwt",jwt);
    }

    public String register(RegisterRequest signUpRequest){
        if (usuarioRepository.existsByUsername(signUpRequest.getUsername()))
            return "Error: userName is already in use!";

        Usuario usuario = new Usuario(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getUsuarioRol());

        usuarioRepository.save(usuario);

        return "User registered successfully!";
    }

    public List<Usuario> findAllUsers(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarXId(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public void eliminarXId (Long id) throws ResourceNotFoundException {
        Optional<Usuario> usuarioEliminar = usuarioRepository.findById(id);
        if(usuarioEliminar.isEmpty())
            throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado");
        usuarioRepository.deleteById(id);
    }

    public Usuario modificarUsuario (Usuario usuario) throws BadRequestException {
        Usuario usuarioModificar = buscarXId(usuario.getId());
        if (usuarioModificar == null)
            throw new BadRequestException("Usuario con id " + usuario.getId() + " no encontrado");
        usuario.setPassword(usuarioModificar.getPassword());
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarXUsername(String username){
        return usuarioRepository.findByUsername(username);
    }

    public List<String> getRoles(){
        RolUsuario[] roles = RolUsuario.values();
        List<String> rolesString = new ArrayList<>();
        for(RolUsuario rol : roles){
            rolesString.add(rol.toString());
        }
        return rolesString;
    }
}
