package com.example.C22C.controller;

import com.example.C22C.entity.Odontologo;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/odontologos")
public class OdontologoController {
    private final OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("id/{id}")
    ResponseEntity<?> buscarXId (@PathVariable("id") Long id){
        Odontologo odontologo = odontologoService.buscarOdontologoXId(id);
        return odontologo != null ? ResponseEntity.ok(odontologo) : ResponseEntity.badRequest().body("Odontologo con id "+ id + " no encontrado");
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> getAllOdontologos(){
        return ResponseEntity.status(200).body(odontologoService.buscarTodoOdontologos());
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo (@RequestBody Odontologo odontologo) throws BadRequestException{
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.guardarOdontolo(odontologo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable("id") Long id) throws ResourceNotFoundException {
        odontologoService.elminarOdontologoXId(id);
        return ResponseEntity.ok("Odontologo con id " + id + " eliminado");
    }

    @PutMapping
    public ResponseEntity<Odontologo>  modificarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        return ResponseEntity.ok(odontologoService.modificarOdontologoXId(odontologo));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> odontologosXNombre (@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarOdontologoXNombre(nombre));
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> odontologosXApellido (@PathVariable String apellido){
        return ResponseEntity.ok(odontologoService.buscarOdontologoXApellido(apellido));
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<List<Odontologo>> odontologosXMatricula (@PathVariable String matricula){
        return ResponseEntity.ok(odontologoService.buscarOdontologoXMatricula(matricula));
    }
}
