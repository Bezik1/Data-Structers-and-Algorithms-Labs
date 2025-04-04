package dsaa.lab03;

import java.util.Iterator;
import java.util.Scanner;

public class Document{
	public static char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_'};
	public static char[] ALPHABET = {
		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '_',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	};
	public static String BASE_LINK = "link=";
	public static String END_OF_DOCUMENT = "eod";

	public String name;
	public TwoWayUnorderedListWithHeadAndTail<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name;
		link=new TwoWayUnorderedListWithHeadAndTail<Link>();
		load(scan);
	}
	public void load(Scanner scan) {
		while (scan.hasNextLine()) {
			String currentLine = scan.nextLine();
			if (currentLine.equals(END_OF_DOCUMENT)) {
				break;
			}
			String[] splitedLine = currentLine.split(" ");

			for (String link : splitedLine) {
				link = link.toLowerCase();
				if (!correctLink(link)) continue;

				Link identifier = getIdentifier(link);
				this.link.add(identifier);
			}
		}
	}

	private static Link getIdentifier(String link) {
		return new Link(link.replace("link=", ""));
	}

	private static boolean correctLink(String link) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Document: ").append(name);

		if (!link.isEmpty()) {
			//sb.append("\n");
			Iterator<Link> iterator = link.iterator();
			while (iterator.hasNext()) {
				if (iterator.hasNext()) {
					sb.append("\n");
				}

				Link link = iterator.next();
				sb.append(link.ref);
			}
		}

		return sb.toString();
	}

	public String toStringReverse() {
		String retStr="Document: "+name;
		return retStr+link.toStringReverse();
	}

}
