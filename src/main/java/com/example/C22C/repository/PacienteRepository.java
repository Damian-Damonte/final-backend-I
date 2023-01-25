package com.example.C22C.repository;

import com.example.C22C.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("select p from Paciente p where p.nombre like %?1%")
    List<Paciente> findByNombre(String nombre);

    @Query("select p from Paciente p where p.apellido like %?1%")
    List<Paciente> findByApellido(String apellido);

    @Query("select p from Paciente p where p.email like %?1%")
    List<Paciente> findByEmail(String email);

    @Query("select p from Paciente p where p.dni=?1")
    List<Paciente> findByDni(Integer dni);
}
