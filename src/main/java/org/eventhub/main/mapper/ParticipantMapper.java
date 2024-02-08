package org.eventhub.main.mapper;


import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.model.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@Component
public interface ParticipantMapper {

    Participant mapToParticipant(ParticipantRequest participantRequest);
    ParticipantResponse mapToParticipantResponse(Participant participant);

}
