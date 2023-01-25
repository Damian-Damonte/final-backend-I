package com.example.C22C.service;

import com.example.C22C.entity.Odontologo;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class OdontologoServiceTest {
    @Autowired
    OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologo() throws Exception{
        Odontologo odontologo = new Odontologo("Matias", "Fernandez", "mp534");
        Odontologo odontologo2 = new Odontologo("Fernando", "Perez", "mp225");
        Odontologo odontologoGuardado = odontologoService.guardarOdontolo(odontologo);
        Odontologo odontologoGuardado2 = odontologoService.guardarOdontolo(odontologo2);
        assertEquals(1, odontologoGuardado.getId());
        assertEquals(2, odontologoGuardado2.getId());
    }

    @Test
    @Order(2)
    public void buscarTodos(){
        List<Odontologo> odontologos = odontologoService.buscarTodoOdontologos();
        assertEquals(2, odontologos.size());
    }

    @Test
    @Order(3)
    public void buscarxId(){
        Odontologo odontologo = odontologoService.buscarOdontologoXId(1L);
        Odontologo odontologo2 = odontologoService.buscarOdontologoXId(2L);

        assertEquals(1, odontologo.getId());
        assertEquals(2, odontologo2.getId());
    }

    @Test
    @Order(4)
    public void modificarOdontologo() throws Exception{
        Odontologo odontologo = odontologoService.buscarOdontologoXId(1L);
        odontologo.setNombre("Nicolas");
        odontologoService.modificarOdontologoXId(odontologo);

        assertEquals("Nicolas", odontologoService.buscarOdontologoXId(1L).getNombre());
    }

    @Test
    @Order(5)
    public void buscarXNombe(){
        List<Odontologo> odontologos = odontologoService.buscarOdontologoXNombre("Fernando");
        boolean odontologoFernando = true;
        for(Odontologo odontologo : odontologos){
            if (!odontologo.getNombre().equals("Fernando")) {
                odontologoFernando = false;
                break;
            }
        }

        assertTrue(odontologoFernando);
    }

    @Test
    @Order(6)
    public void buscarXApellido(){
        List<Odontologo> odontologos = odontologoService.buscarOdontologoXApellido("Perez");
        boolean odontologoPerez = true;
        for(Odontologo odontologo : odontologos){
            if (!odontologo.getApellido().equals("Perez")) {
                odontologoPerez = false;
                break;
            }
        }

        assertTrue(odontologoPerez);
    }

    @Test
    @Order(7)
    public void buscarXMatricula(){
        List<Odontologo> odontologos = odontologoService.buscarOdontologoXMatricula("mp225");
        boolean odontologoMtricula = true;
        for(Odontologo odontologo : odontologos){
            if (!odontologo.getMatricula().equals("mp225")) {
                odontologoMtricula = false;
                break;
            }
        }

        assertTrue(odontologoMtricula);
    }

    @Test
    @Order(8)
    public void eliminar() throws Exception{
        List<Odontologo> odontologos = odontologoService.buscarTodoOdontologos();
        odontologoService.elminarOdontologoXId(1L);
        List<Odontologo> odontologosEliminado = odontologoService.buscarTodoOdontologos();

        assertEquals(2, odontologos.size());
        assertEquals(1, odontologosEliminado.size());
    }
}