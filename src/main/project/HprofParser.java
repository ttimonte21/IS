package main.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HprofParser {


	private ArrayList<ThreadHPROF> threads = new ArrayList<ThreadHPROF>();
	private ArrayList<TraceHPROF> traces = new ArrayList<TraceHPROF>();
	private ArrayList<RootHPROF> roots = new ArrayList<RootHPROF>();
	private ArrayList<ObjectHPROF> objects = new ArrayList<ObjectHPROF>();
	private ArrayList<ArrayHPROF> arrays = new ArrayList<ArrayHPROF>();
	private ArrayList<ClassHPROF> classes = new ArrayList<ClassHPROF>();

	private boolean parsingObjectFields;
	private boolean parsingArrayElements;
	private boolean parsingClassInformation;

	private ObjectHPROF currObject;
	private ArrayHPROF currArray;
	private ClassHPROF currClass;

	Scanner input;


	public static void main(String[] args){
		HprofParser parse = new HprofParser();
		parse.doIt();

	}

	private void doIt() {
		File textFile = new File("C:/Users/Thomas/Documents/Tom's Project/IndependentStudy/src/hprof.txt");

		try {

			FileInputStream stream = new FileInputStream(textFile);
			input = new Scanner(stream);


			this.parseHprof(input);



		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}




	}

	private void parseHprof(Scanner input) {
		parsingObjectFields = true;
		parsingArrayElements = true;
		parsingClassInformation = true;


		String checkLine = input.nextLine();
		while(!checkLine.contains("--------")){
			checkLine = input.nextLine();
		}
		while(input.hasNextLine()){
			String line = input.nextLine();
			if(line.startsWith("\t")){
				if(parsingObjectFields){
					this.parseObjectFields(line);
				}else if(parsingArrayElements){
					this.parseArrayElements(line);
				}else if(parsingClassInformation){
					this.parseClassShit(line);
				}
			}else{
				parsingObjectFields = false;
				parsingArrayElements = false;
				parsingClassInformation = false;
			}
			if(line.startsWith("THREAD START (")){
				this.parseThread(line);
			}else if(line.startsWith("TRACE") && line.contains(":")){
				this.parseTrace(line);
			}else if(line.startsWith("ROOT")){
				this.parseRoot(line);
			}else if(line.startsWith("CLS")){
				this.parseClass(line);
			}else if(line.startsWith("OBJ")){
				this.parseObject(line);
			}else if(line.startsWith("ARR")){
				this.parseArray(line);
			}
		}

		for(ThreadHPROF t : threads){
			System.out.println(t.getObj() + " " + t.getId() + " " + t.getName() + " " + t.getGroup());
		}

		for(TraceHPROF t : traces){
			System.out.println(t.getTraceId());
			for(String s : t.getTraces()){
				System.out.println("              " + s);
			}
		}
		for(RootHPROF r : this.roots){
			System.out.println("Roots " + r.getNumber());
		}
		/*for(ArrayHPROF a : this.arrays){
			System.out.println("Array " + a.getReference());
		}*/
		/*for(ObjectHPROF o : this.objects){
			System.out.println("Object " + o.getReference());
		}*/
		for(ClassHPROF c : this.classes){
			System.out.println("Class " + c.getReference());
		}
	}

	private void parseClass(String line) {
		String[] tokens = line.split(" ");

		String reference = tokens[1];
		//getting the name
		String nameString = tokens[2];
		int index = nameString.indexOf("=");
		String name = nameString.substring(index, nameString.length()-1);

		//gettin the trace
		String traceString = tokens[3];
		index = traceString.indexOf("=");
		String trace = traceString.substring(index, traceString.length()-1);

		currClass = new ClassHPROF(reference, name, trace);

		classes.add(currClass);

		this.parsingClassInformation = true;
	}

	private void parseClassShit(String line) {
		if(line.startsWith("super")){
			String reference = line.split(" ")[1];
			//do something with this
			this.currClass.setSuperReference(reference);
		}else if(line.startsWith("static")){
			String[] tokens = line.split(" ");
			String name = tokens[1];
			String reference = tokens[2];
			//do something with this
		}else if(line.startsWith("constant pool entry ")){
			String[] tokens = line.split(" ");
			String number = tokens[3];
			String reference = tokens[4];
			//do something with this 
		}
	}

	private void parseObject(String line) {
		String[] tokens = line.split(" ");

		String reference = tokens[1];
		//finds the size
		String sizeString = tokens[2];
		int index = sizeString.indexOf("=");
		String size = sizeString.substring(index, sizeString.length()-1);

		//finds trace
		String traceString = tokens[3];
		index = traceString.indexOf("=");
		String trace = traceString.substring(index, traceString.length()-1);

		//finds Class
		String classString = tokens[4];
		index = classString.indexOf("=");
		String className = classString.substring(index, classString.length()-1);

		currObject = new ObjectHPROF(reference, size, trace, className);
		objects.add(currObject);
		this.parsingObjectFields = true;
	}

	private void parseObjectFields(String line) {
		String fieldName = line.split("\t")[0];
		String fieldReference = line.split("\t")[1].trim();
		currObject.getFields().put(fieldName,  fieldReference);
	}

	private void parseArray(String line) {
		String[] tokens = line.split(" ");

		String reference = tokens[1];
		//finds the size
		String sizeString = tokens[2];
		int index = sizeString.indexOf("=");
		String size = sizeString.substring(index, sizeString.length()-1);

		//finds trace
		String traceString = tokens[3];
		index = traceString.indexOf("=");
		String trace = traceString.substring(index, traceString.length()-1);

		//finds the Number of elements
		String numOfElementsString = tokens[4];
		index = numOfElementsString.indexOf("=") +1;
		String numElements = numOfElementsString.substring(index, numOfElementsString.length()-1);

		//finds the type of the elements in the array
		String typeString = tokens[6];
		index = typeString.indexOf("=");
		String type = typeString.substring(index, typeString.length()-1);

		currArray = new ArrayHPROF(reference, size, trace, numElements, type);

		arrays.add(currArray);

		this.parsingArrayElements = true;
	}

	private void parseArrayElements(String line) {
		String elemReference = line.split("\t")[1];
		currArray.getElementRefs().add(elemReference);
	}

	private void parseRoot(String line) {
		String number = line.split(" ")[1];
		RootHPROF root = new RootHPROF();
		String[] tokens = line.split(",");
		int equals = tokens[0].indexOf("=");
		String kind = tokens[0].substring(equals +1);
		if(kind.contains("JNI global ref") || (kind.contains("thread") && !kind.contains("block"))){
			String id = tokens[1];
			id = id.substring(id.indexOf("=") + 1);
			root.setId(id);

			String trace = tokens[2];
			trace = trace.substring(trace.indexOf("=")+1);
			root.setTrace(trace);

			root.setKind(kind);
			root.setNumber(number);		
		}else if(kind.contains("system class")){
			String name = tokens[1];
			name = name.substring(name.indexOf("=") +1);
			root.setName(name);
			root.setKind(kind);
			root.setNumber(number);
		}else if(kind.contains("Java stack")){
			String thread = tokens[1];
			thread = thread.substring(thread.indexOf("=") + 1);
			root.setThread(thread);

			String frame = tokens[2];
			frame = frame.substring(frame.indexOf("=") + 1);
			root.setFrame(frame);
			root.setKind(kind);
			root.setNumber(number);
		}else{
			root.setKind(kind);
			root.setNumber(number);
		}

		roots.add(root);
	}

	private void parseTrace(String line) {
		TraceHPROF trace = new TraceHPROF();
		String traceId = line.split(" ")[1];
		trace.setTraceId(traceId);

		this.parseTraces(trace);
	}

	private void parseTraces(TraceHPROF trace) {
		ArrayList<String> currTraces = new ArrayList<String>();
		boolean done = false;
		while(!done){
			final String nextLine = input.nextLine();
			if(nextLine.contains("TRACE")){
				trace.setTraces(currTraces);
				this.traces.add(trace);
				this.parseTrace(nextLine);
				done = true;
			}else if(nextLine.contains("HEAP DUMP")){
				trace.setTraces(currTraces);
				this.traces.add(trace);
				//				this.parseHeapDump(nextLine);
				done = true;
			}else{
				currTraces.add(nextLine);
			}
		}
	}

	private void parseThread(String line) {
		ThreadHPROF thread = new ThreadHPROF();

		String[] tokens = line.split(",");

		for(String s : tokens){
			int equalsIndex = s.indexOf("=");
			if(s.contains("obj")){
				thread.setObj(s.substring(equalsIndex+1));
			}else if(s.contains("id")){
				thread.setId(s.substring(equalsIndex+1));
			}else if(s.contains("name")){
				thread.setName(s.substring(equalsIndex+1));
			}else if(s.contains("group")){
				thread.setGroup(s.substring(equalsIndex+1, s.length() -1));
			}	
		}

		threads.add(thread);

	}

}
