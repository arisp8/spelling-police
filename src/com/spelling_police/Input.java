/**
 * @class 
 * Reads a text from user's keyboard or uploaded file
 */
import java.util.Scanner;

public class Input {

	/**
	 * Read from keyboard
	 * @return String text
	 */
	public static String keyboard() {
		System.out.println("Please, write the text you want to check");
		Scanner in = new Scanner(System.in);
		String text = in.nextLine();
		return text;
	}

}
