package matheus.com.pass_in.dto.attendee;

import java.time.LocalDateTime;

public record AttendeeDetails (
        String id,
        String name,
        String email,
        LocalDateTime created,
        LocalDateTime checkedAt){
}
