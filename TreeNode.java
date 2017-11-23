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


public class TreeNode<E> implements Comparable<TreeNode<E>> {
	private E _element;
	private TreeNode<E> _parent;
	private LinkedList<TreeNode<E>> _children;
	private Position _position;

	// Creates a new node
	public TreeNode(E element, TreeNode<E> parent, Position position) {
		this._element = element;
		this._parent = parent;
		_children = new LinkedList<TreeNode<E>>();
		this._position = position;
	}

	// Gets the node's element
	public E getElement() {
		return this._element;
	}
	public Position getPosition() {
		return this._position;
	}
	
	// Gets the node's parent
	public TreeNode<E> getParent() {
		return this._parent;
	}
	
	// Gets the node's children
	public LinkedList<TreeNode<E>> getChildren() {
		return this._children;
	}

	// Compare two node's for lexicographical ordering
	@Override
	public int compareTo(TreeNode<E> o) {
		return this.getPosition().compareTo(o.getPosition());
	}
	
	@Override
	public String toString() {
		return this._element.toString();
	}
}