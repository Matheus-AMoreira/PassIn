package matheus.com.pass_in.repositories;

import matheus.com.pass_in.domain.chenkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByAttendeeId(String AttendeeId);
}
