package com.online.pollingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollService {

	@Autowired
	private PollRepository pollRepository;

	@Autowired
	private PollOptionRepository pollOptionRepository;

	public Poll createPoll(Poll poll) {
		return pollRepository.save(poll);
	}

	public List<Poll> getAllPolls() {
		return pollRepository.findAll();
	}

	public Optional<Poll> getPollById(Long id) {
		return pollRepository.findById(id);
	}

	public void voteForPoll(Long pollId, Long optionId) {
		Optional<Poll> optionalPoll = pollRepository.findById(pollId);
		if (optionalPoll.isEmpty()) {
			throw new IllegalArgumentException("Invalid poll Id: " + pollId);
		}
		Poll poll = optionalPoll.get();

		Optional<PollOption> optionalOption = pollOptionRepository.findById(optionId);
		if (optionalOption.isEmpty()) {
			throw new IllegalArgumentException("Invalid option Id: " + optionId);
		}

		PollOption option = optionalOption.get();
		option.incrementVotes();

	
		pollOptionRepository.save(option);
		pollRepository.save(poll);
	}


}
