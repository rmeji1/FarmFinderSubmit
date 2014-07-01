package com.farmfinder.model;

/**
 * @author Group: FarmFinder
 * 
 */

import java.util.ArrayList;

import org.bson.BSONObject;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import com.farmfinder.model.Product ;  
import com.mongodb.BasicDBList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Farm {
	@Id private String id ;
	private String name ;
	private String pass ;
	private String street ;
	private String city ;
	private String state ;
	private String zip ;
	private String phoneNum ;
	private String email ;
	private BasicDBList roles ;
	private ArrayList<Product> product ; 
	
	/*******************Setters and Getters***********************/

	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public ArrayList<Product> getProduct() {
		return product;
	}
	public void setProduct(ArrayList<Product> product) {
		this.product = product;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BasicDBList getRoles() {
		return roles;
	}
	public void setRoles(BasicDBList roles) {
		this.roles = roles;
	}

	
}
