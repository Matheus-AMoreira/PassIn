package matheus.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import matheus.com.pass_in.domain.attendee.Attendee;
import matheus.com.pass_in.domain.chenkin.CheckIn;
import matheus.com.pass_in.domain.chenkin.exceptions.CheckInAlreadyExistsException;
import matheus.com.pass_in.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee){
        verifyCheckInExists(attendee.getId());
        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreateAt(LocalDateTime.now());
        this.checkInRepository.save(newCheckIn);
    }

    public void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn= this.getCheckIn(attendeeId);
        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already check in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
