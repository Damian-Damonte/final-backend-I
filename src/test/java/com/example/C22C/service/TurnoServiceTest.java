package com.example.C22C.service;

import com.example.C22C.dto.TurnoDTO;
import com.example.C22C.entity.Domicilio;
import com.example.C22C.entity.Odontologo;
import com.example.C22C.entity.Paciente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TurnoServiceTest {
    @Autowired
    public TurnoService turnoService;
    @Autowired
    public OdontologoService odontologoService;
    @Autowired
    public PacienteService pacienteService;
    public Odontologo odontologo = new Odontologo("Matias", "Fernandez", "mp534");
    public Odontologo odontologo2 = new Odontologo("Fernando", "Perez", "mp225");
    public Paciente paciente = new Paciente("Fernandez", "Juan", "juanfer@gmail.com", 42527314, LocalDate.of(2022, 9,2), new Domicilio("Congreso", 1234, "La Plata", "Buenos Aires"));
    public Paciente paciente2 = new Paciente("Perez", "Martin", "martinp@gmail.com", 30235936, LocalDate.of(2021, 2,15), new Domicilio("Tronador", 9876, "Belgrano", "CABA"));



    @Test
    @Order(1)
    void guardarTurno() throws Exception{
        odontologoService.guardarOdontolo(odontologo);
        odontologoService.guardarOdontolo(odontologo2);
        pacienteService.guardarPaciente(paciente);
        pacienteService.guardarPaciente(paciente2);


        TurnoDTO turno = new TurnoDTO(LocalDate.of(2022, 10, 15), 1L, 1L);
        TurnoDTO turno2 = new TurnoDTO(LocalDate.of(2022, 11, 23), 2L, 2L);
        turnoService.guardarTurno(turno);
        turnoService.guardarTurno(turno2);

        assertEquals(1, turnoService.buscarXId(1L).getId());
        assertEquals(2, turnoService.buscarXId(2L).getId());
    }

    @Test
    @Order(2)
    void buscarXIdTurno() {
        TurnoDTO turnoBuscado = turnoService.buscarXId(1L);
        TurnoDTO turnoBuscado2 = turnoService.buscarXId(2L);

        assertEquals(1, turnoBuscado.getId());
        assertEquals(2, turnoBuscado2.getId());
    }

    @Test
    @Order(3)
    void buscarTodos() {
        List<TurnoDTO> turnos = turnoService.buscarTodos();

        assertEquals(2, turnos.size());
    }

    @Test
    @Order(4)
    void modificarTurno() throws Exception{
        TurnoDTO turno = turnoService.buscarXId(1L);
        turno.setPaciente_id(2L);
        turno.setFecha(LocalDate.of(2022,8, 8));
        turnoService.modificarTurno(turno);

        assertEquals(2, turnoService.buscarXId(1L).getPaciente_id());
        assertEquals(LocalDate.of(2022,8, 8), turnoService.buscarXId(1L).getFecha());
    }

    @Test
    @Order(5)
    public void buscarXFecha(){
        List<TurnoDTO> turnos = turnoService.buscarXFecha(LocalDate.of(2022,8, 8));

        boolean turnoFecha = true;
        for(TurnoDTO turno : turnos){
            if (!turno.getFecha().equals(LocalDate.of(2022,8, 8))) {
                turnoFecha = false;
                break;
            }
        }

        assertTrue(turnoFecha);
    }

    @Test
    @Order(6)
    public void buscarXOdontologoId(){
        List<TurnoDTO> turnos = turnoService.buscarXIdOdontologo(1L);

        boolean turnoIdOdontolog1 = true;
        for(TurnoDTO turno : turnos){
            if (turno.getOdontologo_id() != 1L) {
                turnoIdOdontolog1 = false;
                break;
            }
        }

        assertTrue(turnoIdOdontolog1);
    }

    @Test
    @Order(7)
    public void buscarXOdontologoApellido(){
        List<TurnoDTO> turnos = turnoService.buscarXApellidoOdontologo("Perez");

        boolean turnoOdontologPerez = true;
        for(TurnoDTO turno : turnos){
            if (!(turno.getOdontologo_apellido().equals("Perez"))) {
                turnoOdontologPerez = false;
                break;
            }
        }

        assertTrue(turnoOdontologPerez);
    }

    @Test
    @Order(8)
    public void buscarXPacienteId(){
        List<TurnoDTO> turnos = turnoService.buscarXIdPaciente(2L);

        boolean turnoIdPaciente2 = true;
        for(TurnoDTO turno : turnos){
            if (turno.getPaciente_id() != 2) {
                turnoIdPaciente2 = false;
                break;
            }
        }

        assertTrue(turnoIdPaciente2);
    }

    @Test
    @Order(9)
    public void buscarXPacienteApellido(){
        List<TurnoDTO> turnos = turnoService.buscarXApellidoPaciente("Fernandez");

        boolean turnoOdontologPerez = true;
        for(TurnoDTO turno : turnos){
            if (!(turno.getPaciente_apellido().equals("Fernandez"))) {
                turnoOdontologPerez = false;
                break;
            }
        }

        assertTrue(turnoOdontologPerez);
    }

    @Test
    @Order(10)
    void eliminarXId() throws Exception{
        List<TurnoDTO> turnos = turnoService.buscarTodos();
        turnoService.eliminarXId(1L);
        List<TurnoDTO> turnosEliminado = turnoService.buscarTodos();

        assertEquals(2, turnos.size());
        assertEquals(1, turnosEliminado.size());
    }

}