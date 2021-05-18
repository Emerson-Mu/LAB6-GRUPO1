package edu.pucp.gtics.lab6_gtics_20211.repository;

import edu.pucp.gtics.lab6_gtics_20211.entity.Juegos;
import edu.pucp.gtics.lab6_gtics_20211.entity.JuegosUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface JuegosRepository extends JpaRepository<Juegos,Integer> {


    @Query(value = "select j.image as imageURL, j.nombre as nombre,\n" +
            "            j.descripcion as descripcion, g.nombre as genero\n" +
            "            from juegos j \n" +
            "            inner join juegosxusuario ju on j.idjuego = ju.idjuego\n" +
            "            inner join usuarios u on u.idusuario = ju.idusuario\n" +
            "            inner join generos g on j.idgenero = g.idgenero\n" +
            "            where u.idusuario = ?1",nativeQuery = true)
    List<JuegosUserDto> obtenerJuegosPorUser(int idusuario);

}
