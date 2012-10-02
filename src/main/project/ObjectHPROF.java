package main.project;

import java.util.HashMap;

public class ObjectHPROF {

	
	private String reference;
	private String size;
	private String trace;
	private String className;
	
	private HashMap<String, String> fields;
	
	
	public ObjectHPROF(String reference2, String size2, String trace2, String className2) {
		this.reference = reference2;
		this.size = size2;
		this.trace = trace2;
		this.className = className2;
		fields = new HashMap<String, String>();
	}
	public HashMap<String, String> getFields() {
		return fields;
	}
	public void setFields(HashMap<String, String> fields) {
		this.fields = fields;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTrace() {
		return trace;
	}
	public void setTrace(String trace) {
		this.trace = trace;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
