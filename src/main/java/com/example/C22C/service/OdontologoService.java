package com.example.C22C.service;

import com.example.C22C.entity.Odontologo;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    private final OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontolo(Odontologo odontologo) throws BadRequestException{
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        if (odontologoGuardado.getId() == null)
            throw new BadRequestException("Atributos incorrectos");
        return odontologoGuardado;
    }

    public List<Odontologo> buscarTodoOdontologos (){
        return odontologoRepository.findAll();
    }

    public Odontologo buscarOdontologoXId(Long id){
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        return odontologo.orElse(null);
    }

    public void elminarOdontologoXId (Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoEliminar = odontologoRepository.findById(id);
        if(odontologoEliminar.isEmpty())
            throw new ResourceNotFoundException("Odontologo con id " + id + " no encontrado");
        odontologoRepository.deleteById(id);
    }

    public Odontologo modificarOdontologoXId (Odontologo odontologo) throws BadRequestException{
        if (buscarOdontologoXId(odontologo.getId()) == null)
            throw new BadRequestException("Odontologo con id " + odontologo.getId() + " no encontrado");
        return odontologoRepository.save(odontologo);
    }

    public List<Odontologo> buscarOdontologoXNombre(String nombre){
        return odontologoRepository.findByNombre(nombre);
    }

    public List<Odontologo> buscarOdontologoXApellido(String apellido){
        return odontologoRepository.findByApellido(apellido);
    }

    public List<Odontologo> buscarOdontologoXMatricula(String matricula){
        return odontologoRepository.findByMatricula(matricula);
    }
}
