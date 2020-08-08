package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.hero.Background;

import lombok.Getter;

@Getter
public class BackgroundDto {
	private int id;
	private String name;
	private String source;
	public BackgroundDto(Background background) {
		this.id = background.getId();
		this.name = background.getName();
		this.source = background.getBook().getSource();
	}
}