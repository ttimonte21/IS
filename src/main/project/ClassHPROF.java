package main.project;

import java.util.HashMap;

public class ClassHPROF {
	private String reference;
	private String name;
	private String trace;
	private HashMap<String, String> stuff;
	private String superReference;


	public ClassHPROF(String reference2, String name2, String trace2) {
		this.reference = reference2;
		this.name = name2;
		this.trace = trace2;
	}

	
	
	public String getSuperReference() {
		return superReference;
	}



	public void setSuperReference(String superReference) {
		this.superReference = superReference;
	}



	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTrace() {
		return trace;
	}


	public void setTrace(String trace) {
		this.trace = trace;
	}


	public HashMap<String, String> getStuff() {
		return stuff;
	}


	public void setStuff(HashMap<String, String> stuff) {
		this.stuff = stuff;
	}
	
	
	
	
}
