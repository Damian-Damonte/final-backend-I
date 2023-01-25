package com.example.C22C.service;

import com.example.C22C.dto.TurnoDTO;
import com.example.C22C.dto.TurnoMapper;
import com.example.C22C.entity.Odontologo;
import com.example.C22C.entity.Paciente;
import com.example.C22C.entity.Turno;
import com.example.C22C.exception.BadRequestException;
import com.example.C22C.exception.ResourceNotFoundException;
import com.example.C22C.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private final TurnoRepository turnoRepository;
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoRepository = turnoRepository;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
    }

    public TurnoDTO buscarXId(Long id){
        Optional<Turno> turno = turnoRepository.findById(id);
        TurnoDTO turnoDTO = null;
        if(turno.isPresent())
            turnoDTO = TurnoMapper.toTunorDTO(turno.get());
        return turnoDTO;
    }

    public List<TurnoDTO> buscarTodos(){
        List<Turno> turnosEntity = turnoRepository.findAll();
        List<TurnoDTO> turnosDAO = new ArrayList<>();
        for(Turno turno : turnosEntity){
            turnosDAO.add(TurnoMapper.toTunorDTO(turno));
        }
        return turnosDAO;
    }

    public TurnoDTO guardarTurno(TurnoDTO turnoDTO) throws BadRequestException{
        String exceptionMessage = "";
        Odontologo odontologo = odontologoService.buscarOdontologoXId(turnoDTO.getOdontologo_id());
        Paciente paciente = pacienteService.buscarXId(turnoDTO.getPaciente_id());

        if (odontologo == null )
            exceptionMessage += "Odontologo con id " + turnoDTO.getOdontologo_id() + " no encontrado";
        if (paciente == null)
            exceptionMessage += "\n" + "Paciente con id " + turnoDTO.getPaciente_id() + " no encontrado";
        if (!exceptionMessage.equals(""))
            throw new BadRequestException(exceptionMessage);

        Turno turno = TurnoMapper.toTurno(turnoDTO);
        Turno turnoGuardado = turnoRepository.save(turno);
        turnoDTO.setId(turnoGuardado.getId());
        turnoDTO.setOdontologo_nombre(odontologo.getNombre());
        turnoDTO.setOdontologo_apellido(odontologo.getApellido());
        turnoDTO.setPaciente_nombre(paciente.getNombre());
        turnoDTO.setPaciente_apellido(paciente.getApellido());
        return turnoDTO;
    }

    public void eliminarXId (Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoAEliminar = turnoRepository.findById(id);
        if(turnoAEliminar.isEmpty())
            throw new ResourceNotFoundException("No existe ningun turno con id " + id);
        turnoRepository.deleteById(id);
    }

    public TurnoDTO modificarTurno(TurnoDTO turnoDTO) throws BadRequestException{
        String exceptionMessage = "";
        if (buscarXId(turnoDTO.getId()) == null)
            exceptionMessage += "Turno con id " + turnoDTO.getId() + " no encontrado" + "\n" ;
        if (odontologoService.buscarOdontologoXId(turnoDTO.getOdontologo_id()) == null )
            exceptionMessage += "Odontologo con id " + turnoDTO.getOdontologo_id() + " no encontrado" + "\n" ;
        if (pacienteService.buscarXId(turnoDTO.getPaciente_id()) == null)
            exceptionMessage += "Paciente con id " + turnoDTO.getPaciente_id() + " no encontrado";
        if (!exceptionMessage.equals(""))
            throw new BadRequestException(exceptionMessage);

        Turno turnoEntity = TurnoMapper.toTurno(turnoDTO);
        Turno turnoGuardado = turnoRepository.save(turnoEntity);
        turnoDTO.setOdontologo_nombre(turnoGuardado.getOdontologo().getNombre());
        turnoDTO.setOdontologo_apellido(turnoGuardado.getOdontologo().getApellido());
        turnoDTO.setPaciente_nombre(turnoGuardado.getPaciente().getNombre());
        turnoDTO.setPaciente_apellido(turnoGuardado.getPaciente().getApellido());
        return turnoDTO;
    }

    public List<TurnoDTO> buscarXFecha(LocalDate fecha){
        List<Turno> turnosEntity = turnoRepository.findByFecha(fecha);
        List<TurnoDTO> turnosDAO = new ArrayList<>();
        for(Turno turno : turnosEntity){turnosDAO.add(TurnoMapper.toTunorDTO(turno));}
        return turnosDAO;
    }

    public List<TurnoDTO> buscarXIdOdontologo(Long id){
        List<Turno> turnosEntity = turnoRepository.findByIdOdontologo(id);
        List<TurnoDTO> turnosDAO = new ArrayList<>();
        for(Turno turno : turnosEntity){turnosDAO.add(TurnoMapper.toTunorDTO(turno));}
        return turnosDAO;
    }

    public List<TurnoDTO> buscarXApellidoOdontologo(String apellido){
        List<Turno> turnosEntity = turnoRepository.findByApellidoOdontologo(apellido);
        List<TurnoDTO> turnosDAO = new ArrayList<>();
        for(Turno turno : turnosEntity){turnosDAO.add(TurnoMapper.toTunorDTO(turno));}
        return turnosDAO;
    }

    public List<TurnoDTO> buscarXIdPaciente(Long id){
        List<Turno> turnosEntity = turnoRepository.findByIdPaciente(id);
        List<TurnoDTO> turnosDAO = new ArrayList<>();
        for(Turno turno : turnosEntity){turnosDAO.add(TurnoMapper.toTunorDTO(turno));}
        return turnosDAO;
    }

    public List<TurnoDTO> buscarXApellidoPaciente(String apellido){
        List<Turno> turnosEntity = turnoRepository.findByApellidoPaciente(apellido);
        List<TurnoDTO> turnosDAO = new ArrayList<>();
        for(Turno turno : turnosEntity){turnosDAO.add(TurnoMapper.toTunorDTO(turno));}
        return turnosDAO;
    }
}


