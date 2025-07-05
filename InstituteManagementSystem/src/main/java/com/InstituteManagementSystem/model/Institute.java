package com.InstituteManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Institute {
	
	@Id
	 @GeneratedValue
	
	
	private Long id;
	private String universityname;
	private String name;
	private String type;
	private String adress;
	private String phone;
	private String email;
	public Institute() {
		super();
	}
	public Institute(Long id, String universityname, String name, String type, String adress, String phone,
			String email) {
		super();
		this.id = id;
		this.universityname = universityname;
		this.name = name;
		this.type = type;
		this.adress = adress;
		this.phone = phone;
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUniversityname() {
		return universityname;
	}
	public void setUniversityname(String universityname) {
		this.universityname = universityname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
