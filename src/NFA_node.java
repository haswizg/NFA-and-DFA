/*
 * this node is a part of item 
 * which a node represent the arrow that comes out of item 
 * and arrow may have letter
 */
public class NFA_node {

	private char name;	//store the letter
	private NFA_item next;	//where it go
	private boolean printed=false;	//is this node has printed ?
	private boolean nextPrinted=false;	//is the next print() called before ?
	
	//create empty node
	public NFA_node() {
		this.setName(' ');
		this.setNext(null);
	}
	
	/*
	 * create node with node connected with empty item
	 * since the arrow point to empty item ( and it's the next for this node )
	 * the next item is final , so add it to final point
	 */
	public NFA_node(char letter) {
		this.setName(letter);
		this.setNext(new NFA_item());
		NFA_Parser.setNFA_final(this.getNext());
	}
	
	public void setName(char letter) {
		this.name=letter;
	}
	
	public char getName() {
		return this.name;
	}
	
	public void setNext(NFA_item next) {
		this.next=next;
	}

	public NFA_item getNext() {
		return this.next;
	}
	
	/*
	 * if next is not null then print the path
	 * and if the path in not epsilon value then print the value too
	 */
	public void printNFAnode(int wonerID) {
		if(printed==false) {
			this.printed=true;
			if(this.getNext()!=null) {
				System.out.print("from " +wonerID +" to " +this.getNext().getId());
				if(this.getName()!=' ') {
					System.out.println(" with " +this.getName());
				}else {
					System.out.println("");
				}
			}
		}
	}
	
	//if the next not null then call the next's print
	public void printNextItem() {
		if(nextPrinted==false) {
			this.nextPrinted=true;
			if(this.getNext()!=null) {
				this.getNext().printNFAitem();
			}
		}
	}
}
