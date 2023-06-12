import java.util.List;

public class BacktrackingAVL extends AVLTree {
    // For clarity only, this is the default ctor created implicitly.
    public BacktrackingAVL() {
        super();
    }

	//You are to implement the function Backtrack.
    public void Backtrack() {
        // You should remove the next two lines, after double-checking that the signature is valid!
        //IntegrityStatement.signature(); // Reminder!
        //throw new UnsupportedOperationException("You should implement this");
    	if(stack.isEmpty()) {
	    	return;
	    }
    	boolean toContinue = true;
    	
	    Object[] prev_action = (Object[])stack.removeFirst();
	    Node wanted_node1 = null;
	    Node wanted_node2 = null;
	    Node wanted_node3 = null;
	    
	    if(prev_action[0].equals("no rotation")) {
	    	wanted_node3 = (Node)prev_action[1];
	    	toContinue = false;
	    }
	    //System.out.println(prev_action[0]);
	    //System.out.println(prev_action[1]);
	   // System.out.println(((Node)prev_action[2]).value);
	    //System.out.println(((Node)prev_action[4]).value);
	    
	    
	    if(toContinue && prev_action[1].equals("rotate right")) {
	    	wanted_node1 = (Node)prev_action[2];
	    	//printTree();
	    	Node current_child1 = rotateRight(wanted_node1);
	    	reconnect(current_child1.parent, wanted_node1.value, current_child1);
	    	//printTree();
	    	if(prev_action[0].equals("double rotation")) {
	    		wanted_node2 = (Node)prev_action[3];
	    		Node current_child2 = rotateLeft(wanted_node2);
	    		reconnect(current_child2.parent, wanted_node2.value, current_child2);
	    	}
	    }else if(toContinue && prev_action[1].equals("rotate left")) {
	    	wanted_node1 = (Node)prev_action[2];
	    	Node current_child1 = rotateLeft(wanted_node1);
	    	reconnect(current_child1.parent, wanted_node1.value, current_child1);
	    	if(prev_action[0].equals("double rotation")) {
	    		wanted_node2 = (Node)prev_action[3];
	    		Node current_child2 = rotateRight(wanted_node2);
	    		reconnect(current_child2.parent, wanted_node2.value, current_child2);
	    	}
	    }
	    if(toContinue) {
	    	wanted_node3 = (Node)prev_action[4];
	    }   
    	if(wanted_node3.parent == null) {
    		root = null;
    		return;
    	}
    	
    	if(wanted_node3.parent.left != null && wanted_node3.parent.left.value == wanted_node3.value) {
    		wanted_node3.parent.left = null;
    	
    	}
    	if(wanted_node3.parent.right != null && wanted_node3.parent.right.value == wanted_node3.value) {
    		wanted_node3.parent.right = null;
    		
    	}
    	Node current_node = wanted_node3.parent;
    	current_node.updateHeight();
    	while(current_node.parent != null) {
    		current_node = current_node.parent;
    		current_node.updateHeight();
    	}
    }
    
    private void reconnect(Node parent, int value, Node child) {
    	if(parent == null) {
    		root = child;
    		return;
    	}
    	if(parent.left != null && parent.left.value == value) {
    		parent.left = child;
    		return;
    	}
    	if(parent.right != null && parent.right.value == value) {
    		parent.right = child;
    		return;
    	}
    }
    
    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> AVLTreeBacktrackingCounterExample() {
        // You should remove the next two lines, after double-checking that the signature is valid!
        IntegrityStatement.signature(); // Reminder!
        throw new UnsupportedOperationException("You should implement this");
    }
    
    public int Select(int index) {
        // You should remove the next two lines, after double-checking that the signature is valid!
        //IntegrityStatement.signature(); // Reminder!
        //throw new UnsupportedOperationException("You should implement this");
    	Node current_node = root;
    	while(current_node != null) {
    		if(current_node.size == 1) {
    			return current_node.value;
    		}
    		int current_rank =  1;
    		if(current_node.left != null) {
    			current_rank += current_node.left.size ;
    		}
        	if(current_rank == index) {
        		return current_node.value;
        	}else if(current_rank > index) {
        		current_node = current_node.left;
        	}else {
        		current_node = current_node.right;
        		index = index - current_rank;
        	}
    	}
    	return -1;
    }
    
    public int Rank(int value) {
        // You should remove the next two lines, after double-checking that the signature is valid!
        //IntegrityStatement.signature(); // Reminder!
        //throw new UnsupportedOperationException("You should implement this");
    	Node current_node = root;
    	int current_rank = 0;
    	while(current_node != null) {
    		if(current_node.value == value & current_node.left == null) {
    			return current_rank;
    		}
    		if(current_node.value == value) {
    			return current_rank + current_node.left.size;
    		}else if (current_node.value > value) {
    			current_node = current_node.left;
    		}else {
    			if(current_node.left != null) {
    				current_rank += current_node.left.size;
    			}
    			current_rank += 1;
    			current_node = current_node.right;
    		}
    	}
    	return 0;
    }
    
}
