package dsaa.lab04;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Document{
	public static char[] ALPHABET = {
		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '_',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	};
	public static String BASE_LINK = "link=";
	public static String END_OF_DOCUMENT = "eod";

	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}
	public void load(Scanner scan) {
		while (scan.hasNextLine()) {
			String currentLine = scan.nextLine();
			if (currentLine.equals(END_OF_DOCUMENT)) {
				break;
			}
			String[] splitedLine = currentLine.split(" ");

			for (String currentLink : splitedLine) {
				if(!currentLink.contains("link=")) continue;
				currentLink = currentLink.toLowerCase();
				Link newLink = createLink(currentLink.replace("link=", ""));
				if(newLink != null) this.link.add(newLink);
			}
		}
	}
	public static boolean isCorrectId(String id) {
		if(id.charAt(0) == '_') return false;
		id = id.toLowerCase();

		char[] validChars = {
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '_',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		};

		for(int i=0; i<id.length(); i++) {
			boolean valid = false;
			for(char validChar: validChars) {
				if(id.charAt(i) == validChar) valid = true;
			}
			if(!valid) return false;
		}
		return true;
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static Link createLink(String link) {
		int splitIndex = link.indexOf("(");
		if(splitIndex == -1) {
			if(!isCorrectId(link)) return null;

			return new Link(link, 1);
		}

		String id = link.substring(0, splitIndex);
		String weightString = link.substring(splitIndex);

		if(!isCorrectId(id)) return null;
		if(weightString.charAt(weightString.length()-1) != ')') return null;

		int weight = 1;
		char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		if(weightString.length() != 0) {
			String modifiedWeightString =
				weightString
					.replace("(", "")
					.replace(")", "");

			for(int i=0; i<modifiedWeightString.length(); i++) {
				boolean valid = false;
				for(char number: NUMBERS) {
					if(modifiedWeightString.charAt(i) == number) valid = true;
				}
				if(!valid) return null;
			}
			weight = Integer.parseInt(modifiedWeightString);
		}

		return new Link(id, weight);
	}

	@Override
	public String toString() {
		String retStr="Document: "+name;

		if (!link.isEmpty()) {
			Iterator<Link> iterator = link.iterator();
			while (iterator.hasNext()) {
				if (iterator.hasNext()) {
					retStr += "\n";
				}

				Link currentLink = iterator.next();
				retStr += currentLink.toString();
			}
		}
		return retStr;
	}

	public String toStringReverse() {
		String retStr="Document: "+name;
		ListIterator<Link> iter=link.listIterator();
		while(iter.hasNext())
			iter.next();
		//TODO
		while(iter.hasPrevious()){
		}
		return retStr;
	}
}

