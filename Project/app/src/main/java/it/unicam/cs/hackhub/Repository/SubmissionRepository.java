package it.unicam.cs.hackhub.Repository;

import java.util.HashMap;
import java.util.Map;

import it.unicam.cs.hackhub.Model.Entity.Submission;

public class SubmissionRepository {
    
    private static final Map<Long, Submission> repo = new HashMap<>();
    private static Long serialId = 1L;

    public Submission get(Long id) {
        return repo.get(id);
    }

    public void put(Submission submission) {
        if (submission.getId() == null) submission.setId(serialId++);
        repo.put(submission.getId(), submission);
    }

    public void remove(Long id) {
        repo.remove(id);
    }

}
