package com.InstituteManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Admin {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	private String designstion;

	@ManyToOne
	@JoinColumn(name = "institute")
	private Institute institute;

	public Admin() {
		super();
	}

	public Admin(Long id, String name, String email, String designstion, Institute institute) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.designstion = designstion;
		this.institute = institute;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignstion() {
		return designstion;
	}

	public void setDesignstion(String designstion) {
		this.designstion = designstion;
	}



	public Institute getInstitute() {
		return institute;
	}


	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

}
