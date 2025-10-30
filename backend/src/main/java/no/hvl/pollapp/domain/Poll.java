package no.hvl.pollapp.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteOption> options;

    public Poll() {}

    public Poll(String question) {
        this.question = question;
    }

    public Long getId() { return id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public List<VoteOption> getOptions() { return options; }
    public void setOptions(List<VoteOption> options) { this.options = options; }
}
