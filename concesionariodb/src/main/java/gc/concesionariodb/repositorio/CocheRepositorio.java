package gc.concesionariodb.repositorio;


//Importo la clase <Cliente> que será la

import gc.concesionariodb.modelo.coche;
import org.springframework.data.jpa.repository.JpaRepository;

//Importo la interfaz JpaRepository
public interface CocheRepositorio extends JpaRepository <coche, Integer>{
}
