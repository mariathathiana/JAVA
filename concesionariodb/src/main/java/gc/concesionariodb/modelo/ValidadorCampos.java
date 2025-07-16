package gc.concesionariodb.modelo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ValidadorCampos {
  public static void validarMatriculaTiempoReal(JTextField campo, JLabel mensajeError) {
    campo.getDocument().addDocumentListener(new DocumentListener() {
      private void validar() {
        String texto = campo.getText();
        if (texto.matches("[a-zA-Z0-9]*")) {
          mensajeError.setVisible(false);
        } else {
          mensajeError.setVisible(true);
        }
      }

      public void insertUpdate(DocumentEvent e) { validar(); }
      public void removeUpdate(DocumentEvent e) { validar(); }
      public void changedUpdate(DocumentEvent e) { validar(); }
    });
  }

}
