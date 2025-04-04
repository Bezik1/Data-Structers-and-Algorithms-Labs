package dsaa.lab01;

import java.util.Scanner;

public class Document {
	public static char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_'};
	public static char[] ALPHABET = {
		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '_',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	};
	public static String BASE_LINK = "link=";
	public static String END_OF_DOCUMENT = "eod";

	public static void loadDocument(String name, Scanner scan) {
		String currentLine = "";
		while(!currentLine.equals(END_OF_DOCUMENT)) {
			currentLine = scan.nextLine();
			String[] splitedLine = currentLine.split(" ");

			for(String link: splitedLine) {
				link = link.toLowerCase();
				if(!correctLink(link)) continue;

				String identifier = getIdentifier(link);
				System.out.println(identifier);
			}
		}
	}

	public static String getIdentifier(String link) {
		return link.replace("link=", "");
	}

	public static boolean correctLink(String link) {
		if(link.length() < 6) return false;
		if(!link.substring(0, 5).equals(BASE_LINK)) return false;

		for(char numbersChar: NUMBERS) {
			if(link.charAt(5) == numbersChar) return false;
		}
		for(int i=5; i<link.length(); i++) {
			boolean correctChar = false;
			for (char letter : ALPHABET) {
				correctChar = letter == link.charAt(i);
				if(correctChar) break;
			}
			if(!correctChar) return false;
		}
		return true;
	}

}
