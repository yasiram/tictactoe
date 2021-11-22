package com.simplegame.TicTacToe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("")
public class IndexController {
    @GetMapping("/")
	public String index() {
		//model.addAttribute("name", name);
		return "home";
	}

	

}
