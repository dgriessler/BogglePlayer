import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/*
  Authors (group members): Dan Levy, Daniel Griessler, and Frank Savino
  Email addresses of group members: dlevy2016@my.fit.edu, dgriessler2016@my.fit.edu, and fsavino2018@my.fit.edu
  Group name: Group 2b
  Course: CSE 2010
  Section: 02
  Description of the overall algorithm and key data structures:
 */

public class BogglePlayer {
	public static Trie DICTIONARY = new Trie(); // Holds all dictionary words in a trie
	public static Tree<Character> POSSIBLE_WORDS = new Tree<Character>(); // Holds all possible Boggle from a certain character in a tree

	/*
	 * Takes in words from dictionary
	 * Adds words to a trie
	 */
	public BogglePlayer(String wordFile) throws FileNotFoundException {
		Scanner stdIn = new Scanner(new File(wordFile));
		while(stdIn.hasNextLine()) {
			DICTIONARY.addWord(stdIn.nextLine());
		}
		stdIn.close();
	}

	/*
	 * Creates a tree of all possible paths from each character in the Boggle board
	 */
	public Word[] getWords(char[][] board) {
		Word[] myWords = new Word[20]; // Words created from the Boggle board
		// Loops through each character in the board and prints it out
		for (int row = 0; row < board.length; row ++) {
			for (int col = 0; col < board[row].length; col ++) {
				System.out.print(board[row][col] + " ");
			}
			System.out.println();
		}
		// Loops through each character in the board and creates all possible paths
		for (int row = 0; row < board.length; row ++) {
			for (int col = 0; col < board[row].length; col ++) {
				POSSIBLE_WORDS.setRoot('0', new Position(-1, -1)); // Sets the root of the tree as the current character in the board
				Position position = new Position(row, col);
				TreeNode<Character> newNode = new TreeNode<Character>(board[row][col], POSSIBLE_WORDS.getRoot(), position);
				POSSIBLE_WORDS.addChild(POSSIBLE_WORDS.getRoot(), newNode);
				getAdjacent(board, row, col, POSSIBLE_WORDS.getRoot()); // Gets all adjacent characters
				return null; // Stops after first character for testing purposed
			}
		}
		return myWords;
	}
	
	/*
	 * Gets adjacent characters from the current character
	 * Includes above, below, next, prev, abovePrev, aboveNext, belowPrev, and aboveNext
	 * Recursive calls get every path from the root
	 */
	public void getAdjacent (char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		for (TreeNode<Character> childNode: parentNode.getChildren()) {
			Position childPosition = childNode.getPosition();
			getAbove(board, childPosition.getRow(), childPosition.getCol(), childNode);
			getBelow(board, childPosition.getRow(), childPosition.getCol(), childNode);
			getNext(board, childPosition.getRow(), childPosition.getCol(), childNode);
			getPrev(board, childPosition.getRow(), childPosition.getCol(), childNode);
			getAbovePrev(board, childPosition.getRow(), childPosition.getCol(), childNode);
			getAboveNext(board, childPosition.getRow(), childPosition.getCol(), childNode);
			getBelowPrev(board, childPosition.getRow(), childPosition.getCol(), childNode);
			getBelowNext(board, childPosition.getRow(), childPosition.getCol(), childNode);
			System.out.println(childNode.getChildren());
			getAdjacent(board, childPosition.getRow(), childPosition.getCol(), childNode); // Recursive call
		}
	}
	
	public void getAbove(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow - 1;
		if (childRow > -1) {
			Position position = new Position(childRow, parentCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[childRow][parentCol], parentNode, position);
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}

	public void getNext(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childCol = parentCol + 1;
		if (childCol < board[0].length) {
			Position position = new Position(parentRow, childCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[parentRow][childCol], parentNode, position);
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}
	
	public void getBelow(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow + 1;
		if (childRow < board.length) {
			Position position = new Position(childRow, parentCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[childRow][parentCol], parentNode, position);
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}

	public void getPrev(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childCol = parentCol - 1;
		if (childCol > -1) {
			Position position = new Position(parentRow, childCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[parentRow][childCol], parentNode, position);
//			System.out.println(newNode.getPosition() + " " + parentNode.getParent().getPosition() + " " + parentNode.getParent().getElement());
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
//				System.out.println("CALL");
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}
	
	public void getAbovePrev(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow - 1;
		int childCol = parentCol - 1;
		if (childRow > -1 && childCol > -1) {
			Position position = new Position(childRow, childCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[childRow][childCol], parentNode, position);
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}

	public void getAboveNext(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow - 1;
		int childCol = parentCol + 1;
		if (childRow > -1 && childCol < board[0].length) {
			Position position = new Position(childRow, childCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[childRow][childCol], parentNode, position);
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}
	
	public void getBelowPrev(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow + 1;
		int childCol = parentCol - 1;
		if (childRow < board.length && childCol > -1) {
			Position position = new Position(childRow, childCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[childRow][childCol], parentNode, position);
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}

	public void getBelowNext(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow + 1;
		int childCol = parentCol + 1;
		if (childRow < board.length && childCol < board[0].length) {
			Position position = new Position(childRow, childCol);
			TreeNode<Character> newNode = new TreeNode<Character>((Character) board[childRow][childCol], parentNode, position);
			if (!newNode.getPosition().equals(parentNode.getParent().getPosition())) {
				POSSIBLE_WORDS.addChild(parentNode, newNode);
			}
		}
	}
}
