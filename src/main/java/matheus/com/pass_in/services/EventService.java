package matheus.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import matheus.com.pass_in.domain.attendee.Attendee;
import matheus.com.pass_in.domain.event.Event;
import matheus.com.pass_in.domain.event.exceptions.EventFullException;
import matheus.com.pass_in.dto.attendee.AttendeeIdDTO;
import matheus.com.pass_in.dto.attendee.AttendeeRequestDTO;
import matheus.com.pass_in.dto.event.EventIdDTO;
import matheus.com.pass_in.dto.event.EventRequestDTO;
import matheus.com.pass_in.dto.event.EventResponseDTO;
import matheus.com.pass_in.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = getEventById(eventId);

        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDTO.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(AttendeeRequestDTO attendeeRequestDTO, String eventId){
        this.attendeeService.verefyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Event event = getEventById(eventId);

        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if(event.getMaximumAttendees() <= attendeeList.size())throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() -> {
            return new RuntimeException("Event not found with ID: " + eventId);
        });
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
