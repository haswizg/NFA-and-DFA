import java.util.HashSet;
import java.util.LinkedList;

public class DFA_build {

	private static LinkedList<DFA_tree_item> DFA_tree_start=new LinkedList<DFA_tree_item>(); //store all start points in DFA 
	private static LinkedList<DFA_tree_item> DFA_tree_leaf=new LinkedList<DFA_tree_item>(); //store all leafs in DFA
	
	/* setter
	 *	add first , because always do operator on the last item add , so add first and get first 
	 */
	public static void setDFA_tree_start(DFA_tree_item s_start) {
		DFA_tree_start.addFirst(s_start);
	}
	//getter
	public static LinkedList<DFA_tree_item> getDFA_tree_start() {
		return DFA_tree_start;
	}
	
	/*
	 * setter
	 * no matter add first or last , just add it
	 */
	public static void setDFA_tree_leaf(DFA_tree_item leaf) {
		DFA_tree_leaf.addFirst(leaf);
	}

	//getter
	public static LinkedList<DFA_tree_item> getDFA_tree_leaf() {
		return DFA_tree_leaf;
	}
	
	//to get a state that will apply theorem on ( as theorem every state take his turn and time )
	private static int state_counter=0;
	//store the state that will create
	private static LinkedList<DFA_state> states=new LinkedList<DFA_state>();
	//return true if to LinkedList has same element ignore the order and the duplicated items 
	public static boolean listEqualsIgnoreOrder(LinkedList<DFA_tree_item> list1, LinkedList<DFA_tree_item> list2) { 
		return new HashSet<>(list1).equals(new HashSet<>(list2));
	}
	
	/*
	 * setter . 
	 * the reason to add last is when we start is impossible know the number of state will create so always we have to start with index 0 and then increase that till last index
	 * so in letter point we will catch it
	 */
	public static void set_states_list(DFA_state income_state) {
		states.addLast(income_state);
	}

	//getter
	public static LinkedList<DFA_state> get_states_list() {
		return states;
	}
	
	/*
	 * take the tree and calculate the first_pos for each node and the follow_pos for leafs
	 * also convert the root from DFA_item phase to State phase , by call build(first state) may create other state
	 * if yes then call build function for each state created
	 * in end , all states of DFA should create and connect with each other
	 */
	public static void pre_build() {
		//as theorem say . and # and cont_opr
		new DFA_tree_item('#');
		DFA_tree_operator.opr_binary('.');
		
		//find first_pos for root by find first his child , the best case is first_pos of leaf is his id 
		DFA_build.first_pos(DFA_build.getDFA_tree_start().get(0));  
		
		//find follow_pos for each leaf in the tree
		for(DFA_tree_item one_leaf:DFA_build.getDFA_tree_leaf()) {
			DFA_build.follow_pos(one_leaf);
		}

		//create first state which is content fisrt_pos for the root
		DFA_build.set_states_list(new DFA_state(DFA_build.getDFA_tree_start().get(0).get_first_pos()));
		
		//if first state created fine , then it's start state
		if(DFA_build.get_states_list().size()>0) {
			DFA_build.get_states_list().get(0).setStart_state(true);
		}
		
		/* 
		 * the (state_counter) start with 0 ; and when we created first state , the size of state list become 1
		 * for that condition is correct for first time , and should be
		 * in while's body , counter increase by 1 , also may that function call create new state which increase state_list size
		 * the idea is do the function for all state create already or will 
		 * get state from state_list by his index , and in same time increase counter for next time
		 */
		while(DFA_build.get_states_list().size()>DFA_build.state_counter) {
			DFA_build.build(DFA_build.get_states_list().get(DFA_build.state_counter++));
		}
	}
	
