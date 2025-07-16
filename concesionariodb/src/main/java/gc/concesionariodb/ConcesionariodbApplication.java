package gc.concesionariodb;

//Importo la interfaz del servicio que será utilizada en esta clase.
import gc.concesionariodb.modelo.coche;
import gc.concesionariodb.servicio.ICocheServicio;
//Importo logger y loggerfactory para imprimir mensajes de log
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
//Importo la notación <@Autowired> para inyección automática de dependenciass
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class ConcesionariodbApplication implements CommandLineRunner {
@Autowired
private ICocheServicio cocheServicio;

//Creo un logger para registrar eventos de ejecución
private static final Logger logger =
LoggerFactory.getLogger(ConcesionariodbApplication.class);
//Creo una variable para introducir un salto de línea en los <loggers>
String nl = System.lineSeparator();

public static void main(String[] args) {
logger.info("Iniciando la aplicación");
SpringApplication.run(ConcesionariodbApplication.class, args);
logger.info("Aplicación finalizada");
}

//Método que se ejecutará justo después de que Spring Boot haya arrancado.
@Override
public void run(String... args) throws Exception{
//Llamo al método con el menú
ConcesionariodbApplication();
}

private void ConcesionariodbApplication(){
var salir=false;
var sc = new Scanner(System.in);
while (!salir){
var opcion = mostrarMenu(sc);
salir = ejecutarOpciones(sc, opcion);
logger.info("");
}
}

private int mostrarMenu(Scanner f_sc){
		logger.info("""
						***Aplicación  Concesionario ****
						\t1. Listar coches
						\t2. Buscar Coches
						\t3. Añadir coches
						\t4. Modificar coches
						\t5. Eliminar coches
						\t6. Salir
						Seleccione una opcion: \s
						""");
		return Integer.parseInt(f_sc.nextLine());
	}

private boolean ejecutarOpciones(Scanner f_sc, int f_opcion){
		var salir = false;
		switch (f_opcion){
			case 1 -> {
				logger.info("----Listado de CLientes ---");
				List<coche> coches = cocheServicio.listaCoches();
				coches.forEach(coche -> logger.info(coche.toString() + nl));
				}
			case 2 -> {
				logger.info("---Buscar Cliente por Id");
				logger.info("Listado de Ids disponibles");
				List<coche> coches = cocheServicio.listaCoches();
				coches.forEach(coche -> logger.info(coche.getId().toString()+ " "));
				logger.info("Indique el ID del cliente que quiere consultar: ");
				var idCoche = Integer.parseInt(f_sc.nextLine());
				coche coche = cocheServicio.buscarCochePorId(idCoche);
				if (coche != null){
					logger.info("Coche encontrado: " + coche);
									} else {
					logger.info("Coche con el ID" + idCoche);
				}
			}
			case 3 ->{
				logger.info("Añadir Coche"+nl);
				logger.info("Marca del coche");
				var marcaAgregar = f_sc.nextLine();
				logger.info("Modelo");
				var modeloAgregar =f_sc.nextLine();
				logger.info("Unidades disponibles");
				var unidadesDisponiblesAgregar =Integer.parseInt(f_sc.nextLine());
				logger.info("Precio");
				var precioAgregar = Double.parseDouble(f_sc.nextLine());
				var nuevoCoche = new coche();
				nuevoCoche.setMarca(marcaAgregar);
				nuevoCoche.setModelo(modeloAgregar);
				//nuevoCoche.getUnidadesDisponibles(unidadesDisponiblesAgregar);
				nuevoCoche.setPrecio(precioAgregar);
				cocheServicio.guardarCoche(nuevoCoche);
				logger.info("Coche [] añadido correctamente"+nl);
			}
			case 4->{
				logger.info("Modificar cliente");
				logger.info("Listado de los IDs disponibles"+nl);
				List<coche> coches =cocheServicio.listaCoches();
				coches.forEach(coche -> logger.info(coche.toString()+nl));
				logger.info("ID del cliente que desee modificar");
				var idCocheModifcar = Integer.parseInt(f_sc.nextLine());
				coche cocheModificar =cocheServicio.buscarCochePorId(idCocheModifcar);
				if (cocheModificar!=null){
				logger.info("Marca: ");
				var marcaModificar = f_sc.nextLine();
				var modeloModificar = f_sc.nextLine();
				var unidadesDispModificar = f_sc.nextLine();
				var precioModificar =  f_sc.nextLine();
				 cocheModificar.setMarca(marcaModificar);
				 // lo mismo para todas, completar
				cocheServicio.guardarCoche(cocheModificar);
				logger.info("Coche modificado "+ cocheModificar);

			}else {
					logger.info("ERROR: Cliente con el ID [" + idCocheModifcar + " -> " +
									cocheModificar + "] no encontrado" + nl);
				}
			}
				case 5 ->{
				logger.info("Eliminar cliente");
				logger.info("Listado de IDs disponibles");
				List<coche> coches =cocheServicio.listaCoches();
				coches.forEach(coche -> logger.info(coche.toString()));
				logger.info("Id coche a eliminar");
				var idCocheELiminar = Integer.parseInt(f_sc.nextLine());
				var cocheEliminar = cocheServicio.buscarCochePorId(idCocheELiminar);
				if (cocheEliminar != null){
					cocheServicio.eliminarCoche(cocheEliminar);
					logger.info("Coche eliminado correctamente de la base de datos");
				}else {
					logger.info("Error:Coche no encontrado"+ idCocheELiminar+ cocheEliminar);
				}
				}
			}
			return salir;
		}
}

