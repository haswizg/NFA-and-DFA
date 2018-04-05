import java.util.Stack;

/*
 * convert from infix to postfix. 
 * When a conversion is made, the process contains the characters of the converted element, either a letter or a operator.
 * if the process letter considers it as root of tree
 * if the operator there are two types is one variable,
 * It takes the last existing root, and the operator process make the last root a child and the root is the operator.
 * The second type contains two variables, which takes the last two root.
 * In the end of the RE is true if and only if there only one tree ( one root ).
 * after that call pre_build in class DFA_build to do DFA step on the tree created in this class
 */

public class DFA_parser {
	private static Stack<Character> Opr_Stack=new Stack<Character>();	//stack to convert the operator from INFIX to POSTFIX by the popular method
		
	public static Stack<Character> getOpr_stack() {
		return Opr_Stack;
	}
	
	public static void parserDFA(String expToken) {
		System.out.print("as PostFix : ");
		//monitoring the RE and analyze it
		for(int i=0; i<expToken.length(); i++) {
			char tmp_Char=expToken.charAt(i);
			// '(' symbol open new scope
			if(tmp_Char=='(') {
				DFA_parser.getOpr_stack().push(tmp_Char);
			}else if(tmp_Char==')') {
				// ')' symbol close last scope open . so every thing in between should calculated and done 
				while(true) {
					if(DFA_parser.getOpr_stack().isEmpty()) {
						System.out.println("exp error cant close scope not exist");
					}
					//to calculate what in between
					char tmp_Opr=DFA_parser.getOpr_stack().pop();
					//if it's not the start then do it
					if(tmp_Opr!='(') {
						DFA_parser.call_opr(tmp_Opr);
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
				if(!DFA_parser.getOpr_stack().isEmpty()) {
					//take the top of stack
					char peek_Opr=DFA_parser.getOpr_stack().peek();
					boolean concat=(tmp_Char=='.'&&peek_Opr=='.');
					if(peek_Opr=='+' || peek_Opr=='*' || concat || tmp_Char=='|') {
						while(true) {
							if(!DFA_parser.getOpr_stack().isEmpty()) {
								if(DFA_parser.getOpr_stack().peek()=='(') {
									break;
								}
								DFA_parser.call_opr(DFA_parser.getOpr_stack().pop());
							}else {
								break;
							}
						}
					}
				}
				//add the opr in stack
				DFA_parser.getOpr_stack().push(tmp_Char);
			}else {
				//if it's letter
				System.out.print(tmp_Char); //print as post fix
				//add to alphabet if not exist . add last to be in order as RE wrote
				if(!start_parser.getAlphabet().contains(tmp_Char)) {
					start_parser.getAlphabet().addLast(tmp_Char);
				}
				//create the letter node
				new DFA_tree_item(tmp_Char);
			}
		}
		//pop everything in opr_stack after end monitoring the RE , till opr_stack become empty and then get out of the while 
		while(true) {
			if(!DFA_parser.getOpr_stack().isEmpty()) {
				DFA_parser.call_opr(DFA_parser.getOpr_stack().pop());
			}else {
				System.out.println(""); //end of postfix printing
				DFA_build.pre_build(); //to do the DFA steps
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
			DFA_tree_operator.opr_unary(opr);
			break;
		case '*':
			DFA_tree_operator.opr_unary(opr);
			break;
		case '.':
			DFA_tree_operator.opr_binary(opr);
			break;
		case '|':
			DFA_tree_operator.opr_binary(opr);
			break;
		default:
			System.out.println("faild :(\nunkown operator");
		}
	}
	
	//print the DFA tree starting with the root and his child will print by recursion
	public static void print_DFA_tree() {
		System.out.print("DFA tree printing ...");
		DFA_build.getDFA_tree_start().get(0).print_tree_node();
		System.out.println("");
	}

	
	//print the DFA state one by one
	public static void printDFA() {
		System.out.println("DFA printing ...");
		for(DFA_state st_on:DFA_build.get_states_list()) {
			st_on.print_state();
		}
	}

}