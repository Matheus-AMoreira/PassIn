package matheus.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import matheus.com.pass_in.domain.attendee.Attendee;
import matheus.com.pass_in.domain.chenkin.CheckIn;
import matheus.com.pass_in.dto.AttendeeDetails;
import matheus.com.pass_in.dto.AttendeeListResponseDTO;
import matheus.com.pass_in.repositories.AttendeeRepository;
import matheus.com.pass_in.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private AttendeeRepository attendeeRepository;

    private CheckInRepository checkinRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkinRepository.findByAttendeeId(eventId);
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreateAt() : null;

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }
}
