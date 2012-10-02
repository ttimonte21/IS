package main.project;

import java.util.ArrayList;

public class ArrayHPROF {

	private String reference;
	private String size;
	private String trace;
	private String numberofElements;
	private String elementType;
	private ArrayList<String> elementRefs;
	
	
	public ArrayHPROF(String reference, String size2, String trace2, String numElements, String type) {
		this.reference = reference;
		this.size = size2;
		this.trace = trace2;
		this.numberofElements = numElements;
		this.elementType = type;
		this.elementRefs = new ArrayList<String>();;
	}
	public ArrayList<String> getElementRefs() {
		return elementRefs;
	}
	public void setElementRefs(ArrayList<String> elementRefs) {
		this.elementRefs = elementRefs;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String number) {
		this.reference = number;
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
	public String getNumberofElements() {
		return numberofElements;
	}
	public void setNumberofElements(String numberofElements) {
		this.numberofElements = numberofElements;
	}
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	
	
	
}
