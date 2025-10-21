package com.cultivapp.cultivapp.model;

import java.time.LocalDateTime;

import com.cultivapp.cultivapp.model.enums.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// Usuario entity: represents a system user (admin or producer)
// Enum stored as string to keep database portable
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    // Password stored as BCrypt hash, never plain text
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String apellido;
    
    private String ciudad;
    
    @Column(length = 20)
    private String telefono;
    
    // Enum stored as string for readability and portability
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
    
    @Column(nullable = false)
    private Boolean activo;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    // Automatically set registration date before saving
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
    
    // No-arg constructor
    public Usuario() {}
    
    // All-args constructor
    public Usuario(Integer id, String email, String password, String nombre, String apellido, 
                   String ciudad, String telefono, Rol rol, Boolean activo, LocalDateTime fechaRegistro) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.rol = rol;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Builder pattern
    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }
    
    public static class UsuarioBuilder {
        private Integer id;
        private String email;
        private String password;
        private String nombre;
        private String apellido;
        private String ciudad;
        private String telefono;
        private Rol rol;
        private Boolean activo;
        private LocalDateTime fechaRegistro;
        
        public UsuarioBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        
        public UsuarioBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public UsuarioBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public UsuarioBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        
        public UsuarioBuilder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }
        
        public UsuarioBuilder ciudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }
        
        public UsuarioBuilder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        
        public UsuarioBuilder rol(Rol rol) {
            this.rol = rol;
            return this;
        }
        
        public UsuarioBuilder activo(Boolean activo) {
            this.activo = activo;
            return this;
        }
        
        public UsuarioBuilder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }
        
        public Usuario build() {
            return new Usuario(id, email, password, nombre, apellido, ciudad, telefono, rol, activo, fechaRegistro);
        }
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}