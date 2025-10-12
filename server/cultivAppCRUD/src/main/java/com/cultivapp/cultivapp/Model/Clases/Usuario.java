package com.cultivapp.cultivapp.Model.Clases;


import com.cultivapp.cultivapp.Model.Util.UsuarioManager;

public class Usuario {
    private int  id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private int telefono;
    private String ciudad;

    private UsuarioManager.Rol rol;


    public Usuario(String nombre, String apellido, String email, String contrasena, int telefono, UsuarioManager.Rol rol,String ciudad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.rol = rol;


        // this.rol = rol;
    }

    public UsuarioManager.Rol getRol() {
        return rol;
    }
}
