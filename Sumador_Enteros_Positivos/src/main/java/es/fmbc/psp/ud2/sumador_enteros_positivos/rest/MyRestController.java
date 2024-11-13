package es.fmbc.psp.ud2.sumador_enteros_positivos.rest;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class MyRestController {
	
	public MyRestController() {
		
	}
	@RequestMapping(method = RequestMethod.GET, value = "/userVisits")
	public ResponseEntity<Integer> handleRequest(HttpSession session){
		
		Integer userVisits = (Integer) session.getAttribute("userVisits");
		int[] arrayNum= new int[5];
		
		if (userVisits == null) {
			userVisits = 0;
		}
		
		userVisits++;
		
		System.out.println("User visits: " + userVisits);
		
		session.setAttribute("User visits", userVisits);
		
		return ResponseEntity.ok().body(userVisits);
	}

}
