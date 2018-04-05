import java.util.LinkedList;
import java.util.Scanner;

/*
 * bla bla
 */

public class start_parser {

	//store the alphabet that used in regular expression
	private static LinkedList<Character> alphabet=new LinkedList<Character>(); //store the alphabet in the input expression
	
	//set function for the alphabet linked list , add last to put in order
	public static void setAlphabet(char arge) {
		alphabet.addLast(arge);
	}

	//get function for the alphabet linked list
	public static LinkedList<Character> getAlphabet() {
		return alphabet;
	}
	
	public static void main(String[] args) {
		
		System.out.print("~ Compilers Projec (Part-One) ~"
				+ "\nthis program build an NFA from a given regular expression to convert it into a DFA."
				+ "\nalso build a DFA from a regular expression directly without going through an NFA"
				+ "\n*note: the expression should enter carefully with 'Brackets' and 'dot for concatenate'"
				+ "\n**note: the space and '#' not allowed to use as letter"
				+ "\n=============="
				+ "\nenter the experssion :");
		
		//take input
		Scanner keyboard = new Scanner(System.in);
		String ExpressionToken = keyboard.next();
		keyboard.close();
		
		//check if input contain 'space' or '#' . if yes take new input
		while(ExpressionToken.contains(" ") || ExpressionToken.contains("#")) {
			System.err.println("clearly your input wrong ,try again");
			System.out.println("enter the experssion :");
			ExpressionToken = keyboard.next();
		}
		//no new input in this round, so close the scanner  
		keyboard.close();
		
		//just to output look :)
		System.out.println(" - - - - - - - -");
		
		//this block stand alone   ... -from RE build NFA, convert to DFA-
		{
			//start in NFA 
			NFA_Parser.parserNFA(ExpressionToken);
			System.out.println(" - - - - - - - -");
			
			//print the NFA  ... note: you should't use this before call the parserNFA() or this function will print empty set
			NFA_Parser.printNFA();
			System.out.println(" - - - - - - - -");
			
			//convert the NFA to DFA , and print by itself ... note: you should't use this before call the parserNFA() or this function will do nothing since no NFA_item there 
			Conve_DFA.converge();
			System.out.println(" - - - - - - - -");
		}
		
		//this block stand alone    ... -from RE build DFA directly-
		{
			//start in DFA
			DFA_parser.parserDFA(ExpressionToken);
			System.out.println(" - - - - - - - -");

			//print the tree that created in way doing the build DFA steps   ... note: you should't use this before call the parserDFA() or this function will print empty set
			DFA_parser.print_DFA_tree();
			System.out.println(" - - - - - - - -");

			//print the DFA  ... note: you should't use this before call the parserDFA() or this function will print empty set
			DFA_parser.printDFA();
			System.out.println(" - - - - - - - -");
		}
		
		//conclusion 
		System.out.println("=============="
				+ "\nHassan.B.AlWizrah"
				+ "\nst.ID: 436026573"
				+ "\nhaswizg@gmail.com");
	}
	
}
