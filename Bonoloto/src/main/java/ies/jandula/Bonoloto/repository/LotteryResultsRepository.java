package ies.jandula.Bonoloto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.jandula.Bonoloto.models.LotteryResults;
import ies.jandula.Bonoloto.models.LotteryResultsId;

public interface LotteryResultsRepository extends JpaRepository<LotteryResults, LotteryResultsId>{

}
