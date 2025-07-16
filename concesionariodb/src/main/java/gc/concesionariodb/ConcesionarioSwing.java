package gc.concesionariodb;

import com.formdev.flatlaf.FlatDarculaLaf;
import gc.concesionariodb.GUI.ConcesionarioForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ConcesionarioSwing {
  public static void main(String[] args) {
    FlatDarculaLaf.setup();

    ConfigurableApplicationContext contextoSpring =
            new SpringApplicationBuilder(ConcesionarioSwing.class)
                    .headless(false)
                    .web(WebApplicationType.NONE)
                    .run(args);
    SwingUtilities.invokeLater(() -> {
      ConcesionarioForm concesionarioForm =
              contextoSpring.getBean(ConcesionarioForm.class);
      concesionarioForm.setVisible(true);
    });
  }
}
