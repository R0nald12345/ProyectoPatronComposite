/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 *
 * @author USER
 */
public interface Empleado {
     /**
     * Muestra la información del empleado o departamento
     */
    void mostrarInfo();
    
    /**
     * Obtiene el salario total (individual o suma del departamento)
     * @return salario total
     */
    double getSalario();
}

