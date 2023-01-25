package com.example.C22C.repository;

import com.example.C22C.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    @Query("select t from Turno t where t.fecha=?1")
    List<Turno> findByFecha(LocalDate fecha);

    @Query("select t from Turno t where t.odontologo.id=?1")
    List<Turno> findByIdOdontologo(Long id);

    @Query("select t from Turno t where t.odontologo.apellido like %?1%")
    List<Turno> findByApellidoOdontologo(String apellido);

    @Query("select t from Turno t where t.paciente.id=?1")
    List<Turno> findByIdPaciente(Long id);

    @Query("select t from Turno t where t.paciente.apellido like %?1%")
    List<Turno> findByApellidoPaciente(String apellido);
}
