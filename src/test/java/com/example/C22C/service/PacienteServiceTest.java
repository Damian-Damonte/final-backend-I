package com.example.C22C.service;

import com.example.C22C.entity.Domicilio;
import com.example.C22C.entity.Odontologo;
import com.example.C22C.entity.Paciente;
import com.example.C22C.exception.BadRequestException;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPaciente() throws BadRequestException {
        Domicilio domicilio = new Domicilio("Congreso", 1234, "La Plata", "Buenos Aires");
        Paciente paciente = new Paciente("Fernandez", "Juan", "juanfer@gmail.com", 42527314, LocalDate.of(2022, 9,2), domicilio);
        Domicilio domicilio2 = new Domicilio("Tronador", 9876, "Belgrano", "CABA");
        Paciente paciente2 = new Paciente("Perez", "Martin", "martinp@gmail.com", 30235936, LocalDate.of(2021, 2,15), domicilio2);

        pacienteService.guardarPaciente(paciente);
        pacienteService.guardarPaciente(paciente2);

        assertEquals(1, paciente.getId());
        assertEquals(2, paciente2.getId());
    }

    @Test
    @Order(2)
    public void buscarTodos(){
        List<Paciente> pacientes = pacienteService.buscarTodos();

        assertEquals(2, pacientes.size());
    }

    @Test
    @Order(3)
    public void buscarXId(){
        Paciente paciente = pacienteService.buscarXId(1L);
        Paciente paciente2 = pacienteService.buscarXId(2L);

        assertEquals(1, paciente.getId());
        assertEquals(2, paciente2.getId());
    }

    @Test
    @Order(4)
    public void modificarPaciente() throws Exception{
        Paciente paciente = pacienteService.buscarXId(1L);
        paciente.setNombre("Santiago");
        pacienteService.modificarPaciente(paciente);

        assertEquals("Santiago", pacienteService.buscarXId(1L).getNombre());
    }

    @Test
    @Order(5)
    public void buscarXNombre(){
        List<Paciente> pacientes = pacienteService.buscarPacienteXNombre("Santiago");
        boolean pacienteSantiago = true;
        for(Paciente paciente : pacientes){
            if (!paciente.getNombre().equals("Santiago")) {
                pacienteSantiago = false;
                break;
            }
        }

        assertTrue(pacienteSantiago);
    }

    @Test
    @Order(6)
    public void buscarXApellido(){
        List<Paciente> pacientes = pacienteService.buscarPacienteXApellido("Perez");
        boolean pacientePerez = true;
        for(Paciente paciente : pacientes){
            if (!paciente.getApellido().equals("Perez")) {
                pacientePerez = false;
                break;
            }
        }

        assertTrue(pacientePerez);
    }

    @Test
    @Order(7)
    public void buscarXEmail(){
        List<Paciente> pacientes = pacienteService.buscarPacienteXEmail("juanfer@gmail.com");
        boolean pacienteEmail = true;
        for(Paciente paciente : pacientes){
            if (!paciente.getEmail().equals("juanfer@gmail.com")) {
                pacienteEmail = false;
                break;
            }
        }

        assertTrue(pacienteEmail);
    }

    @Test
    @Order(8)
    public void buscarXDni(){
        List<Paciente> pacientes = pacienteService.buscarPacienteXDni(42527314);
        boolean pacienteDni = true;
        for(Paciente paciente : pacientes){
            if (!(paciente.getDni() == 42527314)) {
                pacienteDni = false;
                break;
            }
        }

        assertTrue(pacienteDni);
    }

    @Test
    @Order(9)
    public void eliminar() throws Exception{
        List<Paciente> pacientes = pacienteService.buscarTodos();
        pacienteService.eliminarXId(1L);
        List<Paciente> pacienteEliminado = pacienteService.buscarTodos();

        assertEquals(2, pacientes.size());
        assertEquals(1, pacienteEliminado.size());
    }



}