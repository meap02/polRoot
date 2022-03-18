import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class polRoot {
	public static void main(String[] args) {
		
		RooterInterface poly;

		/*
		 * This switch statement will take care of the main
		 * methods that will need to be run
		 */
		List<Object> answer = new ArrayList<Object>(3);
		switch(args[0]) {
		///////////////////////////////////////////////////////////////////////////////////////////////////
		case "-newt":
			try {
				if(args[1].equals("-maxIt")) {
					poly = new Rooter(args[4]);
					answer = poly.newton(Float.valueOf(args[3]), Integer.valueOf(args[2]));
				}else {
					poly = new Rooter(args[2]);
					answer = poly.newton(Float.valueOf(args[1]));
				}
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\" + poly.getName() + ".sol");
				for(int i = 0; i < 2; i++)
					writer.write(answer.get(i) + " ");
				writer.write((String)answer.get(2));
				writer.close();
			}catch(IOException e){
				System.out.println("The file could not be used to initialze the program");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Not enough arguments were given to calculate the roots");
			}catch(NumberFormatException e) {
				System.out.println("One or more of the arguments was not given in the correct format");
			}catch(Exception e) {
				System.out.println("An unknown error has occured: " + e.getMessage());
			}
			break;
		///////////////////////////////////////////////////////////////////////////////////////////////////
		case "-sec":
			try {
				if(args[1].equals("-maxIt")) {
					poly = new Rooter(args[5]);
					answer = poly.secant(Float.valueOf(args[3]), Float.valueOf(args[4]), Integer.valueOf(args[2]));
				}else {
					poly = new Rooter(args[3]);
					answer = poly.secant(Float.valueOf(args[1]), Float.valueOf(args[2]));
				}
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\" + poly.getName() + ".sol");
				for(int i = 0; i < 2; i++)
					writer.write(answer.get(i) + " ");
				writer.write((String)answer.get(2));
				writer.close();
			}catch(IOException e){
				System.out.println("The file could not be used to initialze the program");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Not enough arguments were given to calculate the roots");
			}catch(NumberFormatException e) {
				System.out.println("One or more of the arguments was not given in the correct format");
			}catch(Exception e) {
				System.out.println("An unknown error has occured: " + e.getMessage());
			}
			break;
		///////////////////////////////////////////////////////////////////////////////////////////////////
		case "-hybrid":
			try {
				if(args[1].equals("-maxIt")) {
					poly = new Rooter(args[5]);
					answer = poly.hybrid(Float.valueOf(args[3]), Float.valueOf(args[4]), Integer.valueOf(args[2]));
				}else {
					poly = new Rooter(args[3]);
					answer = poly.hybrid(Float.valueOf(args[1]), Float.valueOf(args[2]));
				}
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\" + poly.getName() + ".sol");
				for(int i = 0; i < 2; i++)
					writer.write(answer.get(i) + " ");
				writer.write((String)answer.get(2));
				writer.close();
			}catch(IOException e){
				System.out.println("The file could not be used to initialze the program");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Not enough arguments were given to calculate the roots");
			}catch(NumberFormatException e) {
				System.out.println("One or more of the arguments was not given in the correct format");
			}catch(Exception e) {
				System.out.println("An unknown error has occured: " + e.getMessage());
			}
			break;
		default:
			try {
				if(args[0].equals("-maxIt")) {
					poly = new Rooter(args[4]);
					answer = poly.bisect(Float.valueOf(args[2]), Float.valueOf(args[3]), Integer.valueOf(args[1]));
				}else {
					poly = new Rooter(args[2]);
					answer = poly.bisect(Float.valueOf(args[0]), Float.valueOf(args[1]));
				}
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\" + poly.getName() + ".sol");
				for(int i = 0; i < 2; i++)
					writer.write(answer.get(i) + " ");
				writer.write((String)answer.get(2));
				writer.close();
			}catch(IOException e){
				System.out.println("The file could not be used to initialze the program");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Not enough arguments were given to calculate the roots");
			}catch(NumberFormatException e) {
				System.out.println("One or more of the arguments was not given in the correct format");
			}catch(Exception e) {
				System.out.println("An unknown error has occured: " + e.getMessage());
			}
		break;
		}//END OF SWITCH
	}//END OF MAIN
}//END OF CLASS
