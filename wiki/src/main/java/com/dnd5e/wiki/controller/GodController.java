package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entities/gods")
public class GodController {

	@GetMapping
	public String getGods()
	{
		return "datatable/gods";
	}
}