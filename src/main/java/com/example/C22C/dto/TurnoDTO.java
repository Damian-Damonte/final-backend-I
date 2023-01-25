package com.example.C22C.dto;

import java.time.LocalDate;

public class TurnoDTO {
    private Long id;
    private LocalDate fecha;
    private Long odontologo_id;
    private String odontologo_nombre;
    private String odontologo_apellido;
    private Long paciente_id;
    private String paciente_nombre;
    private String paciente_apellido;

    public TurnoDTO() {
    }

    public TurnoDTO(LocalDate fecha, Long odontologo_id, Long paciente_id) {
        this.fecha = fecha;
        this.odontologo_id = odontologo_id;
        this.paciente_id = paciente_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOdontologo_nombre() {
        return odontologo_nombre;
    }

    public void setOdontologo_nombre(String odontologo_nombre) {
        this.odontologo_nombre = odontologo_nombre;
    }

    public String getOdontologo_apellido() {
        return odontologo_apellido;
    }

    public void setOdontologo_apellido(String odontologo_apellido) {
        this.odontologo_apellido = odontologo_apellido;
    }

    public String getPaciente_apellido() {
        return paciente_apellido;
    }

    public void setPaciente_apellido(String paciente_apellido) {
        this.paciente_apellido = paciente_apellido;
    }

    public String getPaciente_nombre() {
        return paciente_nombre;
    }

    public void setPaciente_nombre(String paciente_nombre) {
        this.paciente_nombre = paciente_nombre;
    }

    public Long getOdontologo_id() {
        return odontologo_id;
    }

    public void setOdontologo_id(Long odontologo_id) {
        this.odontologo_id = odontologo_id;
    }

    public Long getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(Long paciente_id) {
        this.paciente_id = paciente_id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
