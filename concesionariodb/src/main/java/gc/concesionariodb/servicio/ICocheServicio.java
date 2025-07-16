package gc.concesionariodb.servicio;
//Importo la clase coche
import gc.concesionariodb.modelo.coche;
//Importo la interfaz para trabajar con las listas
import java.util.List;

public interface ICocheServicio {
public List<coche> listaCoches();

public coche buscarCochePorId(Integer idCoche);

public void guardarCoche (coche coche);
// para insertar como para actualizar un objeto de tipo coche

public void eliminarCoche(coche coche);

}
