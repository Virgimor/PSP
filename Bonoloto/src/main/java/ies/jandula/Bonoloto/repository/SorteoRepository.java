package ies.jandula.Bonoloto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.jandula.Bonoloto.models.Sorteo;

public interface SorteoRepository extends JpaRepository<Sorteo, String>{

	int findByNumero1AndNumero2AndNumero3(int num1);
	int findByNumero2(int num2);
	int findBy(int num3);
	int findByNumero4(int num4);
	int findByNumero5(int num5);
	int findByNumero6(int num6);
	int findByReintegro(int reintegro);

}
