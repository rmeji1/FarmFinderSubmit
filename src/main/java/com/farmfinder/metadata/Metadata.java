package com.farmfinder.metadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.farmfinder.model.Farm;
import com.farmfinder.model.Product;

/*
 * This class will be used to add metadata info about 
 * a particular class to a collection.   
 */

public class Metadata {	
	private String title ; /*Class title*/
	private ArrayList<HashMap> attributes ; 	/*Class vars will be stored here with description*/
	
	/*Empty constructor, because Spring Repo needs it declared.*/
	public Metadata(){}
	/*Constructor to set title quicker*/
	public Metadata(String title){
		this.title = title ;
	}
	public Metadata(Class obj){
		this.title = obj.getSimpleName() ;
		System.out.println(obj.getName()) ;
		getAttributes(obj) ;
	}
	
	/*Getters and setters*/
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<HashMap> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<HashMap> attributes) {
		this.attributes = attributes;
	} 
	
	/*Method to add an attribute for a class*/
	public void addAttribute( HashMap attribute ){
		this.attributes.add(attribute) ;
	}
	
	/*Get all the fields of a class and add it to the attributes array*/
	private void getAttributes(Class obj){
		Field[] fields = obj.getDeclaredFields() ;
		System.out.println(fields.length) ;
		this.attributes = new ArrayList<HashMap> () ;
		
		
		for ( Field field : fields ){
			if( field.getName().compareTo("id") != 0 ){
				HashMap<String,String> map = new HashMap<String, String>() ;
				map.put("fieldName", field.getName() ) ;
				map.put("fieldType", field.getType().getSimpleName() ) ;
				System.out.println(field.getName() +field.getType().getSimpleName() ) ;
				this.attributes.add(map) ;
			}
		}
	}
	
	public static void main(String[] args){
		Metadata meta = new Metadata(Product.class) ;
		System.out.println(meta.getAttributes().toString()) ;
	}
}
