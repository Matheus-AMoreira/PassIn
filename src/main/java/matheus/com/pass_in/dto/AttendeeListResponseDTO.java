package matheus.com.pass_in.dto;

import lombok.Getter;

import java.util.List;

@Getter
public record AttendeeListResponseDTO (List<AttendeeDetails> atteandees) {
}
