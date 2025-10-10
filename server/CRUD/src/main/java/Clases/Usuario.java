package Clases;

import Servicios.UsuarioServicio;

public class Usuario {
    private int  id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private int telefono;
    private UsuarioServicio.Rol rol;


    public Usuario(String nombre, String apellido, String email, String contrasena, int telefono, UsuarioServicio.Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.rol = rol;
    }

    public UsuarioServicio.Rol getRol() {
        return rol;
    }

}
