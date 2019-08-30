package br.com.dmsec.hecsplunk.model;



public class Event {
	
	public String app;
    public String msg;
	//List<DataStructure> data;
    public DataStructure data;
	
	public Event(String app, String msg, DataStructure data) {
		this.app = app;
		this.msg = msg;
		this.data = data;
	}
    
	
	
	
	
    
    

}
