package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name  ="domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "codigoPostal")
    private String codigoPostal;

    @Column(name = "pais", nullable = false)
    private String pais;

    @ManyToOne
    @JoinColumn(name = "ciudadano_id", nullable = false)
    private Ciudadano ciudadano;

    public Long getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }
}
