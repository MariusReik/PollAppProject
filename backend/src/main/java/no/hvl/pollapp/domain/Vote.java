package no.hvl.pollapp.domain;

import jakarta.persistence.*;

@Entity
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "option_id")
    private VoteOption option;

    public Vote() {}
    public Vote(User user, VoteOption option) {
        this.user = user; this.option = option;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public VoteOption getOption() { return option; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setOption(VoteOption option) { this.option = option; }
}
