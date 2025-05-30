/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author USER
 */

import model.*;
import view.MainFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.Map;



/**
 * Controlador que maneja la lógica entre la vista y el modelo
 * Implementa el patrón MVC para la aplicación Composite
 */
public class OrganizacionController {
    private MainFrame vista;
    private Departamento empresaPrincipal;
    private Map<String, Empleado> empleadosMap; // Para búsquedas rápidas
    
    public OrganizacionController() {
        this.empleadosMap = new HashMap<>();
        this.empresaPrincipal = new Departamento("Empresa Principal");
        this.vista = new MainFrame(this);
        
        // Datos de ejemplo para inicializar
        inicializarDatosEjemplo();
        actualizarVista();
    }
    
    //Verifico si ya exisite un departamento o empleado con el mismo nombre
    public boolean existeNombre(String nombre){
        return empleadosMap.containsKey(nombre);
    }
    
    /**
     * Agrega un empleado individual al departamento seleccionado
     */
    public boolean agregarEmpleadoIndividual(String nombreDepartamento, String nombre, String cargo, double salario) {
        try {
            EmpleadoIndividual nuevoEmpleado = new EmpleadoIndividual(nombre, cargo, salario);
            Departamento departamento = buscarDepartamento(nombreDepartamento);
            
            
            if (departamento != null) {
                departamento.agregarEmpleado(nuevoEmpleado);
                empleadosMap.put(nombre, nuevoEmpleado);
                actualizarVista();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Crea un nuevo departamento
     */
    public boolean crearDepartamento(String nombrePadre, String nombreNuevo) {
        try {
            Departamento nuevoDepartamento = new Departamento(nombreNuevo);
            
            if (nombrePadre == null || nombrePadre.equals("Empresa Principal")) {
                empresaPrincipal.agregarEmpleado(nuevoDepartamento);
            } else {
                Departamento padre = buscarDepartamento(nombrePadre);
                if (padre != null) {
                    padre.agregarEmpleado(nuevoDepartamento);
                } else {
                    return false;
                }
            }
            
            empleadosMap.put(nombreNuevo, nuevoDepartamento);
            actualizarVista();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Busca un departamento por nombre de forma recursiva
     */
    private Departamento buscarDepartamento(String nombre) {
        if (nombre.equals("Empresa Principal")) {
            return empresaPrincipal;
        }
        return buscarDepartamentoRecursivo(empresaPrincipal, nombre);
    }
    
    private Departamento buscarDepartamentoRecursivo(Departamento departamento, String nombre) {
        if (departamento.getNombre().equals(nombre)) {
            return departamento;
        }
        
        for (Empleado empleado : departamento.getEmpleados()) {
            if (empleado instanceof Departamento) {
                Departamento encontrado = buscarDepartamentoRecursivo((Departamento) empleado, nombre);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }
    
    /**
     * Obtiene información de un empleado/departamento
     */
    public String obtenerInformacionDetallada(String nombre) {
        Empleado empleado = empleadosMap.get(nombre);
        if (empleado == null) return "No encontrado";
        
        StringBuilder info = new StringBuilder();
        if (empleado instanceof EmpleadoIndividual) {
            EmpleadoIndividual emp = (EmpleadoIndividual) empleado;
            info.append("👤 EMPLEADO INDIVIDUAL\n");
            info.append("Nombre: ").append(emp.getNombre()).append("\n");
            info.append("Cargo: ").append(emp.getCargo()).append("\n");
            info.append("Salario: $").append(emp.getSalario()).append("\n");
        } else if (empleado instanceof Departamento) {
            Departamento dept = (Departamento) empleado;
            info.append("🏢 DEPARTAMENTO\n");
            info.append("Nombre: ").append(dept.getNombre()).append("\n");
            info.append("Total Empleados: ").append(dept.getTotalEmpleados()).append("\n");
            info.append("Salario Total: $").append(dept.getSalario()).append("\n");
        }
        return info.toString();
    }
    
    /**
     * Construye el modelo del árbol para la vista
     */
    public DefaultTreeModel construirModeloArbol() {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("🏢 " + empresaPrincipal.getNombre());
        construirNodosRecursivo(raiz, empresaPrincipal);
        return new DefaultTreeModel(raiz);
    }
    
    private void construirNodosRecursivo(DefaultMutableTreeNode nodo, Departamento departamento) {
        for (Empleado empleado : departamento.getEmpleados()) {
            if (empleado instanceof Departamento) {
                Departamento dept = (Departamento) empleado;
                DefaultMutableTreeNode nodoHijo = new DefaultMutableTreeNode("📁 " + dept.getNombre());
                nodo.add(nodoHijo);
                construirNodosRecursivo(nodoHijo, dept);
            } else if (empleado instanceof EmpleadoIndividual) {
                EmpleadoIndividual emp = (EmpleadoIndividual) empleado;
                DefaultMutableTreeNode nodoHijo = new DefaultMutableTreeNode(
                    "👤 " + emp.getNombre() + " (" + emp.getCargo() + ")"
                );
                nodo.add(nodoHijo);
            }
        }
    }
    
    /**
     * Actualiza la vista con los datos actuales
     */
    private void actualizarVista() {
        vista.actualizarArbol();
        vista.actualizarResumen(
            contarTotalEmpleados(),
            empresaPrincipal.getSalario()
        );
    }
    
    private int contarTotalEmpleados() {
        return contarEmpleadosRecursivo(empresaPrincipal);
    }
    
    private int contarEmpleadosRecursivo(Departamento dept) {
        int total = 0;
        for (Empleado emp : dept.getEmpleados()) {
            if (emp instanceof EmpleadoIndividual) {
                total++;
            } else if (emp instanceof Departamento) {
                total += contarEmpleadosRecursivo((Departamento) emp);
            }
        }
        return total;
    }
    
    /**
     * Inicializa con algunos datos de ejemplo
     */
    private void inicializarDatosEjemplo() {
        // Crear departamentos
        Departamento desarrollo = new Departamento("Desarrollo");
        Departamento marketing = new Departamento("Marketing");
        
        // Crear empleados
        EmpleadoIndividual juan = new EmpleadoIndividual("Juan Pérez", "Desarrollador", 3000);
        EmpleadoIndividual maria = new EmpleadoIndividual("María García", "Diseñadora", 2800);
        EmpleadoIndividual ana = new EmpleadoIndividual("Ana López", "Marketing Manager", 3500);
        
        // Construir jerarquía
        desarrollo.agregarEmpleado(juan);
        desarrollo.agregarEmpleado(maria);
        marketing.agregarEmpleado(ana);
        
        empresaPrincipal.agregarEmpleado(desarrollo);
        empresaPrincipal.agregarEmpleado(marketing);
        
        // Agregar al mapa para búsquedas
        empleadosMap.put("Desarrollo", desarrollo);
        empleadosMap.put("Marketing", marketing);
        empleadosMap.put("Juan Pérez", juan);
        empleadosMap.put("María García", maria);
        empleadosMap.put("Ana López", ana);
    }
    
    /**
     * Inicia la aplicación
     */
    public void iniciar() {
        vista.setVisible(true);
    }
    
    // Getters para la vista
    public Departamento getEmpresaPrincipal() { return empresaPrincipal; }
}