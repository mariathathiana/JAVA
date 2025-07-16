package gc.concesionariodb.GUI;

import gc.concesionariodb.modelo.coche;
import gc.concesionariodb.servicio.ICocheServicio;
import gc.concesionariodb.servicio.CocheServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gc.concesionariodb.modelo.ValidadorCampos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


@Component
public class ConcesionarioForm extends JFrame {
  private JPanel PanelPrincipal;
  private JPanel PanelInicial;
  private JPanel Encabezado;
  private JPanel Pie;
  private JPanel titulomodelo;
  private JPanel UbicacionTabla;
  private JTextField introducirMarca;
  private JTextField introducirModelo;
  private JTextField introducirUnidades;
  private JTextField introducirPrecio;
  private JTextField getIntroducirMatricula;
  private JButton guardarBoton;
  private JButton eliminarBoton;
  private JButton limpiarBoton;
  private JTable cochesTabla;
  private JScrollPane scrollPane1;
  private JTextField introducirMatricula;
  private JLabel titulomatricula;
  private JLabel tituloprecio;
  private JLabel titulounidades;
  private JLabel titulomarca;
  private DefaultTableModel tablaModeloCoches;
  private Integer idCoche;
  private JLabel avisoMatricula;

  ICocheServicio cocheServicio;

