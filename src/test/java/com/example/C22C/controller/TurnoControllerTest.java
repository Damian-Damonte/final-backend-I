package com.example.C22C.controller;

import com.example.C22C.dto.TurnoDTO;
import com.example.C22C.entity.Domicilio;
import com.example.C22C.entity.Odontologo;
import com.example.C22C.entity.Paciente;
import com.example.C22C.service.OdontologoService;
import com.example.C22C.service.PacienteService;
import com.example.C22C.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
class TurnoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private ObjectMapper objectMapper;

    public void cargarInfo() throws Exception{
        Odontologo odontologo = new Odontologo("Matias", "Fernandez", "mp534");
        Odontologo odontologo2 = new Odontologo("Fernando", "Perez", "mp225");
        Paciente paciente = new Paciente("Fernandez", "Juan", "juanfer@gmail.com", 42527314, LocalDate.of(2022, 9,2), new Domicilio("Congreso", 1234, "La Plata", "Buenos Aires"));
        Paciente paciente2 = new Paciente("Perez", "Martin", "martinp@gmail.com", 30235936, LocalDate.of(2021, 2,15), new Domicilio("Tronador", 9876, "Belgrano", "CABA"));

        odontologoService.guardarOdontolo(odontologo);
        odontologoService.guardarOdontolo(odontologo2);
        pacienteService.guardarPaciente(paciente);
        pacienteService.guardarPaciente(paciente2);

        TurnoDTO turno = new TurnoDTO(LocalDate.of(2022, 10, 15), 1L, 1L);
        turnoService.guardarTurno(turno);
    }

    @Test
    @Order(1)
    public void guardarTurno() throws Exception{
        cargarInfo();
        TurnoDTO turno = new TurnoDTO(LocalDate.of(2022,9,18), 2L, 2L);
        String turnoJson = objectMapper.writeValueAsString(turno);
        TurnoDTO turno2 = new TurnoDTO(LocalDate.of(2022,11,7), 1L, 2L);
        String turnoJson2 = objectMapper.writeValueAsString(turno2);

        mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                .contentType(MediaType.APPLICATION_JSON).content(turnoJson))
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));

        mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                .contentType(MediaType.APPLICATION_JSON).content(turnoJson2))
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3));
    }

    @Test
    @Order(2)
    public void listarTurnosTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3));
    }

    @Test
    @Order(3)
    public void buscarTurnoXId() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/turnos/id/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.odontologo_apellido").value("Perez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paciente_nombre").value("Martin"));
    }

    @Test
    @Order(4)
    public void modificarTurno() throws Exception{
        TurnoDTO turnoModificado = new TurnoDTO(LocalDate.of(2022,3,3), 2L, 1L);
        turnoModificado.setId(1L);
        String turnoModificadoJson = objectMapper.writeValueAsString(turnoModificado);

        TurnoDTO turnoActual = turnoService.buscarXId(1L);
        assertEquals(1L, turnoActual.getOdontologo_id());
        assertEquals(LocalDate.of(2022, 10, 15), turnoActual.getFecha());

        mockMvc.perform(MockMvcRequestBuilders.put("/turnos")
                .contentType(MediaType.APPLICATION_JSON).content(turnoModificadoJson))
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        TurnoDTO turnoConModificacion = turnoService.buscarXId(1L);
        assertEquals(2L, turnoConModificacion.getOdontologo_id());
        assertEquals(LocalDate.of(2022,3,3), turnoConModificacion.getFecha());
    }

    @Test
    @Order(5)
    public void eliminarTurno() throws Exception{
        List<TurnoDTO> turnos = turnoService.buscarTodos();
        assertEquals(3, turnos.size());

        mockMvc.perform(MockMvcRequestBuilders.delete("/turnos/{id}", 2))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Turno con id 2 eliminado"))
                .andReturn();

        List<TurnoDTO> turnosEliminado = turnoService.buscarTodos();
        assertEquals(2, turnosEliminado.size());

        mockMvc.perform(MockMvcRequestBuilders.delete("/turnos/{id}", 20))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("No existe ningun turno con id 20"))
                .andReturn();
    }

}