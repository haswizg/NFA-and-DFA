/*
 * item represent NFA state , ofcourse every state has unique id
 * in NFA the maximum arrow go from state is two , cause of this the item has two node  
 */
public class NFA_item {
	
	private static int id_gen=0;	//to generate unique id for each item  
	private int item_Id;	//the unique id for this item
	private NFA_node node_One;	//1st arrow
	private NFA_node node_Two;	//2ed arrow

	/*
	 * create empty item with two empty node which mean on arrow
	 * it use when node with letter create and point to empty item 
	 * so, maybe final (if it is , it's done in where the object create)
	 * and definitely not start 
	 */
	public NFA_item() {
		this.item_Id=++id_gen;
		this.node_One=new NFA_node();
		this.node_Two=new NFA_node();
	}
	
	/*
	 * create item , in one node there is letter , and other is empty node
	 * the node has letter represent arrow with letter
	 * after create the item add it to start points list , cause it's start obviously
	 */
	public NFA_item(char letter) {
		this.item_Id=++id_gen;
		this.node_One=new NFA_node(letter);
		this.node_Two=new NFA_node();
		NFA_Parser.setNFA_start(this);
	}
	
	public void setNode_One_Next(NFA_item next_Item) {
		this.getNode_One().setNext(next_Item);
	}

	public NFA_node getNode_One() {
		return node_One;
	}	
	
	public void setNode_Two_Next(NFA_item next_Item) {
		this.getNode_Two().setNext(next_Item);
	}
		
	public NFA_node getNode_Two() {
		return node_Two;
	}
	
	public int getId() {
		return this.item_Id;
	}
	
	/*
	 * print node one and two
	 * and then call print for next of node one and two
	 */
	public void printNFAitem() {
		this.getNode_One().printNFAnode(this.item_Id);
		this.getNode_Two().printNFAnode(this.item_Id);
		this.getNode_One().printNextItem();
		this.getNode_Two().printNextItem();
	}
}
