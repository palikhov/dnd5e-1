package com.dnd5e.wiki.model.tavern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd5e.wiki.model.hero.race.Sex;
import com.dnd5e.wiki.model.tavern.TavernaName.ObjectType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "taverna_prefixes")
@Getter
@Setter
public class TavernaPrefixName {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String male;
	private String female;
	@Enumerated(EnumType.STRING)
	private ObjectType objectType;

	public String getName(Sex sex) {
		return Sex.MALE == sex ? name + male : name + female;
	}
}