import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
	private Stack stack;
	private Stack redoStack;
	private BacktrackingBST.Node root = null;
	private int stack_size;

	// Do not change the constructor's signature
	public BacktrackingBST(Stack stack, Stack redoStack) {
		this.stack = stack;
		this.redoStack = redoStack;
		stack_size = 0;
	}

	public Node getRoot() {
		if (root == null) {
			throw new NoSuchElementException("empty tree has no root");
		}
		return root;
	}

	public Node search(int k) {
		/*
		 * in search we check go over the tree right or left according the the node key
		 * if curr_key>key we go left else right
		 */
		Node curr_node = root;
		while (curr_node != null) {
			if (curr_node.getKey() == k) {
				return curr_node;
			} else if (curr_node.getKey() < k) {
				curr_node = curr_node.right;
			} else {
				curr_node = curr_node.left;
			}
		}
		return null; // temporal return command to prevent compilation error
	}

	public void insert(Node node) {
		/*
		 * in insert we first find a node that doesn't have a child an connect him to
		 * this node and for backtracking we put in the stack an object array that in
		 * the O index we put insert to help us know that the last step where insert and
		 * the node it self to bring it back to his place
		 */
		if (node == null) {
			throw new IllegalArgumentException();
		}
		redoStack.clear();
		Node prev_node = null;
		Node curr_node = root;
		while (curr_node != null) {
			prev_node = curr_node;
			if (curr_node.getKey() > node.getKey()) {
				curr_node = curr_node.left;
			} else {
				curr_node = curr_node.right;
			}
		}
		node.parent = prev_node;
		if (prev_node == null) {
			root = node;
		} else if (node.getKey() > prev_node.getKey()) {
			prev_node.right = node;
		} else {
			prev_node.left = node;
		}
		Object[] insert_array = new Object[2];
		insert_array[0] = new String("Insert");
		insert_array[1] = node;
		stack.push(insert_array);
		stack_size = stack_size + 1;
	}

	public void delete(Node node) {
		/*
		 * in delete we deal with three different cases for each case we put in the
		 * stack different arrays in case 1 we just need the node it self and instead
		 *  we put null case 2 the node should have a left child so we connect the left
		 *  child to the node parent and putting the node in stack for backtracking
		 *  case 3 the node have 2 children so we put the successor instead and saving the successor position in new node 
		 *  to help us return the tree to its original condition after backtracking
		 */
		Node node_parent = node.parent;
		Node new_child = null;
		redoStack.clear();
		if (node.right == null & node.left == null) {
			Object[] delete_array1 = new Object[3];
			delete_array1[0] = new String("Delete");
			delete_array1[1] = new String("Case 1");
			delete_array1[2] = node;
			stack.push(delete_array1);
			stack_size = stack_size + 1;
			new_child = null;
		}
		if (node.right == null & node.left != null) {
			Object[] delete_array2 = new Object[3];
			delete_array2[0] = new String("Delete");
			delete_array2[1] = new String("Case 2");
			delete_array2[2] = node;
			stack.push(delete_array2);
			stack_size = stack_size + 1;
			node.left.parent = node_parent;
			new_child = node.left;

		} else if (node.right != null & node.left == null) {
			Object[] delete_array2 = new Object[3];
			delete_array2[0] = new String("Delete");
			delete_array2[1] = new String("Case 2");
			delete_array2[2] = node;
			stack.push(delete_array2);
			stack_size = stack_size + 1;
			node.right.parent = node_parent;
			new_child = node.right;
		}
		if (node.right != null & node.left != null) {
			Node node_successor = successor(node);
			if (node_successor.getKey() == node.right.getKey()) {
				Object[] delete_array31 = new Object[3];
				delete_array31[0] = new String("Delete");
				delete_array31[1] = new String("Case 3.1");
				delete_array31[2] = node;
				stack.push(delete_array31);
				stack_size = stack_size + 1;
				node.left.parent = node_successor;
				node_successor.left = node.left;
			} else {

				Object[] delete_array32 = new Object[5];
				delete_array32[0] = new String("Delete");
				delete_array32[1] = new String("Case 3.2");
				delete_array32[2] = node;
				Node prev_successor = new Node(node_successor.getKey(), null);
				prev_successor.right = node_successor.right;
				prev_successor.left = node_successor.left;
				prev_successor.parent = node_successor.parent;
				delete_array32[3] = prev_successor;
				delete_array32[4] = node_successor;
				stack.push(delete_array32);
				stack_size = stack_size + 1;
				node.left.parent = node_successor;
				node.right.parent = node_successor;
				if (node_successor.right != null) {
					node_successor.right.parent = node_successor.parent;
					node_successor.parent.left = node_successor.right;
				} else {
					node_successor.parent.left = null;
				}
				node_successor.left = node.left;
				node_successor.right = node.right;
			}
			node_successor.parent = node_parent;
			new_child = node_successor;
		}
		if (node_parent == null) {
			root = new_child;
		} else {
			if (node.getKey() < node_parent.getKey()) {
				node_parent.left = new_child;
			} else {
				node_parent.right = new_child;
			}
		}
	}

	public Node minimum() {
		/*in minimum we keep going to the left until we find a node that has no left child and return it*/
		if (root == null) {
			throw new NoSuchElementException("empty tree has no root");
		}
		Node curr_node = root;
		while (curr_node.left != null) {
			curr_node = curr_node.left;
		}
		return curr_node; // temporal return command to prevent compilation error
	}

	public Node maximum() {
		/*in minimum we keep going to the right until we find a node that has no right child and return it*/
		if (root == null) {
			throw new NoSuchElementException("empty tree has no root");
		}
		Node curr_node = root;
		while (curr_node.right != null) {
			curr_node = curr_node.right;
		}
		return curr_node; // temporal return command to prevent compilation error
	}

	public Node successor(Node node) {
		/*in successor we first check if the node have a right child and finding the minimum
		 *  in this subtree of the right child if he has no right child we go up in the tree
		 *   and returning the parent node if exist*/
		if (search(node.getKey()) == null) {
			throw new NoSuchElementException();
		}
		Node curr_node = null;

		if (node.right != null) {
			curr_node = node.right;
			while (curr_node.left != null) {
				curr_node = curr_node.left;
			}
			return curr_node;
		}
		curr_node = node.parent;
		if (node.parent == null) {
			throw new NoSuchElementException();
		}
		Node temp = node;
		while (curr_node != null & temp == curr_node.right) {
			temp = curr_node;
			curr_node = curr_node.parent;
		}

		return curr_node; // temporal return command to prevent compilation error
	}

	public Node predecessor(Node node) {
		/*in predecessor we first check if the node have a left child and finding the maximum
		 *  in this subtree of the left child */
		if (search(node.getKey()) == null) {
			throw new NoSuchElementException();
		}
		Node curr_node = null;
		if (node.left != null) {
			curr_node = node.left;
			while (curr_node.right != null) {
				curr_node = curr_node.right;
			}
			return curr_node;
		}
		curr_node = node.parent;
		if (node.parent == null) {
			throw new NoSuchElementException();
		}
		Node temp = node;
		while (curr_node != null & temp == curr_node.left) {
			temp = curr_node;
			curr_node = curr_node.parent;
		}
		return curr_node;// temporal return command to prevent compilation error
	}

	@Override
	public void backtrack() {
		/*in backtrack we use the arrays that we insert in the stack an checking which case we want to deal with
		 * in the 0+1 index of the array we have a strings to help us know which step we want to backtrack*/
		if (stack.isEmpty()) {
			return;
		}
		Object[] prev_action = (Object[]) stack.pop();
		redoStack.push(prev_action);
		stack_size = stack_size - 1;
		if (prev_action[0].equals("Insert")) {
			Node wanted_node = (Node) prev_action[1];
			if (wanted_node.parent == null) {
				root = null;
				return;
			} else {
				if (wanted_node.parent.getKey() > wanted_node.getKey()) {
					wanted_node.parent.left = null;
				} else {
					wanted_node.parent.right = null;
				}
			}
		}

		else if (prev_action[0].equals("Delete")) {
			Node wanted_node = (Node) prev_action[2];
			if (prev_action[1].equals("Case 1")) {
				if (wanted_node.parent == null) {
					root = wanted_node;

				} else {
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = wanted_node;
					} else {
						wanted_node.parent.right = wanted_node;
					}
				}
			}

			else if (prev_action[1].equals("Case 2")) {
				if (wanted_node.parent == null) {
					root = wanted_node;
				} else {
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = wanted_node;
					} else {
						wanted_node.parent.right = wanted_node;
					}
				}

				if (wanted_node.right != null) {
					wanted_node.right.parent = wanted_node;
				} else {
					wanted_node.left.parent = wanted_node;
				}
			}

			else if (prev_action[1].equals("Case 3.1")) {
				if (wanted_node.parent == null) {
					root = wanted_node;
				} else {
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = wanted_node;
					} else {
						wanted_node.parent.right = wanted_node;
					}
				}
				wanted_node.right.left = null;
				wanted_node.right.parent = wanted_node;
				wanted_node.left.parent = wanted_node;

			} else if (prev_action[1].equals("Case 3.2")) {

				Node prev_successor = (Node) prev_action[3];
				Node node_successor = (Node) prev_action[4];
				if (wanted_node.parent == null) {
					root = wanted_node;
				} else {
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = wanted_node;
					} else {
						wanted_node.parent.right = wanted_node;
					}
				}

				wanted_node.right.parent = wanted_node;
				wanted_node.left.parent = wanted_node;
				// -----------------------------------------------
				Node temp = new Node(prev_successor.getKey(), null);
				temp.parent = node_successor.parent;
				temp.right = node_successor.right;
				temp.left = node_successor.left;
				// ------------------------------------------------
				node_successor.parent = prev_successor.parent;
				node_successor.right = prev_successor.right;
				node_successor.left = prev_successor.left;
				prev_successor.parent.left = node_successor;
				if (prev_successor.right != null) {
					prev_successor.right.parent = node_successor;
					;
				}

				prev_successor.parent = temp.parent;
				prev_successor.right = temp.right;
				prev_successor.left = temp.left;
			}

		}

	}

	@Override
	public void retrack() {
		/*in retrack we use the arrays that we insert in the redostack an checking which case we want to deal with
		 * in the 0+1 index of the array we have a strings to help us know which step we want to retrack*/
		if (redoStack.isEmpty()) {
			return;
		}
		Object[] next_action = (Object[]) redoStack.pop();
		stack_size = stack_size + 1;
		stack.push(next_action);
		if (next_action[0].equals("Insert")) {
			Node wanted_node = (Node) next_action[1];
			if (wanted_node.parent == null) {
				root = wanted_node;
			} else {
				if (wanted_node.parent.getKey() > wanted_node.getKey()) {
					wanted_node.parent.left = wanted_node;
				} else {
					wanted_node.parent.right = wanted_node;
				}
			}
		} else if (next_action[0].equals("Delete")) {
			Node wanted_node = (Node) next_action[2];
			if (next_action[1].equals("Case 1")) {
				if (wanted_node.parent == null) {
					root = null;
				} else {
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = null;
					} else {
						wanted_node.parent.right = null;
					}
				}
			}

			else if (next_action[1].equals("Case 2")) {
				if (wanted_node.right != null) {
					wanted_node.right.parent = wanted_node.parent;
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = wanted_node.right;
					} else {
						wanted_node.parent.right = wanted_node.right;
					}
				} else {
					wanted_node.left.parent = wanted_node.parent;
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = wanted_node.left;
					} else {
						wanted_node.parent.right = wanted_node.left;
					}
				}

			} else if (next_action[1].equals("Case 3.1")) {
				wanted_node.right.parent = wanted_node.parent;
				wanted_node.right.left = wanted_node.left;
				wanted_node.left.parent = wanted_node.right;

				if (wanted_node.parent == null) {
					root = wanted_node.right;
				} else {
					if (wanted_node.parent.getKey() > wanted_node.getKey()) {
						wanted_node.parent.left = wanted_node.right;
					} else {
						wanted_node.parent.right = wanted_node.right;
					}
				}
			} else if (next_action[1].equals("Case 3.2")) {
				Node prev_successor = (Node) next_action[3];
				Node node_successor = (Node) next_action[4];
				Node node_parent = wanted_node.parent;
				Node new_child = null;
				prev_successor.right = node_successor.right;
				prev_successor.left = node_successor.left;
				prev_successor.parent = node_successor.parent;

				wanted_node.left.parent = node_successor;
				wanted_node.right.parent = node_successor;
				if (node_successor.right != null) {
					node_successor.right.parent = node_successor.parent;
					node_successor.parent.left = node_successor.right;
				} else {
					node_successor.parent.left = null;
				}
				node_successor.left = wanted_node.left;
				node_successor.right = wanted_node.right;
				node_successor.parent = node_parent;
				new_child = node_successor;
				if (node_parent == null) {
					root = new_child;
				} else {
					if (wanted_node.getKey() < node_parent.getKey()) {
						node_parent.left = new_child;
					} else {
						node_parent.right = new_child;
					}
				}
			}
		}

	}

	// TODO: implement your code here

	public void printPreOrder() {
		if (root == null) {
			return;
		}
		int prev_stack_size = stack_size;
		stack.push(root);
		stack_size = stack_size + 1;
		while (stack_size > prev_stack_size) {
			Node temp = (Node) stack.pop();
			stack_size = stack_size - 1;
			System.out.print(temp.getKey() + " ");
			if (temp.right != null) {
				stack.push(temp.right);
				stack_size = stack_size + 1;
			}
			if (temp.left != null) {
				stack.push(temp.left);
				stack_size = stack_size + 1;
			}
		}

	}

	@Override
	public void print() {
		printPreOrder();
	}


	public static class Node {
		// These fields are public for grading purposes. By coding conventions and best
		// practice they should be private.
		public BacktrackingBST.Node left;
		public BacktrackingBST.Node right;

		private BacktrackingBST.Node parent;
		private int key;
		private Object value;

		public Node(int key, Object value) {
			this.key = key;
			this.value = value;
		}

		public int getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

	}



}
