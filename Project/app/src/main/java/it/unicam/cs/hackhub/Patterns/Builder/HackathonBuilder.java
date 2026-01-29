package it.unicam.cs.hackhub.Patterns.Builder;

import java.time.LocalDateTime;
import lombok.NonNull;
import it.unicam.cs.hackhub.Model.Entities.Hackathon;

public class HackathonBuilder implements Builder {

    private String name;

    private String location;

    private String rules;

    private int prize;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime evaluationDate;

    private LocalDateTime endingDate;

    @Override
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public void setLocation(@NonNull String l) {
        this.location = l;
    }

    @Override
    public void setPrize(int prize) {
        this.prize = prize;
    }

    @Override
    public void setRules(@NonNull String rules) {
       this.rules = rules;
    }

    @Override
    public void setStartDate(@NonNull LocalDateTime date) {
        this.startDate = date;
    }

    @Override
    public void setEvaluationDate(@NonNull LocalDateTime date) {
        this.evaluationDate = date;
    }

    @Override
    public void setEndingDate(@NonNull LocalDateTime date) {
       this.endingDate = date;
    }

    public Hackathon getResult() {
        return new Hackathon(name, location, rules, prize, creationDate, startDate, evaluationDate, endingDate);
    }

}
