package com.online.pollingsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm() {
	
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(User user, Model model) {
		user.setRole("ROLE_USER");
		
		if(userService.findByUsername(user)) {
			model.addAttribute("error", "Username Already Exists");
			return "register";
		}
		
		userService.saveUser(user, null);
		return "redirect:/login";
	}
	
	@PostMapping("/error")
	public String logger() {
		return "Errors";
		
	}

}
