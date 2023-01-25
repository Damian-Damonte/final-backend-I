package com.example.C22C.dto;

import com.example.C22C.entity.Odontologo;
import com.example.C22C.entity.Paciente;
import com.example.C22C.entity.Turno;

public class TurnoMapper {
    public static TurnoDTO toTunorDTO(Turno turno){
        TurnoDTO turnoDTO = new TurnoDTO();
        if(turno.getId() != null)
            turnoDTO.setId(turno.getId());

        turnoDTO.setFecha(turno.getFecha());

        turnoDTO.setOdontologo_id(turno.getOdontologo().getId());
        turnoDTO.setOdontologo_nombre(turno.getOdontologo().getNombre());
        turnoDTO.setOdontologo_apellido(turno.getOdontologo().getApellido());

        turnoDTO.setPaciente_id(turno.getPaciente().getId());
        turnoDTO.setPaciente_nombre(turno.getPaciente().getNombre());
        turnoDTO.setPaciente_apellido(turno.getPaciente().getApellido());

        return turnoDTO;
    }

    public static Turno toTurno (TurnoDTO turnoDTO){
        Turno turno = new Turno();

        if(turnoDTO.getId() != null)
            turno.setId(turnoDTO.getId());

        turno.setFecha(turnoDTO.getFecha());

        Odontologo odontologo = new Odontologo();
        odontologo.setId(turnoDTO.getOdontologo_id());
        turno.setOdontologo(odontologo);

        Paciente paciente = new Paciente();
        paciente.setId(turnoDTO.getPaciente_id());
        turno.setPaciente(paciente);

        return turno;
    }
}