	/*
	 * for coming state , take his_set of DFA_items
	 * and then apply the theorem all letter with his follow_pos , and create state if not exist with arrow carrying that letter
	 */
	public static void build(DFA_state income_state) {
		//initially state is not end state
		boolean is_end_state = false;
		
		//for each letter create list store all his first_pos
		for(char letter: start_parser.getAlphabet()) {
			LinkedList<DFA_tree_item> all_fisrt_pos=new LinkedList<DFA_tree_item>();
			
			//for all element in income's set check if the letter apply or not
			for(DFA_tree_item one :income_state.getHis_set()){
				if(one.getName()==letter) {
					for(DFA_tree_item single_one :one.get_follow_pos()) {
						if(!all_fisrt_pos.contains(single_one)) {
							all_fisrt_pos.addFirst(single_one);
						}
					}
				}
				/*
				 * also check if income has in his list element that his name # , yes mean it's end state
				 * note # symbol should never be in Alphabet list !!
				 */
				if(one.getName()=='#') {  
					is_end_state=true;
				}
			}
			//after monitoring all income set
			if(all_fisrt_pos.size()>0) { 
				//if (all_first_pos) has at less one item , make sure this set of element not same set of existing state
				// power_set is gonna tell us about that
				DFA_state power_set=null;
				for(DFA_state single_state:DFA_build.get_states_list()) {
					//if exist then take that state content the set
					if(DFA_build.listEqualsIgnoreOrder(single_state.getHis_set(),all_fisrt_pos)) {
						power_set=single_state;
					}
				}
				//if the set exit in other state , then arrow with letter to that state
				if(power_set!=null) {
					income_state.setLetter_arrows(new DFA_state_node(letter, power_set));
				}else {
					//else create state 
					income_state.setLetter_arrows(new DFA_state_node(letter, all_fisrt_pos));
				}
			}
		}
		//in end of income state analyze , decide if end_state ( accepted final ) 
		income_state.setEnd_state(is_end_state);
	}
	
	/*
	 * form up to bottom of tree (because of this we take the root)
	 * calculate the first for left child and the right if exit , then apply the DFA theorem of calculate the first_pos
	 * if the node has no child it mean it's leaf , and his first_pos it is his id  
	 */
	public static LinkedList<DFA_tree_item> first_pos(DFA_tree_item top) {
		if(top.getL_child()!=null) {
			//calculate left's first_pos
			DFA_build.first_pos(top.getL_child());
			//apply the theorem , in all cases ( . | * + ), left taken
			for(DFA_tree_item one:top.getL_child().get_first_pos()) {
				top.set_first_pos(one);	
			}
			//if the top is ( | . ) then we have right child
			if(top.getName()=='|'||top.getName()=='.') {
				//calculate left's first_pos
				DFA_build.first_pos(top.getR_child());
				//as theorem say take the fisrt_pos of right child when the top is ( | ) or (.) when the left is ( * )
				if(top.getName()=='|'||top.getL_child().getName()=='*') {
					for(DFA_tree_item one:top.getR_child().get_first_pos()) {
						top.set_first_pos(one);	
					}				
				}
			}
		}else {
			//if the top has no left child then this leaf , and leaf's fisrt_pos is his id
			top.set_first_pos(top);
			DFA_build.setDFA_tree_leaf(top);
		}
		//after all return top first_pos
		return top.get_first_pos();	
	}
	
	/*
	 * only we want figure out the follow_pos for the leafs
	 *  
	 * calculate the follow for leafs as follow :
	 * 1) if the parent is ( . ) and the node is left child of the parent then the follow is the first_pos of the parent's right child 
	 * 2) if the parent is ( . ) and the node is tight child of the parent then the follow is the follow_pos of the parent ( recursion )
	 * 3) if the parent is ( | ) then the follow is the follow_pos of the parent , no matter where the child are
	 * 4) if the parent is ( * or + ) then the follow is the follow_pos of the parent + the first_pos of the node itself
	 * 5) if the parent not exist then WTF!!
	 */
	public static LinkedList<DFA_tree_item> follow_pos(DFA_tree_item one) {
		if(one.getParent()!=null) {
			// case #1
			if(one.getParent().getName()=='.'&&one.getParent().getL_child()==one) {
				for(DFA_tree_item are:one.getParent().getR_child().get_first_pos()) {
					if(!one.get_follow_pos().contains(are)) {
						one.set_follow_pos(are);
					}
				}
			}else {
				//as we see in case ( 2 , 3 , 4 ) we call parent_follow_pos
				for(DFA_tree_item are: DFA_build.follow_pos(one.getParent())) {
					if(!one.get_follow_pos().contains(are)) {
						one.set_follow_pos(are);
					}
				}
				// the remain of case 4 
				if(one.getParent().getName()=='*'||one.getParent().getName()=='+') {
					for(DFA_tree_item are:one.get_first_pos()) {
						if(!one.get_follow_pos().contains(are)) {
							one.set_follow_pos(are);
						}
					}
				}
			}
		}
		//after all return top follow_pos , in case #5 will return empty set
		return one.get_follow_pos();		
	}
}