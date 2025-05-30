/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 *
 * @author USER
 */
public interface UnidadOrganizacional {
    /**
     * Obtiene el salario total (individual o suma del departamento)
     * @return salario total
     */
    double getSalario();
    
    /**
     * Muestra la información del empleado o departamento
     * @return información formateada como String para la GUI
     */
    String mostrarInfo();
    
    /**
     * Obtiene el nombre del empleado o departamento
     * @return nombre
     */
    String getNombre();
}
