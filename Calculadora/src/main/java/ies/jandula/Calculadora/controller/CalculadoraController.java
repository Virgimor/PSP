package ies.jandula.Calculadora.controller;

import java.io.IOException;
import java.util.StringTokenizer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ies.jandula.Calculadora.dto.Parametros;
import ies.jandula.Calculadora.dto.Resultado;
import ies.jandula.Calculadora.exception.CalculadoraServerException;

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
	
	@RequestMapping(value = "/exponencial", method = RequestMethod.POST, consumes = "application/json")
	public int exponencial (@RequestBody Parametros parametros) {
		
		return (int) Math.pow(parametros.getNumero1(), parametros.getNumero2());
	}
	
	@RequestMapping(value = "/exponencialObjet", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> exponencialObjet (@RequestBody Resultado resultado){
		
		Resultado resultadoNuevo = resultado;
		try {
			if(resultado.getResultado()<0) {
				throw new CalculadoraServerException(1, "El numero tiene que ser positivo");
			}
		} catch (CalculadoraServerException calculadoraServerException) {
			
			return ResponseEntity.status(500).body(calculadoraServerException.getBodyExceptionMessage());
		}		
		
		return ResponseEntity.ok().body(resultadoNuevo);
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
	@RequestMapping(value = "/factoriall/{numero}")
	public ResponseEntity<?> factoriall(@PathVariable int numero) {
		try {
			if(numero<0) {
			throw new CalculadoraServerException(1, "el numero tiene que ser positivo");
		}
		
		return ResponseEntity.ok().body(factorial(numero));
		
		}catch(CalculadoraServerException calculadoraServerException) {
			return ResponseEntity.status(400).body(calculadoraServerException.getBodyExceptionMessage());
		}
		
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
		//numeros por los que se va a dividir el numero ofrecido
		int i =1;
		while(i<num) {
			if(num%i==0) {
				//la variable almacen suma los numeros divisibles por el numero ofrecido 
				almacen=almacen+i;
				//icremento de i
				i++;
			} else {
				i++;
			}
		}
		//compruba que la suma de los divisores da el mismo resultado que el numero ofrecido
		if(almacen==num) {
			perfecto=true;
		}
		//
		
		return perfecto;
	}
	
	@RequestMapping(value = "/mayorDeTres", method = RequestMethod.POST)
	public int mayorDeTres (@RequestBody Parametros parametros) {
		
		if(parametros.getNumero1()>=parametros.getNumero2() && parametros.getNumero1()>=parametros.getNumero3()) {
			return parametros.getNumero1();
		} else if(parametros.getNumero2()>=parametros.getNumero1()&& parametros.getNumero2()>=parametros.getNumero3()) {
			return parametros.getNumero2();
		}else {
			return parametros.getNumero3();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/by_file/", 
			consumes = {"multipart/form-data"}, produces = {"multipart/form-data"})
	public ResponseEntity<?> sumarFileParam(@RequestParam(value="numeros", required=false) MultipartFile numeros) throws IOException, CalculadoraServerException{
		
		// Obtenemos del fichero el contenido del mismo
		String numerosString = new String(numeros.getBytes());
		
		StringTokenizer stringTokenizer = new StringTokenizer(numerosString, " ") ;
		
		int numero1 = Integer.valueOf(stringTokenizer.nextToken()) ;
		int numero2 = Integer.valueOf(stringTokenizer.nextToken()) ;
		
		// Aquí comprobaremos si son números positivos
		this.checkNumbers(numero1, numero2) ;
					
		// Calculamos la suma
		int outcome = numero1 + numero2 ;
		
		return null;
		
	}
	
	private void checkNumbers(int numero1, int numero2) throws CalculadoraServerException
	{
		if (numero1 < 0)
		{
			throw new CalculadoraServerException(1, "El número 1 es negativo: " + numero1) ;
		}
		else if (numero2 < 0)
		{
			throw new CalculadoraServerException(2, "El número 2 es negativo: " + numero2) ;
		}
	}

}
