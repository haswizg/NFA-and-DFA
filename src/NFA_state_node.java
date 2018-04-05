

/*
 * this node is a part of DFA state 
 * which a node represent the arrow that comes out of state 
 * and arrow may have letter
 */

public class NFA_state_node {
	private char name;	//store the letter
	private NFA_state next;	//where it go
	
	public NFA_state_node(char letter,NFA_state state) {
		this.name=letter;
		this.next=state;
	}
	
	public void setName(char letter) {
		this.name=letter;
	}
	
	public char getName() {
		return this.name;
	}
	
	public void setNext(NFA_state next) {
		this.next=next;
	}

	public NFA_state getNext() {
		return this.next;
	}
}
