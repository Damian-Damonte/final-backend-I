package com.example.C22C.controller;

import com.example.C22C.entity.Odontologo;
import com.example.C22C.entity.Usuario;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.security.payload.LoginRequest;
import com.example.C22C.security.payload.RegisterRequest;
import com.example.C22C.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
    Map<String, String> jwt = authService.login(loginRequest);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest signUpRequest) {
        return ResponseEntity.status(201).body(authService.register(signUpRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> allUsers(){
        return ResponseEntity.ok(authService.findAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("id") Long id) throws ResourceNotFoundException {
        authService.eliminarXId(id);
        return ResponseEntity.ok("Usuario con id " + id + " eliminado");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<Usuario>> usuarioXUsername (@PathVariable String username){
        return ResponseEntity.ok(authService.buscarXUsername(username));
    }

    @GetMapping("id/{id}")
    ResponseEntity<?> buscarXId (@PathVariable("id") Long id){
        Usuario usuario = authService.buscarXId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.badRequest().body("Usuario con id "+ id + " no encontrado");
    }

    @PutMapping
    public ResponseEntity<Usuario>  moficarUsuario(@RequestBody Usuario usuario) throws BadRequestException {
        return ResponseEntity.ok(authService.modificarUsuario(usuario));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles(){
        return ResponseEntity.ok(authService.getRoles());
    }

}