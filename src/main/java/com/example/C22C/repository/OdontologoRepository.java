package com.example.C22C.repository;

import com.example.C22C.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    @Query("select o from Odontologo o where o.nombre like %?1%")
    List<Odontologo> findByNombre(String nombre);

    @Query("select o from Odontologo o where o.apellido like %?1%")
    List<Odontologo> findByApellido(String apellido);

    @Query("select o from Odontologo o where o.matricula like %?1%")
    List<Odontologo> findByMatricula(String matricula);
}
