/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
// Main.java
import controller.OrganizacionController;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase principal que inicia la aplicación del Patrón Composite Seguro
 * Implementa un Sistema de Organización de Empleados con interfaz gráfica
 */
public class Main {
    
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        configurarLookAndFeel();
        
        // Ejecutar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                iniciarAplicacion();
            }
        });
    }
    
    /**
     * Configura el Look and Feel para que se vea nativo del sistema operativo
     */
    private static void configurarLookAndFeel() {
        try {
            // Usar el Look and Feel nativo del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Si falla, usar el Look and Feel por defecto
            System.err.println("No se pudo configurar el Look and Feel del sistema: " + e.getMessage());
            System.err.println("Usando Look and Feel por defecto.");
        }
    }
    
    /**
     * Inicia la aplicación creando el controlador principal
     */
    private static void iniciarAplicacion() {
        try {
            System.out.println("=== INICIANDO APLICACIÓN COMPOSITE SEGURO ===");
            System.out.println("🏢 Sistema de Organización de Empleados");
            System.out.println("📋 Patrón: Composite Seguro (Safe Composite)");
            System.out.println("🎨 Interfaz: Java Swing");
            System.out.println("🏗️ Arquitectura: MVC (Model-View-Controller)");
            System.out.println("=".repeat(50));
            
            // Crear y iniciar el controlador (que creará la vista)
            OrganizacionController controller = new OrganizacionController();
            controller.iniciar();
            
            System.out.println("✅ Aplicación iniciada exitosamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}