//import java.util.ArrayList;
//import java.util.List;
//
//
//
//
//
//public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
//	// For clarity only, this is the default ctor created implicitly.
//	public BacktrackingBTree() {
//		super();
//	}
//
//	public BacktrackingBTree(int order) {
//		super(order);
//	}
//
//	//You are to implement the function Backtrack.
//	public void Backtrack() {
//		// You should remove the next two lines, after double-checking that the signature is valid!
//	    //IntegrityStatement.signature(); // Reminder!
//	    //throw new UnsupportedOperationException("You should implement this");
//	    if(stack.isEmpty()) {
//	    	return;
//	    }
//	    Object[] prev_action = (Object[])stack.removeFirst();
//	    if(prev_action[0].equals("insert")){
//	    	T wanted_value = (T)prev_action[1];
//	    	int num_of_splits = (int)prev_action[2];
//	    	Node<T> wanted_node = getNode(wanted_value);
//	    	wanted_node.removeKey(wanted_value);
//	    	while(num_of_splits > 0) {
//	    		Object[] merge_action = (Object[])stack.removeFirst();
//	    		T wanted_median_value = (T)merge_action[0];
//	    		int wanted_index = (int)merge_action[1];
//	    		Node<T> wanted_node2 = getNode(wanted_median_value);
//	    		merge(wanted_node2 , wanted_index);
//	    		num_of_splits = num_of_splits - 1;
//	    	}
//	    }
//    }
//	
//	//Change the list returned to a list of integers answering the requirements
//	public static List<Integer> BTreeBacktrackingCounterExample(){
//		// You should remove the next two lines, after double-checking that the signature is valid!
//	    //IntegrityStatement.signature(); // Reminder!
//	    //throw new UnsupportedOperationException("You should implement this");
//	    ArrayList<Integer> new_list = new ArrayList();
//	    new_list.add(1);
//	    new_list.add(2);
//	    new_list.add(3);
//	    new_list.add(4);
//	    new_list.add(5);
//	    new_list.add(6);
//	    return new_list;
//	}
//	
//	private void merge(Node<T> node, int index) {
//		Node<T> left = node.getChild(index);
//		Node<T> right = node.getChild(index + 1);
//		Node<T> new_node = new Node<T>(null, left.children.length); 
//		int current_key_index = 0;
//		int current_child_index = 0;
//		for(int i = 0 ; i < left.numOfKeys ; i = i + 1) {
//			new_node.keys[current_key_index] = (T)left.getKey(i);
//			++new_node.numOfKeys;
//			current_key_index = current_key_index + 1;
//		}
//		for(int i = 0 ; i < left.numOfChildren ; i = i + 1) {
//			new_node.children[current_child_index] = (Node<T>)left.getChild(i);
//			++new_node.numOfChildren;
//			current_child_index = current_child_index + 1;
//		}
//		//------------------------------------------------------
//		new_node.keys[current_key_index] = (T)node.getKey(index);
//		++new_node.numOfKeys;
//		current_key_index = current_key_index + 1;
//		//------------------------------------------------------
//		
//		for(int i = 0 ; i < right.numOfKeys ; i = i + 1) {
//			new_node.keys[current_key_index] = (T)right.getKey(i);
//			++new_node.numOfKeys;
//			current_key_index = current_key_index + 1;
//		}
//		for(int i = 0 ; i < right.numOfChildren ; i = i + 1) {
//			new_node.children[current_child_index] = (Node<T>)right.getChild(i);
//			++new_node.numOfChildren;
//			current_child_index = current_child_index + 1;
//		}
//		
//		if(node.parent == null & node.numOfKeys == 1) {
//			root = new_node;
//			return;
//		}
//		if (node.numOfKeys > 1) {
//			node.removeKey(index);
//			node.removeChild(left);
//			node.removeChild(right);
//			node.addChild(new_node);
//			new_node.parent = node;
//		}
//	}
//}
import java.util.ArrayList;
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
	    //IntegrityStatement.signature(); // Reminder!
	    //throw new UnsupportedOperationException("You should implement this");
	    if(stack.isEmpty()) { 
	    	return;
	    }
	    Object[] prev_action = (Object[])stack.removeFirst();
	    if(prev_action[0].equals("insert")){
	    	Node<T> wanted_node = (Node<T>)prev_action[1];
	    	T wanted_value = (T)prev_action[2];
	    	if(wanted_node==root&root.numOfKeys==1) {
	    		wanted_node.removeKey(wanted_value);
	    		root=null;
	    	}
	    	else {
	    	wanted_node.removeKey(wanted_value);
	    	}
	    	int num_of_splits =(int)prev_action[3];
	    	while(num_of_splits > 0) {
	    		Object[] merge_action = (Object[])stack.removeFirst();
	    		
	    		T wanted_median_value = (T)merge_action[0];
	    		int wanted_index = (int)merge_action[1];
	    		Node<T> wanted_node2 = (Node<T>)merge_action[2];
	    		ReConnect(wanted_node2,wanted_median_value,wanted_index);
	    		num_of_splits = num_of_splits - 1;
	    	}
	    }
    }
	
	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample(){
		// You should remove the next two lines, after double-checking that the signature is valid!
	    //IntegrityStatement.signature(); // Reminder!
	    //throw new UnsupportedOperationException("You should implement this");
	    ArrayList<Integer> new_list = new ArrayList();
	    new_list.add(1);
	    new_list.add(2);
	    new_list.add(3);
	    new_list.add(4);
	    new_list.add(5);
	    new_list.add(6);
	    return new_list;
	}
	
