package no.hvl.pollapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class VoteOption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String optionText;

    @ManyToOne(optional = false)
    @JoinColumn(name = "poll_id")
    @JsonBackReference
    private Poll poll;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Vote> votes;

    public VoteOption() {}
    public VoteOption(String optionText, Poll poll) {
        this.optionText = optionText;
        this.poll = poll;
    }

    public Long getId() { return id; }
    public String getOptionText() { return optionText; }
    public Poll getPoll() { return poll; }
    public List<Vote> getVotes() { return votes; }

    public void setId(Long id) { this.id = id; }
    public void setOptionText(String optionText) { this.optionText = optionText; }
    public void setPoll(Poll poll) { this.poll = poll; }
    public void setVotes(List<Vote> votes) { this.votes = votes; }
}
