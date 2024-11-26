package ies.jandula.query.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ies.jandula.query.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByNif(@Param(value = "nif") String nif);
	
	List<Usuario> findByNifContaining(String nif);
	
	List<Usuario> findByEdadLessThan( String edad);
	
	List<Usuario> findByAprobadoTrue();
	
	List<Usuario> findByAprobadoFalse();
	
	List<Usuario> findByNifAndEdad(String nif, Integer edad);
	
	List<Usuario> findByEdadOrderByEdadAsc(Integer edad);
	
	//List<Usuario> findByEdadOrderByEdadDes(Integer edad);
	
	List<Usuario> findTop10ByDireccionOrderByEdadDesc(String nif);
	
	List<Usuario> findDistinctByNif(String nif);
	
	List<Usuario> findByEdadBetween(Integer edad1, Integer edad2);
	
	Usuario findNifByDireccion(String direccion);
}
