/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author USER
 */
// ================================
// HOJA (Leaf)
// ================================
/**
 * Representa un empleado individual - es una HOJA en el patrón Composite.
 * No tiene métodos para manejar hijos porque no puede contener otros empleados.
 * Esto es lo que hace "SEGURO" al patrón.
 */
public class EmpleadoIndividual implements Empleado {
    private String nombre;
    private String cargo;
    private double salario;
    
    /**
     * Constructor para crear un empleado individual
     */
    public EmpleadoIndividual(String nombre, String cargo, double salario) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }
    
    /**
     * Implementación específica para mostrar info de empleado individual
     */
    @Override
    public void mostrarInfo() {
        System.out.println("👤 Empleado: " + nombre + 
                          " | Cargo: " + cargo + 
                          " | Salario: $" + salario);
    }
    
    /**
     * Retorna el salario del empleado individual
     */
    @Override
    public double getSalario() {
        return salario;
    }
    
    // Getters adicionales para acceso a propiedades específicas
    public String getNombre() { return nombre; }
    public String getCargo() { return cargo; }
}