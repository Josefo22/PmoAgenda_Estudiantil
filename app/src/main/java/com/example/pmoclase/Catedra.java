package com.example.pmoclase;

public class Catedra {
    private int id;
    private String nombre;
    private String horario;

    public Catedra() {
    }

    public Catedra(int id, String nombre, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.horario = horario;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nHorario: " + horario;
    }
}