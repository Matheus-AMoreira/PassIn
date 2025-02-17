package matheus.com.pass_in.dto;

public record EventRequestDTO(
        String title,
        String details,
        Integer maximumAttendees
) {
}
