/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author USER
 */
import controller.OrganizacionController;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal de la aplicación Composite Pattern Muestra la jerarquía
 * organizacional en forma de árbol
 */
public class MainFrame extends JFrame {

    private OrganizacionController controller;

    // Componentes de la interfaz
    private JTree arbolOrganizacion;
    private JTextArea areaInformacion;
    private JLabel labelTotalEmpleados;
    private JLabel labelSalarioTotal;
    private JButton btnAgregarEmpleado;
    private JButton btnCrearDepartamento;
    private JButton btnMostrarInfo;

    private JButton btnRemoverEmpleado;
    private JButton btnRemoverDepartamento;

    public MainFrame(OrganizacionController controller) {
        this.controller = controller;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarVentana();
    }

    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Árbol para mostrar la jerarquía
        arbolOrganizacion = new JTree();
        arbolOrganizacion.setRootVisible(true);
        arbolOrganizacion.setShowsRootHandles(true);

        // Área de información detallada
        areaInformacion = new JTextArea(10, 30);
        areaInformacion.setEditable(false);
        areaInformacion.setBackground(new Color(248, 248, 248));
        areaInformacion.setBorder(BorderFactory.createTitledBorder("Información Detallada"));

        // Labels para resumen
        labelTotalEmpleados = new JLabel("Total Empleados: 0");
        labelSalarioTotal = new JLabel("Salario Total: $0.00");

        // Botones de acción
        btnAgregarEmpleado = new JButton("➕ Agregar Empleado");
        btnCrearDepartamento = new JButton("🏢 Crear Departamento");
        btnMostrarInfo = new JButton("ℹ️ Mostrar Información");

        btnRemoverEmpleado = new JButton("❌ Remover Empleado");
        btnRemoverDepartamento = new JButton("🗑️ Remover Departamento");

        configurarEstiloBoton(btnRemoverEmpleado, new Color(244, 67, 54));
        configurarEstiloBoton(btnRemoverDepartamento, new Color(156, 39, 176));

