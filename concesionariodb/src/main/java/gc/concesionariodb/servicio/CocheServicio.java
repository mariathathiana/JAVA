//Declaro el paquete donde está la interfaz
package gc.concesionariodb.servicio;
//Importo la clase Cliente, que será utilizada
import gc.concesionariodb.modelo.coche;
//Importo la interfaz del repositorio para acceder a los datos persistidos
import gc.concesionariodb.repositorio.CocheRepositorio;
//Importo la notación de override. string inyecta dependencias de forma
// automática
import org.springframework.beans.factory.annotation.Autowired;
//Importo <CommandRunner> para ejecutar el código cuando la app arranca
import org.springframework.boot.CommandLineRunner;
//Clase principal que lanza la aplicación
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;
//Indico que esta clase es una aplicación de Spring Boot
//Importo la notación <@Service> para indicar que esta clase es un servicio
import org.springframework.stereotype.Service;
//Importo la clase List para trabajar con listas de objetos
import java.util.List;
//Añadiendo la notación <@Service> consigo que forme parte de la "fabrica de
// Spring"
@Service
public class CocheServicio implements ICocheServicio{
  //Inyecto una instancia de <clienterepositorio> para acceder a los datos sin
  //necesidad de inicializarla.
  @Autowired
  private CocheRepositorio cocheRepositorio;
  //Implemento el método de la interfaz que devuelve todos los clientes
  // registrados
  @Override
  public List<coche> listaCoches(){
    //Obtengo todos los registros de la tabla <coche_tb>
    List<coche> coches = cocheRepositorio.findAll();
    return coches;
  }

  @Override
  public void guardarCoche(coche f_coche){
    //Utilizo el método del JpaRepository para insertar o actualizar
    cocheRepositorio.save(f_coche);
  }
 //Implemento el método para eliminar un coche
 @Override
 public void eliminarCoche(coche f_coche){
    //Utilizo el método delete del repositorio para borrar el cliente de la
   // base de datos
   cocheRepositorio.delete(f_coche);
 }
 @Override
  public coche buscarCochePorId(Integer f_idCoche){
    //Uso el método <findById()> si no encuentra el cliente, devuelve <null>
   coche coche = cocheRepositorio.findById(f_idCoche).orElse(null);
   return coche;
 }
}
