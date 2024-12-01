package ies.jandula.VideoClubBien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.jandula.VideoClubBien.models.Users;

public interface UsersRepository extends JpaRepository<Users,Integer > {

}
