package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/stock/equipments" })
public class EquipmentController {

	@GetMapping
	public String getAllEquipments()
	{
		return "datatable/equipments";
	}
}