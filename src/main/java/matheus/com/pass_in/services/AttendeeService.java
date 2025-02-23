package matheus.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import matheus.com.pass_in.domain.attendee.Attendee;
import matheus.com.pass_in.domain.attendee.exceptions.AttendeeAlreadyExistException;
import matheus.com.pass_in.domain.chenkin.CheckIn;
import matheus.com.pass_in.dto.attendee.AttendeeDetails;
import matheus.com.pass_in.dto.attendee.AttendeeListResponseDTO;
import matheus.com.pass_in.repositories.AttendeeRepository;
import matheus.com.pass_in.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    private final CheckInRepository checkinRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkinRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.map(CheckIn::getCreateAt).orElse(null);

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }

    public void verefyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(email, eventId);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendde is already registered");
    }

    public Attendee registerAttendee(Attendee newAttendee){
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }
}
