import java.util.LinkedList;

public class DFA_state {
	private static int gen_id=0;		//to generate unique id for each state 
	private int id;		//the unique id
	private boolean start_state=false;		//initially not start state
	private boolean end_state=false;		//initially not final state
	private LinkedList<DFA_state_node> letter_arrows=new LinkedList<DFA_state_node>();	//the arrows . letter and state destination 
	private LinkedList<DFA_tree_item> his_set=new LinkedList<DFA_tree_item>();	//the set of state
	
	public DFA_state(LinkedList<DFA_tree_item> his_set) {
		this.id=++gen_id;
		this.his_set = his_set;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStart_state() {
		return start_state;
	}

	public void setStart_state(boolean start_state) {
		this.start_state = start_state;
	}

	public boolean isEnd_state() {
		return end_state;
	}

	public void setEnd_state(boolean end_state) {
		this.end_state = end_state;
	}

	public LinkedList<DFA_state_node> getLetter_arrows() {
		return letter_arrows;
	}

	public void setLetter_arrows(DFA_state_node letter_arrows) {
		this.letter_arrows.addLast(letter_arrows);
	}

	public LinkedList<DFA_tree_item> getHis_set() {
		return his_set;
	}

	public void setHis_set(LinkedList<DFA_tree_item> his_set) {
		this.his_set = his_set;
	}

	public void print_state() {
		//id
		System.out.println("~~~"+id+"~~~");
		//if start state tell me
		if(start_state) {
			System.out.println("start state");
		}
		//if end state tell me
		if(end_state) {
			System.out.println("end state");
		}
		//print his set
		System.out.print("set :");
		for(DFA_tree_item one:this.his_set) {
			System.out.print(one.getId()+" ");
		}
		//print his arrows
		System.out.println("\narrows");
		for(DFA_state_node one:this.letter_arrows) {
			one.print_node();
		}
	}
}
