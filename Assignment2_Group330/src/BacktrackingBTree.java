import java.util.List;





public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
	// For clarity only, this is the default ctor created implicitly.
	public BacktrackingBTree() {
		super();
	}

	public BacktrackingBTree(int order) {
		super(order);
	}

	//You are to implement the function Backtrack.
	public void Backtrack() {
		// You should remove the next two lines, after double-checking that the signature is valid! 
	    //throw new UnsupportedOperationException("You should implement this");
	    if(stack.isEmpty()) {
	    	return;
	    }
	    Object[] prev_action = (Object[])stack.removeFirst();
	    if(prev_action[0].equals("Insert")){
	    	Node<T> wanted_node = (Node<T>)prev_action[1];
	    	int wanted_index = (int)prev_action[2];
	    	int num_of_splits = (int)prev_action[3];
	    	wanted_node.removeKey(wanted_index);
	    	while(num_of_splits > 0) {
	    		Object[] merge_action = (Object[])stack.removeFirst();
	    		wanted_node = (Node<T>)merge_action[0];
	    		wanted_index = (int)prev_action[1];
	    		merge(wanted_node , wanted_index);
	    		num_of_splits = num_of_splits - 1;
	    	}
	    }
    }
	
	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample(){
		// You should remove the next two lines, after double-checking that the signature is valid!
	    IntegrityStatement.signature(); // Reminder!
	    throw new UnsupportedOperationException("You should implement this");
	}
	
	private void merge(Node<T> node, int index) {
		Node<T> left = node.getChild(index);
		Node<T> right = node.getChild(index + 1);
		Node<T> new_node = new Node<T>(node.parent, left.children.length); 
		int current_index = 0;
		for(int i = 0 ; i < left.numOfKeys ; i = i + 1) {
			new_node.keys[current_index] = left.getKey(i);
			new_node.children[current_index] = left.getChild(i);
			current_index = current_index + 1;
		}
		new_node.keys[current_index] = node.getKey(index);
		new_node.children[current_index] = left.getChild(current_index);
		current_index = current_index + 1;
		
		for(int i = 0 ; i < right.numOfKeys ; i = i + 1) {
			new_node.keys[current_index] = right.getKey(i);
			new_node.children[current_index] =right.getChild(i);
			current_index = current_index + 1;
		}
		new_node.children[current_index] = right.getChild(current_index);
		if(node.parent == null) {
			root = new_node;
		}else {
			if(node.numOfKeys > 1) {
				node.removeKey(index);
				node.removeChild(left);
				node.removeChild(right);
				node.addChild(new_node);
			}else {
				node.parent.removeChild(node);
				node.parent.addChild(new_node);
			}
		}
	}
}
