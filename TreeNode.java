import java.util.LinkedList;

/*
Authors (group members): Dan Levy, Daniel Griessler, and Frank Savino
Email addresses of group members: dlevy2016@my.fit.edu, dgriessler2016@my.fit.edu, and fsavino2018@my.fit.edu
Group name: Group 2b
Course: CSE 2010
Section: 02
Description of the overall algorithm and key data structures:
	* Creates a node for a tree data structure
	* Includes the node's element, the node's parent, and the node's children
*/


public class TreeNode<E> implements Comparable<E> {
	private E element;
	private TreeNode<E> parent;
	private LinkedList<TreeNode<E>> children;

	// Creates a new node
	public TreeNode(E e, TreeNode<E> p) {
		this.element = e;
		this.parent = p;
		children = new LinkedList<TreeNode<E>>();
	}

	// Gets the node's element
	public E getElement() {
		return this.element;
	}
	
	// Gets the node's parent
	public TreeNode<E> getParent() {
		return this.parent;
	}
	
	// Gets the node's children
	public LinkedList<TreeNode<E>> getChildren() {
		return this.children;
	}

	// Compare two node's for lexicographical ordering
	@Override
	public int compareTo(Object o) {
		return this.toString().compareTo(o.toString()); // Converts element to a string for comparison -> may not work on elements that are not a string
	}
	
	@Override
	public String toString() {
		return this.element.toString();
	}
}