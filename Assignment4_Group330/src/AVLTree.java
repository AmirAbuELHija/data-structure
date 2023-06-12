import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class AVLTree implements Iterable<Integer> {
    // You may edit the following nested class:
    protected class Node {
    	public Node left = null;
    	public Node right = null;
    	public Node parent = null;
    	public int height = 0;
    	public int value;
    	
    	public int size = 1;
    	public Node last_inserted = null;

    	public Node(int val) {
            this.value = val;
        }

        public void updateHeight() {
            int leftHeight = (left == null) ? -1 : left.height;
            int rightHeight = (right == null) ? -1 : right.height;

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        public int getBalanceFactor() {
            int leftHeight = (left == null) ? -1 : left.height;
            int rightHeight = (right == null) ? -1 : right.height;

            return leftHeight - rightHeight;
        }
    }
    
    protected Node root;
    
    protected ArrayDeque<Object[]> stack;
    
    //You may add fields here.
    
    public AVLTree() {
    	this.root = null;
    	stack = new ArrayDeque<Object[]>();
    }
    
    /*
     * IMPORTANT: You may add code to both "insert" and "insertNode" functions.
     */
	public void insert(int value) {
    	root = insertNode(root, value);
    	
    	if(stack.isEmpty()) {
    		Object[] current_action = new Object[2];
        	current_action[0] = new String("no rotation");
        	current_action[1] = root.last_inserted;
        	stack.addFirst(current_action);
	    	return;
	    }
    	Object[] prev_action = (Object[])stack.removeFirst();
    	if(!prev_action[0].equals("no rotation") && ((Node)prev_action[4]).value == value) {
    		stack.addFirst(prev_action);
    		return;
    	}
    	stack.addFirst(prev_action);
    	Object[] current_action = new Object[2];
    	current_action[0] = new String("no rotation");
    	current_action[1] = root.last_inserted;
    	stack.addFirst(current_action);
    }
	
	protected Node insertNode(Node node, int value) {
	    // Perform regular BST insertion
		
		
        if (node == null) {
        	Node insertedNode = new Node(value);
        	insertedNode.last_inserted = insertedNode;
            return insertedNode;
        }

        if (value < node.value) {
            node.left  = insertNode(node.left, value);
            node.left.parent = node;
            node.last_inserted = node.left.last_inserted;
            ++node.size;
        }
        else {
            node.right = insertNode(node.right, value);
            node.right.parent = node;
            node.last_inserted = node.right.last_inserted;
            ++node.size;
        }
            
        node.updateHeight();

        /* 
         * Check For Imbalance, and fix according to the AVL-Tree Definition
         * If (balance > 1) -> Left Cases, (balance < -1) -> Right cases
         */
        
        
        int balance = node.getBalanceFactor();
        
        if (balance > 1) {
        	boolean double_rotation = false;
        	Object[] current_action = new Object[5];
        	current_action[1] = new String("rotate left");
        	current_action[4] = node.last_inserted;
        	
            if (value > node.left.value) {
                node.left = rotateLeft(node.left);
                //System.out.println("Left");
                double_rotation = true;
                current_action[3] = node.left;
                //System.out.println(node.left.size);
            }
            
            node = rotateRight(node);
            //System.out.println("Right");
            //System.out.println("---------------------");
            current_action[2] = node;
            if(double_rotation) {
            	current_action[0] = new String("double rotation");
            }else {
            	current_action[0] = new String("single rotation");
            	current_action[3] = null;
            }
            stack.addFirst(current_action);
        } else if (balance < -1) {
        	boolean double_rotation = false;
        	Object[] current_action = new Object[5];
        	current_action[1] = new String("rotate right");
        	current_action[4] = node.last_inserted;
            if (value < node.right.value) {
                node.right = rotateRight(node.right);
                double_rotation = true;
                current_action[3] = node.right;
                //System.out.println("Right");
            }
      
            node = rotateLeft(node);
            current_action[2] = node;
            if(double_rotation) {
            	current_action[0] = new String("double rotation");
            }else {
            	current_action[0] = new String("single rotation");
            	current_action[3] = null;
            }
            stack.addFirst(current_action);
            //System.out.println("Left");
            //System.out.println("---------------------");
        }

        return node;
    }
    
	// You may add additional code to the next two functions.
    protected Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        int prev_size_of_x = x.size;
        int size_of_T2 = 0;
        int prev_size_of_y = y.size;
        
        // Perform rotation
        x.right = y;
        y.left = T2;
        
        //Update parents
        if (T2 != null) {
        	T2.parent = y;
        	
        	size_of_T2 = T2.size;
        }

        x.parent = y.parent;
        y.parent = x;
        
        y.updateHeight();
        x.updateHeight();
        
        y.size = prev_size_of_y - prev_size_of_x + size_of_T2;
        x.size = prev_size_of_y;

        // Return new root
        return x;
    }

    protected Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        
        int prev_size_of_x = x.size;
        int size_of_T2 = 0;
        int prev_size_of_y = y.size;
        
        // Perform rotation
        y.left = x;
        x.right = T2;
        
        //Update parents
        if (T2 != null) {
        	T2.parent = x;
        	
        	size_of_T2 = T2.size;
        }
        
        y.parent = x.parent;
        x.parent = y;
        
        x.updateHeight();
        y.updateHeight();
        
        x.size = prev_size_of_x - prev_size_of_y + size_of_T2;
        y.size = prev_size_of_x;

        // Return new root
        return y;
    }
    
    public void printTree() {
    	TreePrinter.print(this.root);
    }

    /***
     * A Printer for the AVL-Tree. Helper Class for the method printTree().
     * Not relevant to the assignment.
     */
    private static class TreePrinter {
        private static void print(Node root) {
            if(root == null) {
                System.out.println("(XXXXXX)");
            } else {    
                final int height = root.height + 1;
                final int halfValueWidth = 4;
                int elements = 1;
                
                List<Node> currentLevel = new ArrayList<Node>(1);
                List<Node> nextLevel    = new ArrayList<Node>(2);
                currentLevel.add(root);
                
                // Iterating through the tree by level
                for(int i = 0; i < height; i++) {
                    String textBuffer = createSpaceBuffer(halfValueWidth * ((int)Math.pow(2, height-1-i) - 1));
        
                    // Print tree node elements
                    for(Node n : currentLevel) {
                        System.out.print(textBuffer);
        
                        if(n == null) {
                            System.out.print("        ");
                            nextLevel.add(null);
                            nextLevel.add(null);
                        } else {
                            System.out.printf("(%6d)", n.value);
                            nextLevel.add(n.left);
                            nextLevel.add(n.right);
                        }
                        
                        System.out.print(textBuffer);
                    }
        
                    System.out.println();
                    
                    if(i < height - 1) {
                        printNodeConnectors(currentLevel, textBuffer);
                    }
        
                    elements *= 2;
                    currentLevel = nextLevel;
                    nextLevel = new ArrayList<Node>(elements);
                }
            }
        }
        
        private static String createSpaceBuffer(int size) {
            char[] buff = new char[size];
            Arrays.fill(buff, ' ');
            
            return new String(buff);
        }
        
        private static void printNodeConnectors(List<Node> current, String textBuffer) {
            for(Node n : current) {
                System.out.print(textBuffer);
                if(n == null) {
                    System.out.print("        ");
                } else {
                    System.out.printf("%s      %s",
                            n.left == null ? " " : "/", n.right == null ? " " : "\\");
                }
    
                System.out.print(textBuffer);
            }
    
            System.out.println();
        }
    }

    /***
     * A base class for any Iterator over Binary-Search Tree.
     * Not relevant to the assignment, but may be interesting to read!
     * DO NOT WRITE CODE IN THE ITERATORS, THIS MAY FAIL THE AUTOMATIC TESTS!!!
     */
    private abstract class BaseBSTIterator implements Iterator<Integer> {
        private List<Integer> values;
        private int index;
        public BaseBSTIterator(Node root) {
            values = new ArrayList<>();
            addValues(root);
            
            index = 0;
        }
        
        @Override
        public boolean hasNext() {
            return index < values.size();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            return values.get(index++);
        }
        
        protected void addNode(Node node) {
            values.add(node.value);
        }
        
        abstract protected void addValues(Node node);
    }
    
    public class InorderIterator extends BaseBSTIterator {
        public InorderIterator(Node root) {
            super(root);
        }

        @Override
        protected void addValues(Node node) {
            if (node != null) {
                addValues(node.left);
                addNode(node);
                addValues(node.right);
            }
        }    
      
    }
    
    public class PreorderIterator extends BaseBSTIterator {

        public PreorderIterator(Node root) {
            super(root);
        }

        @Override
        protected void addValues(AVLTree.Node node) {
            if (node != null) {
                addNode(node);
                addValues(node.left);
                addValues(node.right);
            }
        }        
    }
    
    @Override
    public Iterator<Integer> iterator() {
        return getInorderIterator();
    }
    
    public Iterator<Integer> getInorderIterator() {
        return new InorderIterator(this.root);
    }
    
    public Iterator<Integer> getPreorderIterator() {
        return new PreorderIterator(this.root);
    }
}