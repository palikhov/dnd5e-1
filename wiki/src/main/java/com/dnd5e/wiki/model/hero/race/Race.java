package com.dnd5e.wiki.model.hero.race;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Source;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.hero.Bonus;
import com.dnd5e.wiki.model.hero.classes.Feature;

import lombok.Data;

@Entity
@Table(name = "races")
@Data
public class Race {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@OneToMany
	@JoinColumn(name = "race_id")
	List<Feature> features;
 
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@OneToMany
	@JoinColumn(name = "race_id")
	private List<Bonus> bonuses;
	
	@OneToMany
	List<Language> languages;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "parent_id")
	private Race parent;
	
	@OneToMany(mappedBy = "parent",  orphanRemoval=true)
	private List<Race> subRaces;

	@Enumerated(EnumType.STRING)
	private CreatureSize size;

	@Enumerated(EnumType.STRING)
	private Source source;
}