import java.io.IOException;

public class polRoot {
	public static void main(String[] args) {
		
		RooterInterface poly;
		for(int i = 0; i < args.length; i++)
			System.out.println(args[i]);
		/*
		 * This switch statement will take care of the main
		 * methods that will need to be run
		 */
		switch(args[0]) {
		case "-newt":
			if(args[1].equals("-maxIt"))
				try {
					poly = new Rooter(args[4]);
					poly.newton(Float.valueOf(args[3]), Integer.valueOf(args[2]));
					
				}catch(IOException e){
					System.out.println("The file could not be used to initialze");
				}catch(RootDoesNotConvergeException e) {
					System.out.println(e.getMessage() + ": " + e.getEstimate());
				}
			else
				try {
					poly = new Rooter(args[2]);
					poly.newton(Float.valueOf(args[1]));
				}catch(IOException e){
					System.out.println("The file could not be used to initialze");
				}catch(RootDoesNotConvergeException e) {
					System.out.println(e.getMessage() + ": " + e.getEstimate());
				}
			break;
		case "-sec":
			if(args[1].equals("-maxIt"))
				try {
					poly = new Rooter(args[5]);
					poly.secant(Float.valueOf(args[3]), Float.valueOf(args[4]), Integer.valueOf(args[2]));
				}catch(IOException e){
					System.out.println("The file could not be used to initialze");
				}catch(RootDoesNotConvergeException e) {
					System.out.println(e.getMessage() + ": " + e.getEstimate());
				}
			else
				try {
					poly = new Rooter(args[2]);
					poly.newton(Float.valueOf(args[1]));
				}catch(IOException e){
					System.out.println("The file could not be used to initialze");
				}
			break;
				
		}
	}

}
