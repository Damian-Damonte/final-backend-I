package com.example.C22C.service;

import com.example.C22C.entity.Paciente;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    public Paciente buscarXId (Long id){
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.orElse(null);
    }

    public List<Paciente> buscarTodos(){
        return pacienteRepository.findAll();
    }
    public void eliminarXId (Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteEliminar = pacienteRepository.findById(id);
        if(pacienteEliminar.isEmpty())
            throw new ResourceNotFoundException("No existe ningun paciente con id " + id);
        pacienteRepository.deleteById(id);
    }
    public Paciente guardarPaciente(Paciente paciente) throws BadRequestException{
        Paciente pacienteGuadardo = pacienteRepository.save(paciente);
        if (paciente.getId() == null)
            throw new BadRequestException("Atributos incorrectos");
        return pacienteGuadardo;
    }
    public Paciente modificarPaciente(Paciente paciente) throws BadRequestException{
        String exceptionMessage = "";
        if (buscarXId(paciente.getId()) == null)
            exceptionMessage += "Paciente con id " + paciente.getId() + " no encontrado" + "\n";

        if (!paciente.getId().equals(paciente.getDomicilio().getId()))
            exceptionMessage += "El id del paciente y el id del domicilio deben ser iguales";

        if (!exceptionMessage.equals(""))
            throw new BadRequestException(exceptionMessage);

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> buscarPacienteXNombre(String nombre){
        return pacienteRepository.findByNombre(nombre);
    }

    public List<Paciente> buscarPacienteXApellido(String apellido){
        return pacienteRepository.findByApellido(apellido);
    }

    public List<Paciente> buscarPacienteXEmail(String email){
        return pacienteRepository.findByEmail(email);
    }

    public List<Paciente> buscarPacienteXDni(Integer dni){
        return pacienteRepository.findByDni(dni);
    }
}
