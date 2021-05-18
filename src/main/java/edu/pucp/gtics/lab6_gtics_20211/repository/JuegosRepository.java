package edu.pucp.gtics.lab6_gtics_20211.repository;

import edu.pucp.gtics.lab6_gtics_20211.entity.Juegos;
import edu.pucp.gtics.lab6_gtics_20211.entity.JuegosUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface JuegosRepository extends JpaRepository<Juegos,Integer> {


    @Query(value = "select j.image as imageURL, j.nombre as nombre, " +
            "j.descripcion as descripcion, j.genero as genero from juegos j " +
            "inner join juegosxusuario ju on j.idjuego = ju.idjuego " +
            "inner join usuarios u " +
            "on u.idusuario = ju.idusuario " +
            "where u.idusuario = ?1",nativeQuery = true)
    List<JuegosUserDto> obtenerJuegosPorUser(int idusuario);

}
