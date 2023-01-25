package com.example.C22C.controller;

import com.example.C22C.dto.TurnoDTO;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/turnos")
public class TurnoController {
    private final TurnoService turnoService;

    @Autowired
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarTurnoXId (@PathVariable Long id){
        TurnoDTO turno = turnoService.buscarXId(id);
        return  turno != null ? ResponseEntity.ok(turno) : ResponseEntity.badRequest().body("Turno con id "+ id + " no encontrado");
    }

    @GetMapping
    public List<TurnoDTO> buscarAllTurnos(){
        return turnoService.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno (@RequestBody TurnoDTO turno) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.guardarTurno(turno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable("id") Long id) throws ResourceNotFoundException {
        turnoService.eliminarXId(id);
        return ResponseEntity.ok("Turno con id " + id + " eliminado");
    }

    @PutMapping
    public ResponseEntity<TurnoDTO> modificarTurno (@RequestBody TurnoDTO turno) throws BadRequestException{
        return ResponseEntity.ok(turnoService.modificarTurno(turno));
    }

    @GetMapping("/fecha/{fecha}")
    public List<TurnoDTO> buscarXFecha(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha){
        return turnoService.buscarXFecha(fecha);
    }

    @GetMapping("/idOdontologo/{id}")
    public List<TurnoDTO> buscarXIdOdontologo(@PathVariable Long id){
        return turnoService.buscarXIdOdontologo(id);
    }

    @GetMapping("/apellidoOdontologo/{apellido}")
    public List<TurnoDTO> buscarXNombreOdontologo(@PathVariable String apellido){
        return turnoService.buscarXApellidoOdontologo(apellido);
    }

    @GetMapping("/idPaciente/{id}")
    public List<TurnoDTO> buscarXIdPaciente(@PathVariable Long id){
        return turnoService.buscarXIdPaciente(id);
    }

    @GetMapping("/apellidoPaciente/{apellido}")
    public List<TurnoDTO> buscarXApellidoPaciente(@PathVariable String apellido){
        return turnoService.buscarXApellidoPaciente(apellido);
    }
}
