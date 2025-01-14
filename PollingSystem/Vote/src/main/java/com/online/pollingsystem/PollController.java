package com.online.pollingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PollController {

	@Autowired
	private PollService pollService;

	@GetMapping("/create")
	public String createPollForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		boolean isAdmin = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.anyMatch(role -> role.equals("ROLE_ADMIN"));

		model.addAttribute("poll", new Poll());
		model.addAttribute("isAdmin", isAdmin);

		return "create-poll";
	}

	@PostMapping("/create")
	public String createPollSubmit(@ModelAttribute Poll poll) {
		for (PollOption option : poll.getOptions()) {
			option.setPoll(poll);
		}

		pollService.createPoll(poll);

		return "redirect:/poll-list";
	}

	@GetMapping("/poll-list")
	public String pollList(Model model) {
		List<Poll> polls = pollService.getAllPolls();
		model.addAttribute("polls", polls);
		return "poll-list";
	}

	@GetMapping("/poll/{id}")
	public String pollDetails(@PathVariable Long id, Model model) {
		Optional<Poll> optionalPoll = pollService.getPollById(id);
		if (!optionalPoll.isPresent()) {
			throw new IllegalArgumentException("Invalid poll Id: " + id);
		}
		Poll poll = optionalPoll.get();
		model.addAttribute("poll", poll);
		return "poll-details";
	}

	@PostMapping("/poll/{id}/vote")
	public String voteForPoll(@PathVariable Long id, @RequestParam Long optionId) {
		System.out.println(id + " " + optionId);
		pollService.voteForPoll(id, optionId);
		return "redirect:/confirmation";
	}

	@GetMapping("/confirmation")
	public String voteConfirmation() {
		return "vote-confirmation";
	}

}
