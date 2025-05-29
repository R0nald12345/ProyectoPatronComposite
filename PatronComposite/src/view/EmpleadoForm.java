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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario para crear nuevos empleados individuales
 */
public class EmpleadoForm extends JDialog {
    private OrganizacionController controller;
    private MainFrame parentFrame;
    
    // Componentes del formulario
    private JTextField txtNombre;
    private JTextField txtCargo;
    private JTextField txtSalario;
    private JComboBox<String> comboDepartamento;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    public EmpleadoForm(MainFrame parent, OrganizacionController controller) {
        super(parent, "Agregar Nuevo Empleado", true);
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
        txtCargo = new JTextField(20);
        txtSalario = new JTextField(20);
        comboDepartamento = new JComboBox<>();
        
        btnGuardar = new JButton("💾 Guardar");
        btnCancelar = new JButton("❌ Cancelar");
        
        // Configurar estilos
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        
        txtNombre.setEditable(true);
        txtCargo.setEditable(true);
        txtSalario.setEditable(true);
        
        System.out.println("Nombre editable: " + txtNombre.isEditable());
System.out.println("Cargo editable: " + txtCargo.isEditable());
System.out.println("Salario editable: " + txtSalario.isEditable());

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
    JLabel lblTitulo = new JLabel("👤 Crear Nuevo Empleado");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
    lblTitulo.setForeground(new Color(63, 81, 181));
    gbc.gridx = 0; gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panelFormulario.add(lblTitulo, gbc);
    
    // Resetear configuración para los campos
    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL; // ← ESTO FALTABA
    gbc.weightx = 0.0; // Para las etiquetas
    
    // Campo Nombre
    gbc.gridx = 0; gbc.gridy = 1;
    gbc.weightx = 0.0;
    panelFormulario.add(new JLabel("Nombre:"), gbc);
    gbc.gridx = 1;
    gbc.weightx = 1.0; // ← ESTO FALTABA - para que el campo se expanda
    panelFormulario.add(txtNombre, gbc);
    
    // Campo Cargo
    gbc.gridx = 0; gbc.gridy = 2;
    gbc.weightx = 0.0;
    panelFormulario.add(new JLabel("Cargo:"), gbc);
    gbc.gridx = 1;
    gbc.weightx = 1.0;
    panelFormulario.add(txtCargo, gbc);
    
    // Campo Salario
    gbc.gridx = 0; gbc.gridy = 3;
    gbc.weightx = 0.0;
    panelFormulario.add(new JLabel("Salario:"), gbc);
    gbc.gridx = 1;
    gbc.weightx = 1.0;
    panelFormulario.add(txtSalario, gbc);
    
    // Campo Departamento
    gbc.gridx = 0; gbc.gridy = 4;
    gbc.weightx = 0.0;
    panelFormulario.add(new JLabel("Departamento:"), gbc);
    gbc.gridx = 1;
    gbc.weightx = 1.0;
    panelFormulario.add(comboDepartamento, gbc);
    
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
                guardarEmpleado();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Enter en cualquier campo ejecuta guardar
        ActionListener guardarAction = e -> guardarEmpleado();
        txtNombre.addActionListener(guardarAction);
        txtCargo.addActionListener(guardarAction);
        txtSalario.addActionListener(guardarAction);
    }
    
    /**
     * Configura las propiedades del diálogo
     */
    private void configurarDialogo() {
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
    }
    
    /**
     * Carga los departamentos disponibles en el combo
     */
    private void cargarDepartamentos() {
        comboDepartamento.addItem("Empresa Principal");
        // Aquí podrías agregar lógica para cargar departamentos existentes
        // Por ahora agregamos algunos predeterminados
        comboDepartamento.addItem("Desarrollo");
        comboDepartamento.addItem("Marketing");
    }
    
    /**
     * Valida y guarda el nuevo empleado
     */
    private void guardarEmpleado() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            String nombre = txtNombre.getText().trim();
            String cargo = txtCargo.getText().trim();
            double salario = Double.parseDouble(txtSalario.getText().trim());
            String departamento = (String) comboDepartamento.getSelectedItem();
            
            boolean exito = controller.agregarEmpleadoIndividual(departamento, nombre, cargo, salario);
            
            if (exito) {
                parentFrame.mostrarMensajeExito("Empleado agregado exitosamente!");
                dispose();
            } else {
                parentFrame.mostrarMensajeError("Error al agregar el empleado. Verifique que el departamento exista.");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El salario debe ser un número válido.", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Valida que todos los campos estén completos
     */
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarErrorValidacion("El nombre es obligatorio.");
            txtNombre.requestFocus();
            return false;
        }
        
        if (txtCargo.getText().trim().isEmpty()) {
            mostrarErrorValidacion("El cargo es obligatorio.");
            txtCargo.requestFocus();
            return false;
        }
        
        if (txtSalario.getText().trim().isEmpty()) {
            mostrarErrorValidacion("El salario es obligatorio.");
            txtSalario.requestFocus();
            return false;
        }
        
        try {
            double salario = Double.parseDouble(txtSalario.getText().trim());
            if (salario <= 0) {
                mostrarErrorValidacion("El salario debe ser mayor a 0.");
                txtSalario.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarErrorValidacion("El salario debe ser un número válido.");
            txtSalario.requestFocus();
            return false;
        }
        
        if (comboDepartamento.getSelectedItem() == null) {
            mostrarErrorValidacion("Debe seleccionar un departamento.");
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