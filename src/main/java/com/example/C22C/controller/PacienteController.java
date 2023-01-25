package com.example.C22C.controller;

import com.example.C22C.entity.Paciente;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/id/{id}")
    ResponseEntity<?> pacienteXId (@PathVariable("id") Long id){
        Paciente paciente = pacienteService.buscarXId(id);
        return  paciente != null ? ResponseEntity.ok(paciente) : ResponseEntity.badRequest().body("Paciente con id "+ id + " no encontrado");

    }

    @GetMapping
    public ResponseEntity<List<Paciente>> getAllPacientes(){
        return ResponseEntity.ok().body(pacienteService.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente (@RequestBody Paciente paciente) throws BadRequestException{
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.guardarPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable("id") Long id) throws ResourceNotFoundException {
        pacienteService.eliminarXId(id);
        return ResponseEntity.ok("Paciente con id " + id + " eliminado");
    }

    @PutMapping
    public ResponseEntity<Paciente>  modificarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        return ResponseEntity.ok(pacienteService.modificarPaciente(paciente));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Paciente>> pacienteXNombre (@PathVariable String nombre){
        return ResponseEntity.ok(pacienteService.buscarPacienteXNombre(nombre));
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Paciente>> pacienteXApellido (@PathVariable String apellido){
        return ResponseEntity.ok(pacienteService.buscarPacienteXApellido(apellido));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Paciente>> pacienteXEmail (@PathVariable String email){
        return ResponseEntity.ok(pacienteService.buscarPacienteXEmail(email));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<List<Paciente>> pacienteXDni (@PathVariable Integer dni){
        return ResponseEntity.ok(pacienteService.buscarPacienteXDni(dni));
    }
}
