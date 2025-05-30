/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
// ================================
// COMPUESTO (Composite)
// ================================
/**
 * Representa un departamento que puede contener empleados y otros departamentos.
 * Es el COMPUESTO en el patrón. Solo esta clase tiene métodos para manejar hijos.
 * Esto hace el patrón "SEGURO" - solo quien puede tener hijos, los maneja.
 */
public class Departamento implements Empleado {
    private String nombre;
    private List<Empleado> empleados; // Lista de empleados (individuales o departamentos)
    
    /**
     * Constructor para crear un departamento
     */
    public Departamento(String nombre) {
        this.nombre = nombre;
        this.empleados = new ArrayList<>();
    }
    
    // ================================
    // MÉTODOS ESPECÍFICOS DEL COMPUESTO
    // Estos métodos SOLO existen aquí, no en la interfaz base
    // ================================
    
    /**
     * Agrega un empleado (individual o departamento) al departamento actual
     * NOTA: Este método solo existe en Departamento, no en EmpleadoIndividual
     */
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        System.out.println("✅ Agregado al departamento " + nombre);
    }
    
    /**
     * Remueve un empleado del departamento
     */
    public void removerEmpleado(Empleado empleado) {
        if (empleados.remove(empleado)) {
            System.out.println("❌ Empleado removido del departamento " + nombre);
        } else {
            System.out.println("⚠️ Empleado no encontrado en " + nombre);
        }
    }
    
    /**
     * Obtiene la lista de empleados del departamento
     */
    public List<Empleado> getEmpleados() {
        return new ArrayList<>(empleados); // Retorna copia para evitar modificaciones externas
    }
    
    /**
     * Obtiene el número de empleados (incluyendo subdepartamentos)
     */
    public int getTotalEmpleados() {
        return empleados.size();
    }
    
    // ================================
    // IMPLEMENTACIÓN DE MÉTODOS COMUNES
    // ================================
    
    
    /**
     * Calcula el salario total sumando los salarios de todos los empleados
     * Comportamiento COMPUESTO - suma recursivamente
     */
    @Override
    public double getSalario() {
        double salarioTotal = 0;
        // Suma recursivamente los salarios de todos los empleados
        for (Empleado empleado : empleados) {
            salarioTotal += empleado.getSalario();
        }
        return salarioTotal;
    }
    
    public String getNombre() { return nombre; }
}
