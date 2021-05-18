package edu.pucp.gtics.lab6_gtics_20211.repository;

import edu.pucp.gtics.lab6_gtics_20211.entity.Generos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GenerosRepository  extends JpaRepository<Generos,Integer> {
}
