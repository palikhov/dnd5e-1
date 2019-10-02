package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.classes.Feature;

import lombok.Getter;

@Getter
public class TraitVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String text;

	@XmlElement(required = false)
	private Integer charges;
	@XmlElement(required = false)
	private Integer recharge;

	public TraitVO() {
	}

	public TraitVO(String name, String text) {
		this.name = StringUtils.capitalize(name.toLowerCase());
		this.text = Conpendium.removeHtml(text);
		setRecharge();
	}

	public TraitVO(Feature feature) {
		name = feature.getName();
		setRecharge();
		text = Conpendium.removeHtml(feature.getDescription());
	}

	private void setRecharge() {
		if (name.contains("2-6")) {
			recharge = 2;
		} else if (name.contains("3-6")) {
			recharge = 3;
		} else if (name.contains("4-6")) {
			recharge = 4;
		} else if (name.contains("5-6")) {
			recharge = 5;
		} else if (name.contains("6") && !name.toLowerCase().contains("/день")) {
			recharge = 6;
		}
		if (name.toLowerCase().contains("/день")) {
			recharge = 1;
			if (name.contains("1")) {
				charges = 1;
			} else if (name.contains("2")) {
				charges = 2;
			} else if (name.contains("3")) {
				charges = 3;
			} else if (name.contains("4")) {
				charges = 4;
			} else if (name.contains("5")) {
				charges = 5;
			}
			if (name.contains("перезарядка после короткого")) {
				recharge = 7;
			}
		}
	}
}