import java.util.Iterator;
import java.util.LinkedList;

/*
Authors (group members): Dan Levy, Daniel Griessler, and Frank Savino
Email addresses of group members: dlevy2016@my.fit.edu, dgriessler2016@my.fit.edu, and fsavino2018@my.fit.edu
Group name: Group 2b
Course: CSE 2010
Section: 02
Description of the overall algorithm and key data structures:
 	* Creates tree structure
 	* Includes and iterator, adding nodes, and tree traversing
*/

public class Tree<E> {
	/*
	 * Creates an iterator that is filled with nodes from a specific tree traversal method
	 */
	private class ElementIterator implements Iterator<E> {
		Iterator<TreeNode<E>> posIterator = positions().iterator();

		// Checks if there is another node
		@Override
		public boolean hasNext() {
			return posIterator.hasNext();
		}
		
		// Gets the next node
		@Override
		public E next() {
			return posIterator.next().getElement();
		}
		
		// Removes the node
		public void remove() {
			posIterator.remove();
		}
	}
	
	// Creates a new instance of ElementIterator
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
	// Passes in an empty iterable list to ElementIterator
	public Iterable<TreeNode<E>> positions() {
		return null;
	}
	
	// Creates a new node
	protected TreeNode<E> createNode(E element, TreeNode<E> parent, Position position) {
		return new TreeNode<E>(element, parent, position);
	}
	
	private TreeNode<E> root; // Root node
	private int size; // Number of elements in tree
	
	// Gets the root node
	public TreeNode<E> getRoot() {
		return this.root;
	}
	
	// Creates a new instance of the Tree data structure
	public Tree(E e, Position position) {
		root = new TreeNode<E>(e, null, position);
		size ++;
	}
	
	public Tree() {
		root = null;
	}
	
	public TreeNode<E> setRoot(E e, Position position) {
		this.root = new TreeNode<E>(e, null, position);
		size ++;
		return this.root;
	}
	
	// Gets number of elements in the tree
	public int getSize() {
		return this.size;
	}
	
	// Checks if the tree is empty
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	// Adds a new node to the tree in correct lexicographical order
	public void addChild(TreeNode<E> parentNode, TreeNode<E> childNode) {
		if (parentNode.getChildren().size() == 0) { // If tree is empty -> add the node to the beginning of the parentNode's children
			parentNode.getChildren().addFirst(childNode);
		} else { // Iterate through all children of the parentNode and find the position for the new node in lexicographical order
			int counter = 0;
			for (TreeNode<E> node: parentNode.getChildren()) {
				if (node.compareTo(childNode) < 0) {
					counter ++;
				} else {
					break;
				}
			}
			parentNode.getChildren().add(counter, childNode); // Adds the node to the correct position in the parentNode's children
		}
		this.size ++; // Increments size of tree
	}
	
	// Finds the parentNode of the currentNode in preorder (top-down)
	public TreeNode<E> findParent(String parentNode, TreeNode<E> currentNode) {
		if (currentNode.getElement().equals(parentNode)) { // If parentNode is the currentNode -> return the currentNode
			return currentNode;
		} else { // Otherwise recur through the currentNode's children, grandchildren, etc.
			for (TreeNode<E> e: currentNode.getChildren()) {
				TreeNode<E> current = findParent(parentNode, e);
				if (current != null) {
					return current;
				}
			}
			return null;
		}
	}
	
	// Gets all children of the node passed in
	public LinkedList<TreeNode<E>> getChildren(TreeNode<E> parentNode) {
		return parentNode.getChildren();
	}
	
	// Gets parent of node passed in
	public TreeNode<E> getParent(TreeNode<E> childNode) {
		return childNode.getParent();
	}
	
	// Prints tree using indentation for children
	public void printPreorderIndent(TreeNode<E> p, int d) {
		for (int i = 0; i < d; i ++) { // Adds correct amount of indentation (spaces)
			System.out.print("  ");
		}
		System.out.println(p.getElement());
		// Gets all children using recursion
		for (TreeNode<E> c: p.getChildren()) {
			printPreorderIndent(c, d+1);
		}
	}
	
	// Traverses tree top-down
	private void preorderSubtree(TreeNode<E> rootNode, LinkedList<TreeNode<E>> snapshot) {
		snapshot.add(rootNode); // Adds parentNode (rootNode) to snapshot
		for (TreeNode<E> node: rootNode.getChildren()) { // Recurs on children
			preorderSubtree(node, snapshot);
		}
	}
	
	// Creates iterator for preorderSubtree
	public Iterable<TreeNode<E>> preorder() {
		return iterableLinkedList("preorder");
	}
	
	// Traverses tree bottom-up
	private void postorderSubtree(TreeNode<E> rootNode, LinkedList<TreeNode<E>> snapshot) {
		for (TreeNode<E> node: rootNode.getChildren()) {
			preorderSubtree(node, snapshot); // Recurs on children
		}
		snapshot.add(rootNode); // Adds parentNode (rootNode) to snapshot
	}
	
	// Creates iterator for postorderSubtre
	public Iterable<TreeNode<E>> postorder() {
		return iterableLinkedList("postorder");
	}
	
	// Calls preorderSubtree or postorderSubtree if the tree is not empty
	private Iterable<TreeNode<E>> iterableLinkedList(String order) {
		LinkedList<TreeNode<E>> snapshot = new LinkedList<>(); // Creates a snapshot that will contain all of the nodes in the tree
		// If the tree is not empty -> call the proper method
		if (!this.isEmpty()) {
			if (order == "preorder") {
				preorderSubtree(this.root, snapshot);
			} else {
				postorderSubtree(this.root, snapshot);
			}
			
		}
		return snapshot;
	}
}
