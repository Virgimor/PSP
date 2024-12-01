package ies.jandula.VideoClubBien.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.jandula.VideoClubBien.models.booking;


public interface BookingRepository extends JpaRepository<booking,Integer > 
{
	Optional<booking> findByIdMovieIdAndIdUserId(int movieId, int userId);

}