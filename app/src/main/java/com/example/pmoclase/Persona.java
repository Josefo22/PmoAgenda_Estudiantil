package com.example.pmoclase;

public class Persona {
    private int id;
    private String nombres;
    private String apellidos;
    private String documento;
    private String correo;

    public Persona() {
    }

    public Persona(int id, String nombres, String apellidos, String documento, String correo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.correo = correo;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Nombres: " + nombres + " " + apellidos +
                "\nDocumento: " + documento +
                "\nCorreo: " + correo;
    }
}