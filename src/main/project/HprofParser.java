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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}

	private void parseHprof(Scanner input) {
		while(input.hasNextLine()){
			String line = input.nextLine();
			if(line.contains("THREAD START (")){
				this.parseThread(line);
			}else if(line.contains("TRACE") && line.contains(":")){
				this.parseTrace(line);
			}else if(line.contains("ROOT")){
				this.parseRoot(line);
			}else if(line.contains("CLS")){
				this.parseClass(line);
			}else if(line.contains("OBJ")){
				this.parseObject(line);
			}else if(line.contains("ARR")){
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

	}

	private void parseClass(String line) {
		// TODO Auto-generated method stub
		
	}

	private void parseObject(String line) {
		// TODO Auto-generated method stub
		
	}

	private void parseArray(String line) {
		// TODO Auto-generated method stub
		
	}

	private void parseRoot(String line) {
		String number = line.split(" ")[1];
		RootHPROF root = new RootHPROF();
		String[] tokens = line.split(",");
		int equals = tokens[0].indexOf("=");
		String kind = tokens[0].substring(equals +1);
		if(kind.contains("JNI global ref") || kind.contains("thread")){
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
