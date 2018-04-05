import java.util.LinkedList;
import java.util.Stack;

/*
 * convert from infix to postfix. when its convert 
 * When a conversion is made, the process contains the characters of the converted element, either a letter or a operator.
 * if the process letter considers it as start state
 * if the operator there are two types is one variable,
 * It takes the last existing state, and the operator process deletes the last start state and creates a new start.
 * The second type contains two variables, which takes the last two start state, and the operator process deletes the last two start state and creates a new start.
 * In the end of the RE is true if and only if the NFA has one start state only.
 */

public class NFA_Parser {
	private static LinkedList<NFA_item> NFA_start=new LinkedList<NFA_item>(); //store all start points in NFA 
	private static LinkedList<NFA_item> NFA_final=new LinkedList<NFA_item>(); //store all final points in NFA
	private static Stack<Character> Opr_Stack=new Stack<Character>();	//stack to convert the operator from INFIX to POSTFIX by the popular method
	
	public static void setNFA_start(NFA_item s_start) {
		NFA_start.addFirst(s_start);
	}

	public static LinkedList<NFA_item> getNFA_start() {
		return NFA_start;
	}
	
	public static void setNFA_final(NFA_item s_final) {
		NFA_final.addFirst(s_final);
	}

	public static LinkedList<NFA_item> getNFA_final() {
		return NFA_final;
	}
	
	public static Stack<Character> getOpr_stack() {
		return Opr_Stack;
	}
	
	public static void parserNFA(String expToken) {		
		System.out.print("as PostFix : ");
		//monitoring the RE and analyze it
		for(int i=0; i<expToken.length(); i++) {
			char tmp_Char=expToken.charAt(i);	
			// '(' symbol open new scope
			if(tmp_Char=='(') {
				NFA_Parser.getOpr_stack().push(tmp_Char);
			}else if(tmp_Char==')') {
				// ')' symbol close last scope open . so every thing in between should calculated and done 
				while(true) {
					if(NFA_Parser.getOpr_stack().isEmpty()) {
						System.out.println("exp error cant close scope not exist");
					}
					//to calculate what in between
					char tmp_Opr=NFA_Parser.getOpr_stack().pop();
					//if it's not the start then do it
					if(tmp_Opr!='(') {
						NFA_Parser.call_opr(tmp_Opr);
					}else {
						//if it the scope start the stop
						break;
					}
				}
			}else if(tmp_Char=='+' || tmp_Char=='*' || tmp_Char=='.' || tmp_Char=='|') {
				/*
				 * if it is operator then check the convert from infix to postfix rule
				 * the order is : + * the highest
				 * 				   .
				 * 				   |  the lowest
				 * if opr_stack not empty the compernt between the in stack and income
				 */
				if(!NFA_Parser.getOpr_stack().isEmpty()) {
					//take the top of stack
					char peek_Opr=NFA_Parser.getOpr_stack().peek();
					boolean concat=(tmp_Char=='.'&&peek_Opr=='.');
					if(peek_Opr=='+' || peek_Opr=='*' || concat || tmp_Char=='|') {
						while(true) {
							if(!NFA_Parser.getOpr_stack().isEmpty()) {
								if(NFA_Parser.getOpr_stack().peek()=='(') {
									break;
								}
								NFA_Parser.call_opr(NFA_Parser.getOpr_stack().pop());
							}else {
								break;
							}
						}
					}
				}
				//add the opr in stack
				NFA_Parser.getOpr_stack().push(tmp_Char);
			}else {
				//if it's letter
				System.out.print(tmp_Char); //print as post fix
				//add to alphabet if not exist . add last to be in order as RE wrote
				if(!start_parser.getAlphabet().contains(tmp_Char)) {
					start_parser.getAlphabet().addLast(tmp_Char);
				}
				//create the letter node
				new NFA_item(tmp_Char);
			}
		}
		//pop everything in opr_stack after end monitoring the RE , till opr_stack become empty and then get out of the while 
		while(true) {
			if(!NFA_Parser.getOpr_stack().isEmpty()) {
				NFA_Parser.call_opr(NFA_Parser.getOpr_stack().pop());
			}else {
				System.out.println(""); //end of postfix printing
				break;
			}
		}
		//end the parser
	}
	
	//just call operator function
	public static void call_opr(char opr) {
		System.out.print(opr); //print as post fix
		switch (opr) {
		case '+':
			NFA_operator.opr_plus();
			break;
		case '*':
			NFA_operator.opr_star();
			break;
		case '.':
			NFA_operator.opr_cont();
			break;
		case '|':
			NFA_operator.opr_or();
			break;
		default:
			System.out.println("faild :(\nunkown operator");
		}
	}
	
	//print the first start state (and should be the only start) if exist , by recursion all states will print with their arrows
	public static void printNFA() {
		System.out.println("NFA printing ...");
		//check if it only start
		if(NFA_Parser.getNFA_start().size()==1) {
			//to know what is the start state and the end
			System.out.println("the start state is "+NFA_Parser.getNFA_start().get(0).getId());
			System.out.println("the final state is "+NFA_Parser.getNFA_final().get(0).getId());
			//print the start state
			NFA_Parser.getNFA_start().getFirst().printNFAitem();
		}else {
			System.out.println("oops more then one start :(\nmaybe invalid input");
		}
	}
}