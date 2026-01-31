package it.unicam.cs.hackhub.Application.Mappers;

import it.unicam.cs.hackhub.Application.DTOs.SubmissionDTO;
import it.unicam.cs.hackhub.Controllers.Requests.SubmissionRequest;
import it.unicam.cs.hackhub.Model.Entities.Submission;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {
    public SubmissionDTO toDTO(Submission s){
        return new SubmissionDTO(
                s.getId(),
                s.getContent(),
                s.getGrade(),
                s.getSubmissioner().getName()
        );
    }
}