        // Configurar estilos de botones
        configurarEstiloBoton(btnAgregarEmpleado, new Color(76, 175, 80));
        configurarEstiloBoton(btnCrearDepartamento, new Color(33, 150, 243));
        //configurarEstiloBoton(btnMostrarInfo, new Color(255, 152, 0));
    }

    /**
     * Configura el estilo de los botones
     */
    private void configurarEstiloBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Configura el layout de la ventana
     */
    private void configurarLayout() {
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(63, 81, 181));
        JLabel titulo = new JLabel("🏢 Sistema de Organización - Patrón Composite Seguro");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con árbol
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Árbol en scroll pane
        JScrollPane scrollArbol = new JScrollPane(arbolOrganizacion);
        scrollArbol.setBorder(BorderFactory.createTitledBorder("Jerarquía Organizacional"));
        scrollArbol.setPreferredSize(new Dimension(400, 300));

        // Área de información en scroll pane  
        JScrollPane scrollInfo = new JScrollPane(areaInformacion);
        scrollInfo.setPreferredSize(new Dimension(350, 300));

        // Split pane para dividir árbol e información
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollArbol, scrollInfo);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        panelCentral.add(splitPane, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botones y resumen
        JPanel panelInferior = new JPanel(new BorderLayout());

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregarEmpleado);
        panelBotones.add(btnCrearDepartamento);
        //panelBotones.add(btnMostrarInfo);
        
        panelBotones.add(btnRemoverEmpleado);
        panelBotones.add(btnRemoverDepartamento);
        



        // Panel de resumen
        JPanel panelResumen = new JPanel(new FlowLayout());
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen"));
        panelResumen.add(labelTotalEmpleados);
        panelResumen.add(new JLabel("  |  "));
        panelResumen.add(labelSalarioTotal);

        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(panelResumen, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);
        
        
    }

    /**
     * Configura los eventos de los componentes
     */
    private void configurarEventos() {
        // Evento para agregar empleado
        btnAgregarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioEmpleado();
            }
        });

        // Evento para crear departamento
        btnCrearDepartamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioDepartamento();
            }
        });

        // Evento para mostrar información
        btnMostrarInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInformacionSeleccionada();
            }
        });

        // Evento de selección en el árbol
        arbolOrganizacion.addTreeSelectionListener(e -> {
            mostrarInformacionSeleccionada();
        });

        btnRemoverEmpleado.addActionListener(e -> removerEmpleadoSeleccionado());
        btnRemoverDepartamento.addActionListener(e -> removerDepartamentoSeleccionado());
    }

    /**
     * Remueve el empleado seleccionado del árbol
     */
    private void removerEmpleadoSeleccionado() {
        DefaultMutableTreeNode nodoSeleccionado
                = (DefaultMutableTreeNode) arbolOrganizacion.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            mostrarMensajeError("Seleccione un empleado del árbol.");
            return;
        }

        String textoNodo = nodoSeleccionado.toString();

        // Solo remover empleados individuales
        if (!textoNodo.startsWith("👤 ")) {
            mostrarMensajeError("Solo se pueden remover empleados individuales. Para departamentos use 'Remover Departamento'.");
            return;
        }

        String nombreEmpleado = extraerNombreDeNodo(textoNodo);

        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea remover al empleado '" + nombreEmpleado + "'?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controller.removerEmpleado(nombreEmpleado);

            if (exito) {
                mostrarMensajeExito("Empleado '" + nombreEmpleado + "' removido exitosamente.");
            } else {
                mostrarMensajeError("Error al remover el empleado.");
            }
        }
    }

    /**
     * Remueve el departamento seleccionado del árbol
     */
    private void removerDepartamentoSeleccionado() {
        DefaultMutableTreeNode nodoSeleccionado
                = (DefaultMutableTreeNode) arbolOrganizacion.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            mostrarMensajeError("Seleccione un departamento del árbol.");
            return;
        }

        String textoNodo = nodoSeleccionado.toString();

        // Solo remover departamentos (no empleados individuales)
        if (textoNodo.startsWith("👤 ")) {
            mostrarMensajeError("Seleccione un departamento. Para empleados use 'Remover Empleado'.");
            return;
        }

        String nombreDepartamento = extraerNombreDeNodo(textoNodo);

        // El controlador ya maneja la confirmación
        boolean exito = controller.removerDepartamento(nombreDepartamento);

        if (exito) {
            mostrarMensajeExito("Departamento removido exitosamente.");
        } else {
            mostrarMensajeError("Error al remover el departamento o operación cancelada.");
        }
    }

    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle("Patrón Composite Seguro - Sistema Organizacional");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setMinimumSize(new Dimension(700, 500));
    }

    /**
     * Muestra el formulario para agregar un nuevo empleado
     */
    private void mostrarFormularioEmpleado() {
        EmpleadoForm formulario = new EmpleadoForm(this, controller);
        formulario.setVisible(true);
    }

    /**
     * Muestra el formulario para crear un nuevo departamento
     */
    private void mostrarFormularioDepartamento() {
        DepartamentoForm formulario = new DepartamentoForm(this, controller);
        formulario.setVisible(true);
    }

    /**
     * Muestra información detallada del elemento seleccionado
     */
    private void mostrarInformacionSeleccionada() {
        DefaultMutableTreeNode nodoSeleccionado
                = (DefaultMutableTreeNode) arbolOrganizacion.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            areaInformacion.setText("Seleccione un elemento del árbol para ver su información.");
            return;
        }

        String textoNodo = nodoSeleccionado.toString();

        // Extraer el nombre limpio del nodo
        String nombre = extraerNombreDeNodo(textoNodo);

        // Obtener información del controlador
        String informacion = controller.obtenerInformacionDetallada(nombre);
        areaInformacion.setText(informacion);
    }

    /**
     * Extrae el nombre limpio del texto del nodo del árbol
     */
    private String extraerNombreDeNodo(String textoNodo) {
        // Remover emojis y paréntesis
        String nombre = textoNodo;

        if (nombre.startsWith("🏢 ")) {
            nombre = nombre.substring(3).trim();
        } else if (nombre.startsWith("📁 ")) {
            nombre = nombre.substring(3).trim();
        } else if (nombre.startsWith("👤 ")) {
            nombre = nombre.substring(3).trim();
            // Si tiene paréntesis, tomar solo la parte antes del paréntesis
            int indiceParentesis = nombre.indexOf(" (");
            if (indiceParentesis > 0) {
                nombre = nombre.substring(0, indiceParentesis).trim();
            }
        }

        return nombre;
    }

    /**
     * Actualiza el árbol con los datos actuales
     */
    public void actualizarArbol() {
        DefaultTreeModel modelo = controller.construirModeloArbol();
        arbolOrganizacion.setModel(modelo);

        // Expandir todos los nodos
        for (int i = 0; i < arbolOrganizacion.getRowCount(); i++) {
            arbolOrganizacion.expandRow(i);
        }
    }

    /**
     * Actualiza el resumen con totales
     */
    public void actualizarResumen(int totalEmpleados, double salarioTotal) {
        labelTotalEmpleados.setText("Total Empleados: " + totalEmpleados);
        labelSalarioTotal.setText(String.format("Salario Total: $%.2f", salarioTotal));
    }

    /**
     * Muestra un mensaje de éxito
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInformacionCompleta() {
        DefaultMutableTreeNode nodoSeleccionado
                = (DefaultMutableTreeNode) arbolOrganizacion.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            mostrarMensajeError("Seleccione un elemento del árbol.");
            return;
        }

        String nombre = extraerNombreDeNodo(nodoSeleccionado.toString());

        // Usar el método mostrarInfo() a través del controlador
        String informacionCompleta = controller.obtenerInformacionDetallada(nombre);

        // Mostrar en un diálogo separado para mejor visualización
        JDialog dialogoInfo = new JDialog(this, "Información Completa", true);
        JTextArea areaTexto = new JTextArea(informacionCompleta);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Courier New", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(400, 300));

        dialogoInfo.add(scroll);
        dialogoInfo.pack();
        dialogoInfo.setLocationRelativeTo(this);
        dialogoInfo.setVisible(true);
    }
}
