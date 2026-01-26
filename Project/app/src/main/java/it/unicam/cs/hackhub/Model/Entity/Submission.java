package it.unicam.cs.hackhub.Model.Entity;



import lombok.NonNull;
import lombok.Getter;

import java.io.File;

public class Submission {

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

    /**
     * Adds the id
     * @param id the id
     */
    public void setId(@NonNull Long id) {
        this.id = id;
    }

}
