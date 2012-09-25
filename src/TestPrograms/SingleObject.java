package TestPrograms;


public class SingleObject {


	public static void main(String[] args){
		SingleObject obj = new SingleObject();
		obj.run();
	}

	private void run() {
		for(int i = 0; i < 1234; i++){
			System.out.println(12345);
		}

	}
}
