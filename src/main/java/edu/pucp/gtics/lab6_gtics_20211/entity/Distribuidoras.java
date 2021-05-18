package edu.pucp.gtics.lab6_gtics_20211.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "distribuidoras")
public class Distribuidoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Distribuidora no puede estar vacío")
    private int iddistribuidora;

    @Size(min=3, max = 50, message = "Debe contener entre 3 y 50 caracteres")
    private String nombre;

    @Size(min=3, max = 198, message = "Debe contener entre 3 y 198 caracteres")
    private String descripcion;

    @Min(value = 1880, message = "Debe ser mayor que o igual a 1880")
    @Max(value = 2100, message = "Debe ser menor que 2100")
    private int fundacion = 1800;

    @ManyToOne
    @JoinColumn(name = "idsede")
    @Valid
    private Paises pais;

    public int getIddistribuidora() {
        return iddistribuidora;
    }

    public void setIddistribuidora(int iddistribuidora) {
        this.iddistribuidora = iddistribuidora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFundacion() {
        return fundacion;
    }

    public void setFundacion(int fundacion) {
        this.fundacion = fundacion;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }
}
