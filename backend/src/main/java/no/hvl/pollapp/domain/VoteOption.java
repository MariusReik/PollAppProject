package no.hvl.pollapp.domain;

import jakarta.persistence.*;

@Entity
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public VoteOption() {}

    public VoteOption(String text, Poll poll) {
        this.text = text;
        this.poll = poll;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}
