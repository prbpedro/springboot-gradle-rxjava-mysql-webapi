package com.github.prbpedro.springboot.webapi.gradle.entities;

import javax.persistence.*;

@Entity
public class DumbEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
	private String transientString;

	public DumbEntity(Long id) {
		this.id = id;
	}

	public DumbEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransientString() {
		return transientString;
	}

	public void setTransientString(String transientString) {
		this.transientString = transientString;
	}
}
