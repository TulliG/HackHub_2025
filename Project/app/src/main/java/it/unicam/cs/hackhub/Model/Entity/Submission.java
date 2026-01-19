package it.unicam.cs.hackhub.Model.Entity;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;

public class Submission {

    private Long id;

    private File content;

    private Integer grade = null;

    private Team submissioner;

    public Submission(@NonNull File content,@NonNull Team team) {
        //TODO Controllo file sottomissione
        this.content = content;
        this.submissioner = team;
    }

    public File getContent() {
        return content;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = Math.clamp(grade, 1, 10);
    }

    public Team getSubmissioner() {
        return submissioner;
    }


    /**
     * Adds the id
     * @param id the id
     */
    public void setId(@NonNull Long id) {
        this.id = id;
    }

}
