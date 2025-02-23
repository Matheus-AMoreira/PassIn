package matheus.com.pass_in.controller;

import lombok.RequiredArgsConstructor;
import matheus.com.pass_in.dto.attendee.AttendeeIdDTO;
import matheus.com.pass_in.dto.attendee.AttendeeListResponseDTO;
import matheus.com.pass_in.dto.attendee.AttendeeRequestDTO;
import matheus.com.pass_in.dto.event.EventIdDTO;
import matheus.com.pass_in.dto.event.EventRequestDTO;
import matheus.com.pass_in.dto.event.EventResponseDTO;
import matheus.com.pass_in.services.AttendeeService;
import matheus.com.pass_in.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    private final AttendeeService attendeeService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.eventService.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String id){
        AttendeeListResponseDTO attendeeListResponseDTO = this.attendeeService.getEventsAttendee(id);
        return  ResponseEntity.ok(attendeeListResponseDTO);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.EventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeId = this.eventService.registerAttendeeOnEvent(body, eventId);

        var uri = uriComponentsBuilder.path("attendees/{attendeeId}/badge").buildAndExpand(attendeeId.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeId);
    }

}
