package br.com.dmsec.hecsplunk.model;

public class DataStructure {
	
	
	
	//Imṕlementar mais informaçoes
	
	public String cod;
	public String description;

	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "DataStructure";
	}
	
	
	
	
	
}
