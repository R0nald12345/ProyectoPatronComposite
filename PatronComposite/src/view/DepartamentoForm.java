/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author USER
 */
import model.*;
import controller.OrganizacionController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario para crear nuevos departamentos
 */
public class DepartamentoForm extends JDialog {

    private OrganizacionController controller;
    private MainFrame parentFrame;

    // Componentes del formulario
    private JTextField txtNombre;
    private JComboBox<String> comboDepartamentoPadre;
    private JTextArea txtDescripcion;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public DepartamentoForm(MainFrame parent, OrganizacionController controller) {
        super(parent, "Crear Nuevo Departamento", true);
        this.parentFrame = parent;
        this.controller = controller;

        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarDialogo();
        cargarDepartamentos();
    }

    /**
     * Inicializa los componentes del formulario
     */
    private void inicializarComponentes() {
        txtNombre = new JTextField(20);
        comboDepartamentoPadre = new JComboBox<>();
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        btnGuardar = new JButton("🏢 Crear Departamento");
        btnCancelar = new JButton("❌ Cancelar");

        // Configurar estilos
        btnGuardar.setBackground(new Color(33, 150, 243));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));

        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
    }

    /**
     * Configura el layout del formulario
     */
    private void configurarLayout() {
        setLayout(new BorderLayout());

        // Panel principal con el formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        JLabel lblTitulo = new JLabel("🏢 Crear Nuevo Departamento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(lblTitulo, gbc);

        // Resetear configuración
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campo Nombre del Departamento
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre del Departamento:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);

        // Campo Departamento Padre
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Departamento Padre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboDepartamentoPadre, gbc);

        // Campo Descripción
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelFormulario.add(new JLabel("Descripción (opcional):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setBorder(BorderFactory.createLoweredBevelBorder());
        panelFormulario.add(scrollDescripcion, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Configura los eventos de los componentes
     */
    private void configurarEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearDepartamento();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Enter en el campo nombre ejecuta crear
        txtNombre.addActionListener(e -> crearDepartamento());
    }

    /**
     * Configura las propiedades del diálogo
     */
    private void configurarDialogo() {
        setSize(450, 350);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
    }

    /**
     * Carga los departamentos disponibles como padres
     */
    private void cargarDepartamentos() {
        comboDepartamentoPadre.removeAllItems(); // Limpiar items existentes
        comboDepartamentoPadre.addItem("Empresa Principal");

        // Cargar departamentos dinámicamente desde el controlador
        cargarDepartamentosRecursivo(controller.getEmpresaPrincipal());
    }

    /**
     * Mtodo aux para cargar departamento de forma recursiva
     */
    private void cargarDepartamentosRecursivo(Departamento departamento) {
        for (Empleado empleado : departamento.getEmpleados()) {
            if (empleado instanceof Departamento) {
                Departamento dept = (Departamento) empleado;
                comboDepartamentoPadre.addItem(dept.getNombre());
                // Recursión para subdepartamentos
                cargarDepartamentosRecursivo(dept);
            }
        }
    }
        /**
         * Valida y crea el nuevo departamento
         */
    private void crearDepartamento() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String departamentoPadre = (String) comboDepartamentoPadre.getSelectedItem();

        boolean exito = controller.crearDepartamento(departamentoPadre, nombre);

        if (exito) {
            parentFrame.mostrarMensajeExito("¡Departamento '" + nombre + "' creado exitosamente!");
            dispose();
        } else {
            parentFrame.mostrarMensajeError("Error al crear el departamento. Verifique que no exista ya un departamento con ese nombre.");
        }
    }

    /**
     * Valida que todos los campos obligatorios estén completos
     */
    private boolean validarCampos() {
        // Validar nombre del departamento
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarErrorValidacion("El nombre del departamento es obligatorio.");
            txtNombre.requestFocus();
            return false;
        }

        // Validar que el nombre no sea muy corto
        if (txtNombre.getText().trim().length() < 3) {
            mostrarErrorValidacion("El nombre del departamento debe tener al menos 3 caracteres.");
            txtNombre.requestFocus();
            return false;
        }

        //valido que no exista ya un depatamento con ese nombre
        String nombreDepartamento = txtNombre.getText().trim();
        if(controller.existeNombre(nombreDepartamento)){
            mostrarErrorValidacion("'Ya existe un departamento o empleado con ese nombre' "+ nombreDepartamento);
            txtNombre.requestFocus();
            return false;
        }
        
        
        // Validar selección de departamento padre
        if (comboDepartamentoPadre.getSelectedItem() == null) {
            mostrarErrorValidacion("Debe seleccionar un departamento padre.");
            comboDepartamentoPadre.requestFocus();
            return false;
        }
        
      

        return true;
    }

    /**
     * Muestra un mensaje de error de validación
     */
    private void mostrarErrorValidacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.WARNING_MESSAGE);
    }
}
