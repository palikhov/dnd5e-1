package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.repository.RaceRepository;

@Controller
@RequestMapping("/hero/races")
public class RaceController {
	private  RaceRepository repo;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public void setRepo(RaceRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping
	public String getRaces(Model model) {
		List<Race> races = repo.findByParentIsNull(Sort.by(Sort.Direction.ASC, "name"));
		Setting settings = (Setting) session.getAttribute(SettingRestController.HOME_RULE);
		if (settings == null || !settings.isHomeRule()) {
			races = races.stream()
					.filter(r -> r.getBook().getType() == TypeBook.OFFICAL)
					.collect(Collectors.toList());
		}
		model.addAttribute("races", races);
		return "hero/races";
	}

	@GetMapping("/race/{id}")
	public String getRace(Model model, @PathVariable Integer id) {
		model.addAttribute("race", repo.findById(id).get());
		return "hero/raceView";
	}
}