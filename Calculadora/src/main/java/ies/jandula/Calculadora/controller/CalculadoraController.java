package ies.jandula.Calculadora.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculadora")
public class CalculadoraController {
	
	@RequestMapping(value = "/sumar/{a}/{b}", method = RequestMethod.POST)
	public int suma (@PathVariable(value = "a") int a,
					 @PathVariable(value = "b") int b) {
		return a + b;
	}
	
	@RequestMapping(value = "/resta")
	public int resta (@PathVariable(value = "a") int a,
					 @PathVariable(value = "b") int b) {
		return a + b;
	}
	
	@RequestMapping(value = "/multiplicacion")
	public int multiplicacion (@PathVariable(value = "a") int a,
					 @PathVariable(value = "b") int b) {
		return a * b;
	}
	
	@RequestMapping(value = "/division")
	public int division (@PathVariable(value = "a") int a,
					 @PathVariable(value = "b") int b) {
		return a % b;
	}
	
	@RequestMapping(value = "/factorial")
	public int factorial (@PathVariable(value = "num") int num){
		
		for(int i = num; i>0; i--) {
			num=num*i;
		}
		
		return num;
	}
	
	@RequestMapping(value = "/fibonnachi/{num}")
	public int fibonnachi (@PathVariable(value = "num") int num) {
		
		int resultado = 0;
		int a = 0;
		int b =1;
		for(int i = num; i>0; i--) {
			
			resultado = a + b;
			
			a=b;
			b=resultado;
		}
		
		return resultado;
	}
	
	@RequestMapping(value = "/pares/{num}")
	public int[] pares (@PathVariable(value = "num") int num) {
		
		int cont=0;
		int comprobacion= 0;
		int[] pares = new int[num];
		while(comprobacion<=2*(num-1)) {
			if(comprobacion%2==0) {
				pares[cont]=comprobacion;
				cont++;
				comprobacion=comprobacion+2;
			}
			
		}
		
		return pares;
	}
	
	@RequestMapping(value = "/impares/{num}")
	public int[] impares (@PathVariable(value = "num") int num){
		int cont=0;
		int comprobacion= 1;
		int[] impares = new int[num];
		while(comprobacion<=2*(num)) {
			if(comprobacion%2!=0) {
				impares[cont]=comprobacion;
				cont++;
				comprobacion=comprobacion+2;
			}
			
		}
		
		return impares;
}
	@RequestMapping(value = "/numerosPrimos/{num}")
	public int[] numerosPrimos (@PathVariable(value = "num") int num){
		int[] primos = new int[num];
	    int cont=0;
	    int posiblePrimo=1;
	    boolean primo = false;
	    while(cont<num) {
	        for(int i=2;i<posiblePrimo;i++) {
	        	
	            if(posiblePrimo%i==0) {
	                primo=false;
	             
	        }
		        if(primo) {
		            primos[cont]=posiblePrimo;
		            cont++;
	        }
		        primo=true;
		        posiblePrimo++;
	        }
	    }
		return primos;
	}
	
	@RequestMapping(value = "/numerosPerfectos/{num}")
	public boolean numerosPerfectos (@PathVariable(value = "num") int num){
		
		boolean perfecto=false;
		int almacen=0;
		int i =1;
		while(i<num) {
			if(num%i==0) {
				almacen=almacen+i;
				i++;
			} else {
				i++;
			}
		}
		if(almacen==num) {
			perfecto=true;
		}
		
		return perfecto;
	}

}
