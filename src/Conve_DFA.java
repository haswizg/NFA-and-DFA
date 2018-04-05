import java.util.HashSet;
import java.util.LinkedList;

/*
 * this .........
 */

public class Conve_DFA {
	private static int state_counter=0;
	private static LinkedList<NFA_state> group_set=new LinkedList<NFA_state>();
	//return true if to LinkedList has same element ignore the order and the duplicated items 
	public static boolean listEqualsIgnoreOrder(LinkedList<NFA_item> list1, LinkedList<NFA_item> list2) { 
		return new HashSet<>(list1).equals(new HashSet<>(list2));
	}
	
	//do closuer for first item in nfa which is start
	public static void converge() {
		//its state
		group_set.addFirst(new NFA_state(closuer(NFA_Parser.getNFA_start().getFirst())));
		//its start state
		group_set.getFirst().setStart_state(true);
		
		System.out.println("Conver NFA to DFA printing ...");
		//for all states create do , will cont. till all state done 
		while(group_set.size()>state_counter) {
			analyz_state(group_set.get(state_counter++));
		}
		//print
		for(NFA_state one_state:group_set) {
			//check if it is end . and set it if true
			if(is_end(one_state.getHis_set())) {
				one_state.setEnd_state(true);
			}
			//print the state
			one_state.print_state();
		}
	}

	//if state his_set hava that item in end of nfa , then its end state
	public static boolean is_end(LinkedList<NFA_item> list) {
		for(NFA_item one:list) {
			if(NFA_Parser.getNFA_final().get(0)==one) {
				return true;
			}
		}
		//if the state set dont contin the end node , then false
		return false;
	}
	
	//state study , move(letter) from state set , and do cluser on that to get the state the letter has relation with 
	public static void analyz_state(NFA_state C_state) {
		//for all letter in RE
		for(char letter:start_parser.getAlphabet()) {
			//return move() , if more than zero then  this letter come out of this state
			LinkedList<NFA_item> moving=NFA_DFA_move(C_state,letter);
			if(moving.size()>0) {
				//do cluser on the return move() , if the state rerurn from is_state() already exist do nothing , if not exist then add it to the list
				NFA_state the_state=is_state(closuer(moving));
				if(!group_set.contains(the_state)) {
					group_set.addLast(the_state);
				}
				//do the arrow
				C_state.setLetter_arrows(new NFA_state_node(letter,the_state));
			}
		}
	}
	
	
	//check the set (income) if equal to set one of the state in list , then return that state , if not equal anything then crate new state 
	public static NFA_state is_state(LinkedList<NFA_item> income) {
		//as start
		NFA_state power_set=null;
		//for each
		for(NFA_state single_state:group_set) {
			//check , if equal then store in power_set
			if(Conve_DFA.listEqualsIgnoreOrder(single_state.getHis_set(),income)) {
				power_set=single_state;
			}
		}
		//if not null then it has been chenge with one state
		if(power_set!=null) {
			return power_set;
		}else {
			//if null mean no state has same set of elemente
			return new NFA_state(income);
		}
	}
	
	//from set eleamnte of state , return the move(as you study)
	public static LinkedList<NFA_item> NFA_DFA_move(NFA_state state , char letter) {
		//store the set to get easy in use
		LinkedList<NFA_item> list=state.getHis_set();
		//empty set , which will store the ruslt
		LinkedList<NFA_item> E_List=new LinkedList<NFA_item>();
		//for each elemnte in set of the state cheack if there way to the wanted letter , if yes add that way
		for(NFA_item one:list) {
			if(one.getNode_One().getNext()!=null&&one.getNode_One().getName()==letter) {
					E_List.addLast(one.getNode_One().getNext());
			}
			if(one.getNode_Two().getNext()!=null&&one.getNode_One().getName()==letter) {
					E_List.addLast(one.getNode_Two().getNext());
			}
		}
		//return the set , may be empty set
		return E_List;
	}
	
	
	//convarge the item to set and call clusuer() that carry list , this clusuer(item) call clusuer(set)
	public static LinkedList<NFA_item> closuer(NFA_item input) {
		LinkedList<NFA_item> tmp=new LinkedList<NFA_item>();
		tmp.add(input);
		return closuer(tmp);
	}
	
	//return set of element the can catch without pass throue letter
	public static LinkedList<NFA_item> closuer(LinkedList<NFA_item> input) {
		//set will return , empty set for now
		LinkedList<NFA_item> E_List=new LinkedList<NFA_item>();
		//start watch out from each item in (income) set
		for(NFA_item one:input) {
			//always the item start with be in return set
			E_List.addLast(one);
			//check first way by recurion take the clouser of the first way
			if(one.getNode_One().getNext()!=null&&one.getNode_One().getName()==' ') {
				for(NFA_item single:closuer(one.getNode_One().getNext())) {
					E_List.addLast(single);
				}
			}
			//check scond way , by recurion take the clouser of the scond way
			if(one.getNode_Two().getNext()!=null&&one.getNode_One().getName()==' ') {
				for(NFA_item single:closuer(one.getNode_Two().getNext())) {
					E_List.addLast(single);
				}
			}
		}
		//return what we got
		return E_List;
	}
}
