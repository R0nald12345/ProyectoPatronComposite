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
import javax.swing.JOptionPane;

/**
 * Controlador que maneja la lógica entre la vista y el modelo Implementa el
 * patrón MVC para la aplicación Composite
 */
public class OrganizacionController {

    private MainFrame vista;
    private Departamento empresaPrincipal;
    private Map<String, UnidadOrganizacional> empleadosMap; // Para búsquedas rápidas

    public OrganizacionController() {
        this.empleadosMap = new HashMap<>();
        this.empresaPrincipal = new Departamento("Empresa Principal");
        this.vista = new MainFrame(this);

        // Datos de ejemplo para inicializar
        inicializarDatosEjemplo();
        actualizarVista();
    }

    //Verifico si ya exisite un departamento o empleado con el mismo nombre
    public boolean existeNombre(String nombre) {
        return empleadosMap.containsKey(nombre);
    }

    /**
     * Agrega un empleado individual al departamento seleccionado
     */
    public boolean agregarEmpleadoIndividual(String nombreDepartamento, String nombre, String cargo, double salario) {
        try {
            Empleado nuevoEmpleado = new Empleado(nombre, cargo, salario);
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

        for (UnidadOrganizacional empleado : departamento.getEmpleados()) {
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
        // Primero buscar en el mapa
        UnidadOrganizacional empleado = empleadosMap.get(nombre);

        // Si no está en el mapa, buscar recursivamente
        if (empleado == null) {
            empleado = buscarEmpleadoRecursivo(empresaPrincipal, nombre);
        }

        if (empleado != null) {
            // ¡Aquí usamos el método de la interfaz!
            return empleado.mostrarInfo();
        }

        return "❌ No se encontró información para: " + nombre;
    }

    /**
     * Método auxiliar para buscar empleados recursivamente
     */
    private UnidadOrganizacional buscarEmpleadoRecursivo(Departamento departamento, String nombre) {
        // Verificar si el departamento actual es el buscado
        if (departamento.getNombre().equals(nombre)) {
            return departamento;
        }

        // Buscar en los empleados del departamento
        for (UnidadOrganizacional empleado : departamento.getEmpleados()) {
            if (empleado.getNombre().equals(nombre)) {
                return empleado;
            }

            // Si es un departamento, buscar recursivamente
            if (empleado instanceof Departamento) {
                UnidadOrganizacional encontrado = buscarEmpleadoRecursivo((Departamento) empleado, nombre);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }

        return null;
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
        for (UnidadOrganizacional empleado : departamento.getEmpleados()) {
            if (empleado instanceof Departamento) {
                Departamento dept = (Departamento) empleado;
                DefaultMutableTreeNode nodoHijo = new DefaultMutableTreeNode("📁 " + dept.getNombre());
                nodo.add(nodoHijo);
                construirNodosRecursivo(nodoHijo, dept);
            } else if (empleado instanceof Empleado) {
                Empleado emp = (Empleado) empleado;
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
        for (UnidadOrganizacional emp : dept.getEmpleados()) {
            if (emp instanceof Empleado) {
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
        Empleado juan = new Empleado("Juan Pérez", "Desarrollador", 3000);
        Empleado maria = new Empleado("María García", "Diseñadora", 2800);
        Empleado ana = new Empleado("Ana López", "Marketing Manager", 3500);

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
    public Departamento getEmpresaPrincipal() {
        return empresaPrincipal;
    }

    /**
     * Remueve un empleado individual del sistema
     */
    public boolean removerEmpleado(String nombreEmpleado) {
        try {
            UnidadOrganizacional empleado = empleadosMap.get(nombreEmpleado);

            if (empleado == null || !(empleado instanceof Empleado)) {
                return false;
            }

            // Buscar en qué departamento está
            Departamento departamentoPadre = buscarDepartamentoPadre(empleado);

            if (departamentoPadre != null) {
                departamentoPadre.removerEmpleado(empleado);
                empleadosMap.remove(nombreEmpleado);
                actualizarVista();
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Remueve un departamento completo (incluyendo todo su contenido)
     */
    public boolean removerDepartamento(String nombreDepartamento) {
        try {
            if (nombreDepartamento.equals("Empresa Principal")) {
                return false; // No se puede eliminar la raíz
            }

            Departamento departamento = buscarDepartamento(nombreDepartamento);

            if (departamento == null) {
                return false;
            }

            // Confirmar eliminación (porque es destructiva)
            int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "⚠️ ADVERTENCIA: Esto eliminará el departamento '" + nombreDepartamento
                    + "' y TODOS sus empleados y sub-departamentos.\n\n"
                    + "Total de elementos que se eliminarán: " + contarElementosRecursivo(departamento)
                    + "\n\n¿Está seguro de continuar?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return false;
            }

            // Remover recursivamente del mapa
            removerDelMapaRecursivo(departamento);

            // Buscar departamento padre y remover
            Departamento padre = buscarDepartamentoPadre(departamento);
            if (padre != null) {
                padre.removerEmpleado(departamento);
                actualizarVista();
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Busca el departamento padre de un empleado dado
     */
    private Departamento buscarDepartamentoPadre(UnidadOrganizacional empleadoBuscado) {
        return buscarDepartamentoPadreRecursivo(empresaPrincipal, empleadoBuscado);
    }

    private Departamento buscarDepartamentoPadreRecursivo(Departamento departamento, UnidadOrganizacional empleadoBuscado) {
        // Verificar si está en este departamento
        for (UnidadOrganizacional empleado : departamento.getEmpleados()) {
            if (empleado == empleadoBuscado) {
                return departamento; // Encontrado, este es el padre
            }

            // Si es un departamento, buscar recursivamente
            if (empleado instanceof Departamento) {
                Departamento encontrado = buscarDepartamentoPadreRecursivo((Departamento) empleado, empleadoBuscado);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }

        return null;
    }

    /**
     * Cuenta total de elementos que se eliminarían (para advertencia)
     */
    private int contarElementosRecursivo(Departamento departamento) {
        int count = 1; // El departamento mismo

        for (UnidadOrganizacional empleado : departamento.getEmpleados()) {
            if (empleado instanceof Empleado) {
                count++;
            } else if (empleado instanceof Departamento) {
                count += contarElementosRecursivo((Departamento) empleado);
            }
        }

        return count;
    }

    /**
     * Remueve todos los elementos del mapa recursivamente
     */
    private void removerDelMapaRecursivo(Departamento departamento) {
        // Remover el departamento del mapa
        empleadosMap.remove(departamento.getNombre());

        // Remover todos sus hijos
        for (UnidadOrganizacional empleado : departamento.getEmpleados()) {
            empleadosMap.remove(empleado.getNombre());

            if (empleado instanceof Departamento) {
                removerDelMapaRecursivo((Departamento) empleado);
            }
        }
    }
}
