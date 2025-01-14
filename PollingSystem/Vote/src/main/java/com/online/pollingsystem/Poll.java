package com.online.pollingsystem;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="poll")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollOption> options = new ArrayList<>();

    public Poll() {}

    public Poll(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PollOption> getOptions() {
        return options;
    }

    public void addOption(PollOption option) {
        option.setPoll(this);  // Associate the option with the current poll
        this.options.add(option);
    }

    public void setOptions(List<PollOption> options) {
        this.options = options;
        for (PollOption option : options) {
            option.setPoll(this);  // Set the poll for each option
        }
    }
}
