package com.online.pollingsystem;

import jakarta.persistence.*;

@Entity
public class PollOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String optionText;

	@ManyToOne
	@JoinColumn(name = "poll_id")
	private Poll poll;

	@Column(name = "vote_count", nullable = false)
	private int voteCount = 0;

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public PollOption() {
	}

	public PollOption(String optionText) {
		this.optionText = optionText;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public void incrementVotes() {
		this.voteCount++;
	}
}