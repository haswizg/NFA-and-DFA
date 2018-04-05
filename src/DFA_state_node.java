import java.util.LinkedList;
/*
 * node represent Arrow , of course arrow has letter and a allocated destination
 */

public class DFA_state_node {
	private char letter;
	private DFA_state his_state=null;

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public DFA_state getHis_state() {
		return his_state;
	}
	
	public void setHis_state(DFA_state his_state) {
		this.his_state = his_state;
	}
	
	//if destination is a state then just pass it
	public DFA_state_node(char name , DFA_state state) {
		this.setLetter(name);
		this.setHis_state(state);
	}
	
	//if destination is not a state ,then create a state
	public DFA_state_node(char name , LinkedList<DFA_tree_item> his_set ) {
		this.setLetter(name);
		DFA_build.set_states_list(new DFA_state(his_set));
		DFA_state tmp_state=DFA_build.get_states_list().getLast();
		this.setHis_state(tmp_state);
	}

	//print arrow's info
	public void print_node() {
		System.out.println(this.getLetter()+" to state "+this.getHis_state().getId());
	}
	
}
