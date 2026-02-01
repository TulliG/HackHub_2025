package it.unicam.cs.hackhub.Model.Entities;

import lombok.NonNull;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name="submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String content;

    @Getter
    @Column
    private Integer grade = null;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team submissioner;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;

    protected Submission() {}

    public Submission(@NonNull String content, @NonNull Team team, @NonNull Hackathon hackathon) {
        this.hackathon = hackathon;
        this.content = content;
        this.submissioner = team;
    }

    public void setGrade(Integer grade) {
        this.grade = Math.clamp(grade, 1, 10);
    }
}
