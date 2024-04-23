package com.example.demo.entity;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name  ="ciudadanos")
public class Ciudadano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name = "f_nacimiento")
    private java.sql.Date f_nacimiento;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domicilio> domicilios;


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Date getF_nacimiento() {
        return f_nacimiento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setF_nacimiento(Date f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(List<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }
}
