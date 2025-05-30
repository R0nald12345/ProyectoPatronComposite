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
public class Empleado implements UnidadOrganizacional {
    private String nombre;
    private String cargo;
    private double salario;
    
    public Empleado(String nombre, String cargo, double salario) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }
    
    @Override
    public double getSalario() {
        return salario;
    }
    
    @Override
    public String getNombre() { 
        return nombre; 
    }
    
    public String getCargo() { 
        return cargo; 
    }
    
    @Override
    public String mostrarInfo() {
        StringBuilder info = new StringBuilder();
        info.append("👤 EMPLEADO INDIVIDUAL\n");
        info.append("====================\n");
        info.append("Nombre: ").append(nombre).append("\n");
        info.append("Cargo: ").append(cargo).append("\n");
        info.append("Salario: $").append(String.format("%.2f", salario)).append("\n");
        info.append("====================");
        return info.toString();
    }
}