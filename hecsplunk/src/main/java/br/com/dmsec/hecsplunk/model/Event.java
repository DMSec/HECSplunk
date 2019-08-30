package br.com.dmsec.hecsplunk.model;

import java.util.List;

public class Event {
	
	public String app;
    public String msg;
	List<DataStructure> data;
	
	public Event(String app, String msg, List<DataStructure> data) {
		this.app = app;
		this.msg = msg;
		this.data = data;
	}
    
	public List<DataStructure> getData() {
		return data;
	}
	public void setData(List<DataStructure> data) {
		this.data = data;
	}
    
    

}