  @Autowired
  public ConcesionarioForm(CocheServicio f_cocheServicio) {
    this.cocheServicio = f_cocheServicio;
    iniciarForma();

    guardarBoton.addActionListener(e -> guardarCoche());

    scrollPane1.addMouseListener(new MouseAdapter() {
    });

    cochesTabla.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        cargarCocheSeleccionado();
      }
    });

    limpiarBoton.addActionListener(e -> {
      limpiarFormulario();
      listarCoches();
    });

    eliminarBoton.addActionListener(e -> eliminarCoche());
  }

  private void cargarCocheSeleccionado() {
    var fila = cochesTabla.getSelectedRow();
    if (fila != -1) {
      var id = cochesTabla.getModel().getValueAt(fila, 0).toString();
      this.idCoche = Integer.parseInt(id);
      var marca = cochesTabla.getModel().getValueAt(fila, 1).toString();
      this.introducirMarca.setText(marca);
      var modelo = cochesTabla.getModel().getValueAt(fila, 2).toString();
      this.introducirModelo.setText(modelo);
      var unidades_disponibles =
              cochesTabla.getModel().getValueAt(fila, 3).toString();
      this.introducirUnidades.setText(unidades_disponibles);
      var precio = cochesTabla.getModel().getValueAt(fila, 4).toString();
      this.introducirPrecio.setText(precio);
      var matricula = cochesTabla.getModel().getValueAt(fila, 5).toString();
      this.introducirMatricula.setText(matricula);
    }
  }

  private void iniciarForma() {
    setContentPane(PanelPrincipal);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 700);
    setLocationRelativeTo(null);

    if (avisoMatricula != null) {
      avisoMatricula.setForeground(Color.RED);
      avisoMatricula.setVisible(false);
    } else {
      System.err.println("⚠️ Error: 'avisoMatricula' no está inicializado. Asegúrate de crearlo en el diseñador.");
    }

    ValidadorCampos.validarMatriculaTiempoReal(introducirMatricula, avisoMatricula);
  }

  private void createUIComponents() {

    introducirMatricula = new JTextField();
    this.tablaModeloCoches = new DefaultTableModel(0, 6){
    @Override
    public boolean isCellEditable (int row, int column){
    return false;
    }
    };

    String[] cabeceros = {"Id", "Marca", "Modelo", "Unidades Disponibles",
            "Precio",
            "Matrícula"};
    this.tablaModeloCoches.setColumnIdentifiers(cabeceros);
    this.cochesTabla = new JTable(tablaModeloCoches);
    this.cochesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listarCoches();
  }

  private void listarCoches() {
    this.tablaModeloCoches.setRowCount(0);
    var coches = this.cocheServicio.listaCoches();
    coches.forEach(coche -> {
      Object[] filaCoche = {
              coche.getId(),
              coche.getMarca(),
              coche.getModelo(),
              coche.getUnidades_disponibles(),
              coche.getPrecio(),
              coche.getMatricula()
      };
      //Añado a la tabla la línea que se ha creado
      this.tablaModeloCoches.addRow(filaCoche);
    });
  }

  private void eliminarCoche() {
    var fila = cochesTabla.getSelectedRow();
    if (fila != -1) {

      var id = cochesTabla.getModel().getValueAt(fila, 0).toString();
      this.idCoche = Integer.parseInt(id);
      var coche = new coche();
      coche.setId(this.idCoche);
      cocheServicio.eliminarCoche(coche);

      mostrarMensaje("Coche con el ID [" + this.idCoche+"]");
      limpiarFormulario();
      listarCoches();

    } else {
      mostrarMensaje("Debe seleccionar el coche que desea eliminar");
    }
  }

  private void guardarCoche() {
    listarCoches();
    //Para que pida la marca
    if (introducirMarca.getText().isBlank()) {
      mostrarMensaje("Proporcione la marca");
      introducirMarca.requestFocusInWindow();
      return;
    }

    //Para que pida el modelo
    if (introducirModelo.getText().isBlank()) {
      mostrarMensaje("Proporcione un modelo");
      introducirModelo.requestFocusInWindow();
    }

    //Para que pida las unidades disponibles
    if (introducirUnidades.getText().isBlank()) {
      mostrarMensaje("Proporcione las unidades");
      introducirUnidades.requestFocusInWindow();
    }

    //Para que pida el precio
    if (introducirPrecio.getText().isBlank()) {
      mostrarMensaje("Proporcione el precio");
      introducirPrecio.requestFocusInWindow();
    }

    if (introducirMatricula.getText().isBlank()) {
      mostrarMensaje("¿Cual es la matrícula del vehículo?");
      introducirMatricula.requestFocusInWindow();
      return;
    }

    try {
      //INICIO TRY
      var marca = introducirMarca.getText().toLowerCase().trim();
      var modelo = introducirModelo.getText().toLowerCase().trim();
      var unidades_disponibles = Integer.parseInt(introducirUnidades.getText());
      var precioIn = introducirPrecio.getText().replace(',', '.');
      var precio = Double.parseDouble(precioIn);
      var matricula = introducirMatricula.getText().toLowerCase();
      var coche = new coche(this.idCoche, marca, modelo, unidades_disponibles,
              precio, matricula);

      //validador de formato matrícula
      if (!matricula.matches("[A-Za-z]{3}\\d{4}")) {
        mostrarMensaje("La matrícula debe tener 3 letras seguidas de 4 números. Ejemplo: ABC1234");
        introducirMatricula.requestFocusInWindow();
        return;
      }
      this.cocheServicio.guardarCoche(coche);

      if (this.idCoche == null) {
        mostrarMensaje("Se añadió un coche nuevo [" + marca + "]");
      } else {
        mostrarMensaje("Se han modificado los datos del coche [" + this.idCoche +
                "]" +
                "[" + marca +
                "]");
      }

      limpiarFormulario();
      listarCoches();
    } catch (NumberFormatException e) {
      mostrarMensaje("Por favor, introduzca un número válido para unidades y precio.");
    }//FIN TRY CATCH
    //Fin de método guardarCoche
  }


  private void limpiarFormulario() {
    introducirMarca.setText("");
    introducirModelo.setText("");
    introducirUnidades.setText("");
    introducirPrecio.setText("");
    introducirMatricula.setText("");
    this.idCoche = null;
    this.cochesTabla.getSelectionModel().clearSelection();
  }

  private void mostrarMensaje(String f_mensaje) {
    JOptionPane.showMessageDialog(this, f_mensaje);
  }
}
