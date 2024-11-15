package es.fmbc.psp.ud2.mi_primer_microservicio;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.fmbc.psp.ud2.mi_primer_microservicio.exception.MiPrimerMicroServicioException;
import es.fmbc.psp.ud2.mi_primer_microservicio.models.Numeros;
import es.fmbc.psp.ud2.mi_primer_microservicio.models.Resultado;

public class LauncherClient
{
    /** Attribute - Logger de la clase */
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * Launcher de la aplicación cliente
	 * @param args con los parámetros de entrada
	 * @throws MiPrimerMicroServicioException con una excepción propia
	 */
	public static void main(String[] args) throws MiPrimerMicroServicioException
	{
		LauncherClient miLauncher = new LauncherClient() ;
		
        // Método para probar el endpoint /by_path/
        miLauncher.enviarPeticionPath();

        // Método para probar el endpoint /by_query/
        miLauncher.enviarPeticionQuery();

        // Método para probar el endpoint /by_header/
        miLauncher.enviarPeticionHeader();
        
        // Método para probar el endpoint /by_body/
        miLauncher.enviarPeticionBody() ;

        // Método para probar el endpoint /by_file/
        miLauncher.enviarPeticionFile();
	}
	
	/**
	 * Este método sirve para probar el endpoint /by_path/
	 * 
	 * @throws MiPrimerMicroServicioException con una exception propia
	 */
    private void enviarPeticionPath() throws MiPrimerMicroServicioException
    {
    	// Esto servirá para abrir el canal de comunicación desde el cliente al servidor
    	// Vamos a utilizar una instancia por defecto, por lo que usaremos "createDefault"
        CloseableHttpClient httpClientRequest = HttpClients.createDefault();
        
        int numero1=2 ;
        int numero2=4 ;

        // Como el Endpoint es de tipo Get debemos usar una instancia "HttpGet"
        HttpGet request = new HttpGet("http://localhost:8081/sumar_numeros_positivos/by_path/" + numero1 + "/" + numero2 + "/");

        // Response será el canal donde proviene la respuesta del servidor
        CloseableHttpResponse httpServerResponse = null ;
        
        // En esta variable vendrá la respuesta del servidor
        String responseBody = null ;

        try
        {
            // Ejecutamos la request HTTP GET sobre el canal  httpClientRequest
            // El valor de retorno será "httpServerResponse", por donde vendrá la respuesta del servidor
            httpServerResponse = httpClientRequest.execute(request);
            
            // StatusLine nos va a permitir saber el código de respuesta del servidor
            StatusLine statusLine = httpServerResponse.getStatusLine();
            
            // Si el código de error no es el 200, es que ha sucedido un error
            if (statusLine.getStatusCode() != 200)
            {
            	// Obtenemos el cuerpo del mensaje de error de la respuesta con el error
            	responseBody = EntityUtils.toString(httpServerResponse.getEntity());
            	
            	// Generamos el mensaje de error
            	String errorString = "(Endpoint /by_path/) - El servidor ha devuelto un código de error (" + statusLine.getStatusCode() + ") " + 
            						 "con la siguiente información: " + responseBody ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString) ;
            	throw new MiPrimerMicroServicioException(2, errorString) ;            	
            }
            
            // Para conseguir la respuesta del servidor, hacemos un getEntity() que convertiremos a su vez en un string que contendrá el valor de respuesta            
            responseBody = EntityUtils.toString(httpServerResponse.getEntity());

            // Mostramos por pantalla en los logs
            LOGGER.info("(Endpoint /by_path/) - Resultado de la suma {}, {} = {}", numero1, numero2, responseBody) ;
        }
        catch (IOException ioException)
        {
        	// Generamos el mensaje de error
        	String errorString = "(Endpoint /by_path/) - Excepción mientras procesaba la llamada a través del path" ;
        	
        	// Lo incluimos en los logs y enviamos excepción
        	LOGGER.error(errorString, ioException) ;
        	throw new MiPrimerMicroServicioException(1, errorString, ioException) ;
        }
        finally
        {
        	// Cerramos los flujos abiertos de request y response
        	this.cerramosFlujos(httpClientRequest, httpServerResponse) ;
        }
    }

	/**
	 * Este método sirve para probar el endpoint /by_query/
	 * 
	 * @throws MiPrimerMicroServicioException con una exception propia
	 */
    private void enviarPeticionQuery() throws MiPrimerMicroServicioException
    {
    	// Esto servirá para abrir el canal de comunicación desde el cliente al servidor
    	// Vamos a utilizar una instancia por defecto, por lo que usaremos "createDefault"
        CloseableHttpClient httpClientRequest = HttpClients.createDefault();
        
        int numero1=2 ;
        int numero2=4 ;

        // Como el Endpoint es de tipo Get debemos usar una instancia "HttpGet"
        HttpGet request = new HttpGet("http://localhost:8081/sumar_numeros_positivos/by_query/?numero1=" + numero1 + "&numero2=" + numero2);

        // Response será el canal donde proviene la respuesta del servidor
        CloseableHttpResponse httpServerResponse = null ;
        
        // En esta variable vendrá la respuesta del servidor
        String responseBody = null ;

        try
        {
            // Ejecutamos la request HTTP GET sobre el canal  httpClientRequest
            // El valor de retorno será "httpServerResponse", por donde vendrá la respuesta del servidor
            httpServerResponse = httpClientRequest.execute(request);
            
            // StatusLine nos va a permitir saber el código de respuesta del servidor
            StatusLine statusLine = httpServerResponse.getStatusLine();
            
            // Si el código de error no es el 200, es que ha sucedido un error
            if (statusLine.getStatusCode() != 200)
            {
            	// Obtenemos el cuerpo del mensaje de error de la respuesta con el error
            	responseBody = EntityUtils.toString(httpServerResponse.getEntity());
            	
            	// Generamos el mensaje de error
            	String errorString = "(Endpoint /by_query/) - El servidor ha devuelto un código de error (" + statusLine.getStatusCode() + ") " + 
            						 "con la siguiente información: " + responseBody ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString) ;
            	throw new MiPrimerMicroServicioException(3, errorString) ;            	
            }
            
            // Para conseguir la respuesta del servidor, hacemos un getEntity() que convertiremos a su vez en un string que contendrá el valor de respuesta            
            responseBody = EntityUtils.toString(httpServerResponse.getEntity());

            // Mostramos por pantalla en los logs
            LOGGER.info("(Endpoint /by_query/) - Resultado de la suma {}, {} = {}", numero1, numero2, responseBody) ;
        }
        catch (IOException ioException)
        {
        	// Generamos el mensaje de error
        	String errorString = "(Endpoint /by_query/) - Excepción mientras procesaba la llamada a través de query" ;
        	
        	// Lo incluimos en los logs y enviamos excepción
        	LOGGER.error(errorString, ioException) ;
        	throw new MiPrimerMicroServicioException(1, errorString, ioException) ;
        }
        finally
        {
        	// Cerramos los flujos abiertos de request y response
        	this.cerramosFlujos(httpClientRequest, httpServerResponse) ;
        }
    }

	/**
	 * Este método sirve para probar el endpoint /by_header/
	 * 
	 * @throws MiPrimerMicroServicioException con una exception propia
	 */
    private void enviarPeticionHeader() throws MiPrimerMicroServicioException
    {
    	// Esto servirá para abrir el canal de comunicación desde el cliente al servidor
    	// Vamos a utilizar una instancia por defecto, por lo que usaremos "createDefault"
        CloseableHttpClient httpClientRequest = HttpClients.createDefault();
        
        int numero1=2 ;
        int numero2=4 ;

        // Como el Endpoint es de tipo Get debemos usar una instancia "HttpGet"
        HttpGet request = new HttpGet("http://localhost:8081/sumar_numeros_positivos/by_header/");
        
        // Añadimos el header "numero1"
        request.addHeader("numero1", String.valueOf(numero1));
        
        // Añadimos el header "numero2"
        request.addHeader("numero2", String.valueOf(numero2));

        // Response será el canal donde proviene la respuesta del servidor
        CloseableHttpResponse httpServerResponse = null ;
        
        // En esta variable vendrá la respuesta del servidor
        String responseBody = null ;

        try
        {
            // Ejecutamos la request HTTP GET sobre el canal  httpClientRequest
            // El valor de retorno será "httpServerResponse", por donde vendrá la respuesta del servidor
            httpServerResponse = httpClientRequest.execute(request);
            
            // StatusLine nos va a permitir saber el código de respuesta del servidor
            StatusLine statusLine = httpServerResponse.getStatusLine();
            
            // Si el código de error no es el 200, es que ha sucedido un error
            if (statusLine.getStatusCode() != 200)
            {
            	// Obtenemos el cuerpo del mensaje de error de la respuesta con el error
            	responseBody = EntityUtils.toString(httpServerResponse.getEntity());
            	
            	// Generamos el mensaje de error
            	String errorString = "(Endpoint /by_header/) - El servidor ha devuelto un código de error (" + statusLine.getStatusCode() + ") " + 
            						 "con la siguiente información: " + responseBody ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString) ;
            	throw new MiPrimerMicroServicioException(4, errorString) ;            	
            }
            
            // Para conseguir la respuesta del servidor, hacemos un getEntity() que convertiremos a su vez en un string que contendrá el valor de respuesta
            responseBody = EntityUtils.toString(httpServerResponse.getEntity());

            // Mostramos por pantalla en los logs
            LOGGER.info("(Endpoint /by_header/) - Resultado de la suma {}, {} = {}", numero1, numero2, responseBody) ;
        }
        catch (IOException ioException)
        {
        	// Generamos el mensaje de error
        	String errorString = "(Endpoint /by_header/) - Excepción mientras procesaba la llamada a través del header" ;
        	
        	// Lo incluimos en los logs y enviamos excepción
        	LOGGER.error(errorString, ioException) ;
        	throw new MiPrimerMicroServicioException(1, errorString, ioException) ;
        }
        finally
        {
        	// Cerramos los flujos abiertos de request y response
        	this.cerramosFlujos(httpClientRequest, httpServerResponse) ;
        }
    }
	
	/**
	 * Este método sirve para probar el endpoint /by_body/
	 * 
	 * @throws MiPrimerMicroServicioException con una exception propia
	 */
    private void enviarPeticionBody() throws MiPrimerMicroServicioException
    {
    	// Esto servirá para abrir el canal de comunicación desde el cliente al servidor
    	// Vamos a utilizar una instancia por defecto, por lo que usaremos "createDefault"
        CloseableHttpClient httpClientRequest = HttpClients.createDefault();

        // Como el Endpoint es de tipo Post debemos usar una instancia "HttpPost"
        HttpPost request = new HttpPost("http://localhost:8081/sumar_numeros_positivos/by_body/");
        
        // Además, como en el body va un objeto, debemos indicar que es de tipo "application/json"
        request.setHeader("Content-Type", "application/json");

        // Esta clase nos permitirá convertir el objeto a JSON (request), y de JSON a objeto (response)
        ObjectMapper objectMapper = new ObjectMapper();

        // Creamos un objeto Numeros con los números a sumar
        Numeros numeros = new Numeros();
        numeros.setNumero1(2);
        numeros.setNumero2(4);

        // Response será el canal donde proviene la respuesta del servidor
        CloseableHttpResponse httpServerResponse = null ;
        
        // En esta variable vendrá la respuesta del servidor
        String responseBody = null ;

        try
        {
            // Convertimos el objeto Numeros a JSON y configuramos el cuerpo de la request
            String jsonBody = objectMapper.writeValueAsString(numeros);
            StringEntity requestBody = new StringEntity(jsonBody);
            request.setEntity(requestBody);

            // Ejecutamos la request HTTP POST sobre el canal  httpClientRequest
            // El valor de retorno será "httpServerResponse", por donde vendrá la respuesta del servidor
            httpServerResponse = httpClientRequest.execute(request);
            
            // StatusLine nos va a permitir saber el código de respuesta del servidor
            StatusLine statusLine = httpServerResponse.getStatusLine();
            
            // Si el código de error no es el 200, es que ha sucedido un error
            if (statusLine.getStatusCode() != 200)
            {
            	// Obtenemos el cuerpo del mensaje de error de la respuesta con el error
            	responseBody = EntityUtils.toString(httpServerResponse.getEntity());
            	
            	// Generamos el mensaje de error
            	String errorString = "(Endpoint /by_body/) - El servidor ha devuelto un código de error (" + statusLine.getStatusCode() + ") " + 
            						 "con la siguiente información: " + responseBody ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString) ;
            	throw new MiPrimerMicroServicioException(5, errorString) ;            	
            }
            
            // Para conseguir la respuesta del servidor, hacemos un getEntity() que convertiremos a su vez en un string que contendrá el valor de respuesta
            responseBody = EntityUtils.toString(httpServerResponse.getEntity());

            // Deserializamos la respuesta JSON a un objeto Resultado
            // Como sabemos que es un objeto la respuesta, usamos el ObjectMapper que convertirá el JSON en formato String en un objeto Resultado
            Resultado resultado = objectMapper.readValue(responseBody, Resultado.class);

            // Mostramos por pantalla en los logs
            LOGGER.info("(Endpoint /by_body/) - Resultado de la suma {}, {} = {}", numeros.getNumero1(), numeros.getNumero2(), resultado.getValor()) ;
        } 
        catch (IOException ioException)
        {
        	// Generamos el mensaje de error
        	String errorString = "(Endpoint /by_body/) - Excepción mientras procesaba la llamada a través del body" ;
        	
        	// Lo incluimos en los logs y enviamos excepción
        	LOGGER.error(errorString, ioException) ;
        	throw new MiPrimerMicroServicioException(1, errorString, ioException) ;
        }
        finally
        {
        	// Cerramos los flujos abiertos de request y response
        	this.cerramosFlujos(httpClientRequest, httpServerResponse) ;
        }
    }
    
	/**
	 * Este método sirve para probar el endpoint /by_file/
	 * 
	 * @throws MiPrimerMicroServicioException con una exception propia
	 */
    private void enviarPeticionFile() throws MiPrimerMicroServicioException
    {
    	// Esto servirá para abrir el canal de comunicación desde el cliente al servidor
    	// Vamos a utilizar una instancia por defecto, por lo que usaremos "createDefault"
        CloseableHttpClient httpClientRequest = HttpClients.createDefault();

        // Como el Endpoint es de tipo Post debemos usar una instancia "HttpPost"
        HttpPost request = new HttpPost("http://localhost:8081/sumar_numeros_positivos/by_file/");
        
        // Creamos un objeto de tipo HttpEntity a partir de la clase MultipartEntityBuilder ya que vamos a enviar un fichero
        // Como vemos, el primer parámetro es "numeros" que así es como se llamará en el servidor. Si vas al servidor verás: value="numeros"
        // Por otro lado, el segundo parámetro será el que indiquemos donde está el fichero numero.txt con la clase File
        // Por último, el tercer parámetro es el tipo del contenido que será "multipart/form-data"
        HttpEntity httpEntity = MultipartEntityBuilder.create()
        											  .addBinaryBody("numeros",
        													  		 new File("numeros.txt"), 
        													  		 ContentType.MULTIPART_FORM_DATA, "numeros.txt")
        											  .build();
        
        // Añadimos la entidad fichero al cuerpo de la request
        request.setEntity(httpEntity);

        // Response será el canal donde proviene la respuesta del servidor
        CloseableHttpResponse httpServerResponse = null ;
        
        // En esta variable vendrá la respuesta del servidor
        String responseBody = null ;

        try
        {
            // Ejecutamos la request HTTP POST sobre el canal  httpClientRequest
            // El valor de retorno será "httpServerResponse", por donde vendrá la respuesta del servidor
            httpServerResponse = httpClientRequest.execute(request);
            
            // StatusLine nos va a permitir saber el código de respuesta del servidor
            StatusLine statusLine = httpServerResponse.getStatusLine();
            
            // Si el código de error no es el 200, es que ha sucedido un error
            if (statusLine.getStatusCode() != 200)
            {
            	// Obtenemos el cuerpo del mensaje de error de la respuesta con el error
            	responseBody = EntityUtils.toString(httpServerResponse.getEntity());
            	
            	// Generamos el mensaje de error
            	String errorString = "(Endpoint /by_file/) - El servidor ha devuelto un código de error (" + statusLine.getStatusCode() + ") " + 
            						 "con la siguiente información: " + responseBody ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString) ;
            	throw new MiPrimerMicroServicioException(6, errorString) ;            	
            }
            
            // Para conseguir la respuesta del servidor, hacemos un getEntity() que convertiremos a su vez en un string que contendrá el valor de respuesta
            responseBody = EntityUtils.toString(httpServerResponse.getEntity());

            // Mostramos por pantalla en los logs
            LOGGER.info("(Endpoint /by_file/) - Resultado de la suma {}, {} = {}", 2, 4, responseBody) ;
        }
        catch (IOException ioException)
        {
        	// Generamos el mensaje de error
        	String errorString = "(Endpoint /by_file/) - Excepción mientras procesaba la llamada a través del fichero" ;
        	
        	// Lo incluimos en los logs y enviamos excepción
        	LOGGER.error(errorString, ioException) ;
        	throw new MiPrimerMicroServicioException(1, errorString, ioException) ;
        }
        finally
        {
        	// Cerramos los flujos abiertos de request y response
        	this.cerramosFlujos(httpClientRequest, httpServerResponse) ;
        }
    }

    /**
     * @param httpClientRequest  con el canal de la request cliente http
     * @param httpServerResponse con el canal de la response servidor http
	 * @throws MiPrimerMicroServicioException con una exception propia
     */
	private void cerramosFlujos(CloseableHttpClient httpClientRequest, CloseableHttpResponse httpServerResponse) throws MiPrimerMicroServicioException
	{
		// Cerramos el canal response del servidor HTTP
        if (httpServerResponse != null)
        {
            try
            {
            	// Intentamos cerrar el canal
            	httpServerResponse.close();
            }
            catch (IOException ioException)
            {
            	// Generamos el mensaje de error
            	String errorString = "Excepción mientras procesaba el cierre del canal response del servidor HTTP" ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString, ioException) ;
            	throw new MiPrimerMicroServicioException(1, errorString, ioException) ;
            }
        }
    	
        // Cerramos el canal request del cliente HTTP
        if (httpClientRequest != null)
        {
            try
            {
            	// Intentamos cerrar el canal
                httpClientRequest.close();
            }
            catch (IOException ioException)
            {
            	// Generamos el mensaje de error
            	String errorString = "Excepción mientras procesaba el cierre del canal request del cliente HTTP" ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString, ioException) ;
            	throw new MiPrimerMicroServicioException(1, errorString, ioException) ;
            }
        }
	}
}
