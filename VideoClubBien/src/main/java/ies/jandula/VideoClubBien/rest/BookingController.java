package ies.jandula.VideoClubBien.rest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.jandula.VideoClubBien.models.BookingId;
import ies.jandula.VideoClubBien.models.Movies;
import ies.jandula.VideoClubBien.models.Users;
import ies.jandula.VideoClubBien.models.booking;
import ies.jandula.VideoClubBien.repository.BookingRepository;
import ies.jandula.VideoClubBien.repository.MoviesRepository;
import ies.jandula.VideoClubBien.repository.UsersRepository;
import ies.jandula.VideoClubBien.utils.VideoclubException;


@RestController
@RequestMapping("/videoclub")
public class BookingController 
{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/booking")
    public ResponseEntity<List<Movies>> getMovies() 
    {
        List<Movies> movies = moviesRepository.findAll(); 
        return ResponseEntity.ok(movies);
    }
    //buscar peliculas segun su titulo
    @PostMapping("/booking")
    public ResponseEntity<List<Movies>> searchMovies(@RequestBody Movies searchCriteria) 
    {
        // Lógica para buscar películas en función de `searchCriteria`.
        List<Movies> movies = moviesRepository.findByTituloContaining(searchCriteria.getTitulo());
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/booking/{movieId}")
    public ResponseEntity<String> bookMovie(@PathVariable Integer movieId, @RequestParam(value = "userId") Integer userId) throws VideoclubException 
    {
        Movies movies = moviesRepository.findById(movieId)
                .orElseThrow(() -> new VideoclubException(400, "Película no encontrada."));
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new VideoclubException(404, "Usuario no encontrado."));
        
        BookingId bookingId=new BookingId(movieId,userId);

        booking bookings = new booking();
        bookings.setId(bookingId);
        bookings.setMovies(movies);
        bookings.setUser(user);

        bookingRepository.save(bookings);

        return ResponseEntity.ok("Película reservada correctamente.");
    }

    @DeleteMapping("/booking/{movieId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer movieId, @RequestParam Integer userId) throws VideoclubException 
    {
        // Obtener la película por su ID
        Movies movies = moviesRepository.findById(movieId)
                .orElseThrow(() -> new VideoclubException(400, "Película no encontrada."));
        
        // Obtener el usuario por su ID
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new VideoclubException(404, "Usuario no encontrado."));

        // Buscar la reserva usando los IDs
        booking bookings = bookingRepository.findByIdMovieIdAndIdUserId(movies.getMovieId(), user.getUserId())
                .orElseThrow(() -> new VideoclubException(400, "Reserva no encontrada."));

        // Eliminar la reserva
        bookingRepository.delete(bookings);

        return ResponseEntity.ok("Reserva cancelada correctamente.");
    }
    

}
