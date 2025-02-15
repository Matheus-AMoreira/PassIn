package matheus.com.pass_in.repositories;

import matheus.com.pass_in.domain.chenkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<CheckIn, Integer> {
}
