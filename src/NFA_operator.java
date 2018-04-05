/*
 * bunch of function which are the operators used in regular expression
 */
public class NFA_operator {

	public static void opr_or() {
		/* make sure there is two item at less
		 * 1st : start point list not empty ? (there is at less one)
		 * 2ed : first item and last one in start point not same element (it's more then one)
		 */
		if(NFA_Parser.getNFA_start().isEmpty()||NFA_Parser.getNFA_start().getFirst()==NFA_Parser.getNFA_start().getLast()) {
			System.out.println("faild :(\nparameters are not enough");
			return;
		} 
		// already point 1 go to 2
		// already point 3 go to 4
		new NFA_item(' '); // create 5 go to 6
		/* 
		 * index  -0- -1- -2- -n-
		 * start | 5 | 3 | 1 | a
		 * final | 6 | 4 | 2 | b
		 */
		NFA_Parser.getNFA_start().get(0).getNode_One().setNext(NFA_Parser.getNFA_start().get(2)); // 5 go to 1
		NFA_Parser.getNFA_start().get(0).getNode_Two().setNext(NFA_Parser.getNFA_start().get(1)); // 5 go to 3
		NFA_Parser.getNFA_final().get(2).getNode_One().setNext(NFA_Parser.getNFA_final().get(0)); // 2 go to 6
		NFA_Parser.getNFA_final().get(1).getNode_One().setNext(NFA_Parser.getNFA_final().get(0));// 4 go to 6
		NFA_Parser.getNFA_start().remove(2); // 1 not start anymore
		NFA_Parser.getNFA_start().remove(1); // 3 not start anymore
		NFA_Parser.getNFA_final().remove(2); // 2 not final anymore
		NFA_Parser.getNFA_final().remove(1); // 4 not final anymore
	}
	
	public static void opr_cont() {
		// make sure there is two item at less
		if(NFA_Parser.getNFA_start().isEmpty()||NFA_Parser.getNFA_start().getFirst()==NFA_Parser.getNFA_start().getLast()) {
			System.out.println("faild :(\nparameters are not enough");
			return;
		}
		/*
		 * already point 1 go to 2
		 * already point 3 go to 4
		 * 
		 * index  -0- -1- -n- 
		 * start | 3 | 1 | a 
		 * final | 4 | 2 | b
		 */
		NFA_Parser.getNFA_final().get(1).setNode_One_Next(NFA_Parser.getNFA_start().get(0)); // 2 go to 3
		NFA_Parser.getNFA_start().remove(0); // 3 not start anymore
		NFA_Parser.getNFA_final().remove(1); // 2 not final anymore
	}
	
	public static void opr_star() {
		// make sure there is one item at less
		if(NFA_Parser.getNFA_start().isEmpty()) {
			System.out.println("faild :(\nparameters are not enough");
			return;
		} 
		// already point 1 go to 2
		new NFA_item(' '); // create 3 go to 4 
		/* 
		 * index  -0- -1- -n- 
		 * start | 3 | 1 | a 
		 * final | 4 | 2 | b
		 */
		NFA_Parser.getNFA_final().get(1).getNode_One().setNext(NFA_Parser.getNFA_start().get(1)); // 2 go to 1
		NFA_Parser.getNFA_start().get(0).getNode_Two().setNext(NFA_Parser.getNFA_start().get(1)); // 3 go to 1
		NFA_Parser.getNFA_final().get(1).getNode_Two().setNext(NFA_Parser.getNFA_final().get(0)); // 2 go to 4
		NFA_Parser.getNFA_start().remove(1); // 1 not start anymore
		NFA_Parser.getNFA_final().remove(1); // 2 not final anymore
	}
	
	public static void opr_plus() {
		// make sure there is one item at less
		if(NFA_Parser.getNFA_start().isEmpty()) {
			System.out.println("faild :(\nparameters are not enough");
			return;
		} 
		/* 
		 * already point 1 go to 2
		 * index  -0- -n- 
		 * start | 1 | a
		 * final | 2 | b
		 */
		NFA_Parser.getNFA_final().get(0).getNode_Two().setNext(NFA_Parser.getNFA_start().get(0));// 2 go to 1
		/* the start and final still as it is
		 * 
		 * note : 
		 * ( the final point is not go to null in both of his nodes
		 * so what could happen after this move ?
		 * 
		 * either "or" or "concatenate" use the first node of the final point
		 * because of that we used the second node in point that is final point
		 * which mean no conflict here ) 
		 * 
		 * "plus" or "star" ? should logical error occur
		 */
	}
}
