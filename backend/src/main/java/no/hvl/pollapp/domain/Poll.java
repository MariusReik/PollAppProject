package no.hvl.pollapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VoteOption> options = new ArrayList<>();

    public Poll() {}
    public Poll(String question) { this.question = question; }

    public Long getId() { return id; }
    public String getQuestion() { return question; }
    public List<VoteOption> getOptions() { return options; }

    public void setId(Long id) { this.id = id; }
    public void setQuestion(String question) { this.question = question; }
    public void setOptions(List<VoteOption> options) { this.options = options; }
}
