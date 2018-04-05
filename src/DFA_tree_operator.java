

/*
 * bunch of function which are the operators used in regular expression
 */

public class DFA_tree_operator {

	public static void opr_binary(char opr) {
		/* make sure there is two item at less
		 * 1st : start point list not empty ? (there is at less one)
		 * 2ed : first item and last one in start point not same element (it's more then one)
		 */
		if(DFA_build.getDFA_tree_start().isEmpty()||DFA_build.getDFA_tree_start().getFirst()==DFA_build.getDFA_tree_start().getLast()) {
			System.out.println("faild :(\nparameters are not enough");
			return;
		} 
		DFA_tree_item opr_item=new DFA_tree_item(opr);				//create opr node (root) , it will become the first index in tree starts
		opr_item.setL_child(DFA_build.getDFA_tree_start().get(2)); 	//take start(2) to be lift child
		opr_item.setR_child(DFA_build.getDFA_tree_start().get(1));	//take start(1) to be right child
		DFA_build.getDFA_tree_start().get(2).setParent(opr_item);	//start(2) parent is start(0) 
		DFA_build.getDFA_tree_start().get(1).setParent(opr_item);	//start(1) parent is start(0)
		DFA_build.getDFA_tree_start().remove(2);	//remove(2)
		DFA_build.getDFA_tree_start().remove(1);	//remove(1)
	}

	public static void opr_unary(char opr) {
		// make sure there is one item at less
		if(DFA_build.getDFA_tree_start().isEmpty()) {
			System.out.println("faild :(\nparameters are not enough");
			return;
		}
		DFA_tree_item opr_item=new DFA_tree_item(opr);				//create opr node (root) , it will become the first index in tree starts
		opr_item.setL_child(DFA_build.getDFA_tree_start().get(1));	//take start(1) to be right child
		DFA_build.getDFA_tree_start().get(1).setParent(opr_item);	//start(1) parent is start(0)
		DFA_build.getDFA_tree_start().remove(1);	//remove(1)
	}
	
}