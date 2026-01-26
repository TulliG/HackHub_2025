package it.unicam.cs.hackhub.Model.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NonNull;
import lombok.Getter;
import org.springframework.data.annotation.Id;

public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id = null;

    @Getter
    private String content;

    @Getter
    private Integer grade = null;

    @Getter
    private Team submissioner;

    public Submission(@NonNull String content, @NonNull Team team) {
        //TODO Controllo file sottomissione
        this.content = content;
        this.submissioner = team;
    }

    public void setGrade(Integer grade) {
        this.grade = Math.clamp(grade, 1, 10);
    }

}