//	private void merge(Node<T> node, int index) {
//		Node<T> left = node.getChild(index);
//		Node<T> right = node.getChild(index + 1);
//		Node<T> new_node = new Node<T>(null, left.children.length); 
//		int current_key_index = 0;
//		int current_child_index = 0;
//		for(int i = 0 ; i < left.numOfKeys ; i = i + 1) {
//			new_node.keys[current_key_index] = (T)left.getKey(i);
//			++new_node.numOfKeys;
//			current_key_index = current_key_index + 1;
//		}
//		for(int i = 0 ; i < left.numOfChildren ; i = i + 1) {
//			new_node.children[current_child_index] = (Node<T>)left.getChild(i);
//			++new_node.numOfChildren;
//			current_child_index = current_child_index + 1;
//		}
//		//------------------------------------------------------
//		new_node.keys[current_key_index] = (T)node.getKey(index);
//		++new_node.numOfKeys;
//		current_key_index = current_key_index + 1;
//		//------------------------------------------------------
//		
//		for(int i = 0 ; i < right.numOfKeys ; i = i + 1) {
//			new_node.keys[current_key_index] = (T)right.getKey(i);
//			++new_node.numOfKeys;
//			current_key_index = current_key_index + 1;
//		}
//		for(int i = 0 ; i < right.numOfChildren ; i = i + 1) {
//			new_node.children[current_child_index] = (Node<T>)right.getChild(i);
//			++new_node.numOfChildren;
//			current_child_index = current_child_index + 1;
//		}
//		
//		if(node.parent == null & node.numOfKeys == 1) {
//			root = new_node;
//			return;
//		}
//		if (node.numOfKeys > 1) {
//			node.removeKey(index);
//			node.removeChild(left);
//			node.removeChild(right);
//			node.addChild(new_node);
//			new_node.parent = node;
//		}
//	}
	private void ReConnect(Node<T> node,T value,int pos) {
		if(node.parent == root & node.parent.numOfKeys == 1) {
			root = node;
		}
		else { 
	    Node<T> left=node.parent.getChild(pos);
	    Node<T> right=node.parent.getChild(pos+1);
		node.parent.removeKey(value);
		node.parent.removeChild(left);
		node.parent.removeChild(right);
		node.parent.addChild(node);
		}
		
	}
}

