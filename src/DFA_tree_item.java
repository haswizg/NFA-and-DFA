import java.util.LinkedList;

public class DFA_tree_item {
	
	private static int id_gen=0;	//to generate unique id for each item 
	private int item_Id=0; 	//the unique id .. ?? for leafs only ??______________________________________________??
	private DFA_tree_item Parent;	//parent
	private DFA_tree_item R_child;	//left child
	private DFA_tree_item L_child;	//right child
	private char name;	//store the item face
	private LinkedList<DFA_tree_item> first_pos=new LinkedList<DFA_tree_item>();	//fisrt_pos
	private LinkedList<DFA_tree_item> follow_pos=new LinkedList<DFA_tree_item>();	//follow_pos
	
	// create tree node with face
	public DFA_tree_item(char letter) {
		this.name=letter;
		if(letter!='*'||letter!='+'||letter!='.'||letter!='|') {
			this.item_Id=++id_gen;
		}  //  ??_______________________________________________________________??
		this.setParent(null);
		this.setR_child(null);
		this.setL_child(null);
		DFA_build.setDFA_tree_start(this);
	}
	
	public LinkedList<DFA_tree_item> get_first_pos() {
		return first_pos;
	}
	
	public void set_first_pos(LinkedList<DFA_tree_item> list) {
		this.first_pos=list;
	}
	
	public void set_first_pos(DFA_tree_item item) {	
		this.first_pos.addLast(item);
	}
	
	public LinkedList<DFA_tree_item> get_follow_pos() {
		return follow_pos;
	}
	
	public void set_follow_pos(LinkedList<DFA_tree_item> list) {
		this.follow_pos=list;
	}
	
	public void set_follow_pos(DFA_tree_item item) {
		this.follow_pos.addLast(item);
	}
	
	public void setParent(DFA_tree_item parent) {
		Parent = parent;
	}

	public DFA_tree_item getParent() {
		return Parent;
	}
	
	public void setL_child(DFA_tree_item l_child) {
		L_child = l_child;
	}

	public DFA_tree_item getL_child() {
		return L_child;
	}

	public void setR_child(DFA_tree_item r_child) {
		R_child = r_child;
	}

	public DFA_tree_item getR_child() {
		return R_child;
	}
	
	public char getName() {
		return name;
	}
	
	public int getId() {
		return this.item_Id;
	}

	public void print_tree_node() {
		//print id
		System.out.println("\n---id:"+this.getId()+"---");
		//print first_pos
		System.out.print("first_pos: ");
		for(DFA_tree_item one:this.get_first_pos()) {
			System.out.print(one.getId()+",");
		}
		//if no left child then this is leaf . so print the follow_pos
		if(this.L_child==null) {
			System.out.print("\nfollow_pos: ");
			for(DFA_tree_item two:this.get_follow_pos()) {
				System.out.print(two.getId()+",");
			}
		}else {
			//if it is not leaf then call print function for his children
			if(this.L_child!=null) {
				this.L_child.print_tree_node();
			}
			if(this.R_child!=null) {
				this.R_child.print_tree_node();
			}
		}
	}
}
