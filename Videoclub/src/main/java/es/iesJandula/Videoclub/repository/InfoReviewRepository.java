package es.iesJandula.Videoclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesJandula.Videoclub.models.InfoReview;
import es.iesJandula.Videoclub.models.InfoReviewId;

public interface InfoReviewRepository extends JpaRepository<InfoReview, InfoReviewId>{

}
