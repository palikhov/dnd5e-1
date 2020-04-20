package com.dnd5e.wiki.controller;

import java.util.Collections;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;
import com.dnd5e.wiki.model.gods.GodSex;
import com.dnd5e.wiki.model.gods.Pantheon;
import com.dnd5e.wiki.repository.GodRepository;
import com.dnd5e.wiki.repository.PantheonRepository;

@Controller
@Scope("session")
@RequestMapping("/gods")
public class GodController {
	private Alignment aligmentSelected;
	private Domain domainSlected;
	private GodSex sexSelected;
	private Integer selectedPanteonId;
	
	@Autowired
	private GodRepository repository;
	@Autowired
	private PantheonRepository pantheonRepo;
	
	private String search = "";

	@GetMapping
	public String getGods(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<God> specification = null;
		if (!search.isEmpty()) {
			specification = byName(search);
		}
		if (aligmentSelected != null) {
			if (specification == null) {
				specification = byAlignment();
			} else {
				specification = Specification.where(specification).and(bySex());
			}
		}
		if (domainSlected != null) {
			if (specification == null) {
				specification = byDomain();
			} else {
				specification = Specification.where(specification).and(byDomain());
			}
		}
		if (sexSelected != null) {
			if (specification == null) {
				specification = bySex();
			} else {
				specification = Specification.where(specification).and(bySex());
			}
		}
		if (selectedPanteonId != null) {
			if (specification == null) {
				specification = byPatheon();
			} else {
				specification = Specification.where(specification).and(byPatheon());
			}

		}
		if (specification == null) {
			model.addAttribute("gods", repository.findAll(page));
		} else {
			model.addAttribute("gods", repository.findAll(specification, page));
		}
		model.addAttribute("searchText", search);
		model.addAttribute("aligmentSelected", aligmentSelected);
		model.addAttribute("domainSelected", domainSlected);
		model.addAttribute("sexSelected", sexSelected);
		model.addAttribute("alignments", Alignment.values());
		model.addAttribute("domains", Domain.values());
		model.addAttribute("sexs", GodSex.values());
		model.addAttribute("pantheons", pantheonRepo.findAll());
		model.addAttribute("panteonSelectedId", selectedPanteonId);
		model.addAttribute("filtered", sexSelected != null || aligmentSelected != null || domainSlected !=null || selectedPanteonId!=null);
		return "gods";
	}

	@GetMapping(params = { "search" })
	public String searchSpells(Model model, String search) {
		this.search = search.trim();
		return "redirect:/gods?sort=name,asc";
	}

	@GetMapping(params = { "sort", "alignment", "domain", "sex", "panteonType" })
	public String filterByLevels(Model model, String sort, String alignment, String domain, String sex, String panteonType, Pageable page) {
		aligmentSelected = "ALL".equals(alignment) ? null : Alignment.valueOf(alignment);
		domainSlected = "ALL".equals(domain) ? null : Domain.valueOf(domain);
		sexSelected = "ALL".equals(sex) ? null : GodSex.valueOf(sex);
		selectedPanteonId = "ALL".equals(panteonType) ? null : Integer.valueOf(panteonType.trim());
		return "redirect:/gods?sort=" + sort;
	}
	
	@GetMapping(params = { "clear" })
	public String cleaarFilters(Model model, String search) {
		this.search = "";
		aligmentSelected = null;
		domainSlected = null;
		sexSelected = null;
		selectedPanteonId = null;
		return "redirect:/gods?sort=name,asc";
	}

	private Specification<God> byName(String search) {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("commitment"), "%" + search + "%"));
	}
	
	private Specification<God> byAlignment() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("aligment"), aligmentSelected));
	}

	private Specification<God> bySex() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("sex"), sexSelected));
	}
	
	private Specification<God> byDomain() {
		return (root, query, cb) -> {
			Join<God, Domain> domain = root.join("domains", JoinType.LEFT);
			return domain.in(Collections.singleton(domainSlected));
		};	
	}
	
	private Specification<God> byPatheon() {
		return (root, query, cb) -> {
			Join<Pantheon, God> pantheon = root.join("pantheon", JoinType.LEFT);
			return cb.equal(pantheon.get("id"), (selectedPanteonId));
		};	
	}
}