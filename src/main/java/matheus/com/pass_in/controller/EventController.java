package matheus.com.pass_in.controller;

import lombok.RequiredArgsConstructor;
import matheus.com.pass_in.dto.EventIdDTO;
import matheus.com.pass_in.dto.EventRequestDTO;
import matheus.com.pass_in.dto.EventResponseDTO;
import matheus.com.pass_in.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.service.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.service.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.EventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }
}
