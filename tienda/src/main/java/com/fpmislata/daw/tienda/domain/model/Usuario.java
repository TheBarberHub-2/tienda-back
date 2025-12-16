package com.fpmislata.daw.tienda.domain.model;

import com.fpmislata.daw.tienda.enums.Rol;

public class Usuario {

    private Long id;
    private String email;
    private String nombre;
    private String contrasenya;
    private Rol rol;

    public Usuario(Long id, String email, String nombre, String contrasenya, Rol rol) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.contrasenya = contrasenya;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
