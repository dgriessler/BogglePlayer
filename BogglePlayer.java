import java.io.File;
import java.io.FileNotFoundException;
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
		// Loops through each character in the board
		for (int row = 0; row < board.length; row ++) {
			for (int col = 0; col < board[row].length; col ++) {
				System.out.print(board[row][col] + " "); // Prints each character in the board
				POSSIBLE_WORDS.setRoot('0'); // Sets the root of the tree as the current character in the board
				POSSIBLE_WORDS.addChild(POSSIBLE_WORDS.getRoot(), POSSIBLE_WORDS.createNode(board[row][col], POSSIBLE_WORDS.getRoot()));
				getAdjacent(board, row, col, POSSIBLE_WORDS.getRoot()); // Gets all adjacent characters
			}
			System.out.println();
		}
		return myWords;
	}
	
	/*
	 * Gets adjacent characters from the current character
	 * Includes above, below, next, prev, abovePrev, aboveNext, belowPrev, and aboveNext
	 * Recursive calls get every path from the root
	 */
	public void getAdjacent (char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		for (TreeNode<Character> childNode: parentNode.getChildren()) { // ConcurrentModificationError thrown
			getAbove(board, parentRow, parentCol, childNode);
			getBelow(board, parentRow, parentCol, childNode);
			getNext(board, parentRow, parentCol, childNode);
			getPrev(board, parentRow, parentCol, childNode);
			getAbovePrev(board, parentRow, parentCol, childNode);
			getAboveNext(board, parentRow, parentCol, childNode);
			getBelowPrev(board, parentRow, parentCol, childNode);
			getBelowNext(board, parentRow, parentCol, childNode);
			System.out.println(parentNode.getChildren());
			getAdjacent(board, parentRow, parentCol, childNode); // Recursive call
		}
	}
	
	public void getAbove(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow - 1;
		if (childRow > -1) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[childRow][parentCol], parentNode));
		}
	}

	public void getNext(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childCol = parentCol + 1;
		if (childCol < board[0].length) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[parentRow][childCol], parentNode));
		}
	}
	
	public void getBelow(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow + 1;
		if (childRow < board.length) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[childRow][parentCol], parentNode));
		}
	}

	public void getPrev(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childCol = parentCol - 1;
		if (childCol > -1) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[parentRow][childCol], parentNode));
		}
	}
	
	public void getAbovePrev(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow - 1;
		int childCol = parentCol - 1;
		if (childRow > -1 && childCol > -1) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[childRow][childCol], parentNode));
		}
	}

	public void getAboveNext(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow - 1;
		int childCol = parentCol + 1;
		if (childRow > -1 && childCol < board[0].length) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[childRow][childCol], parentNode));
		}
	}
	
	public void getBelowPrev(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow + 1;
		int childCol = parentCol - 1;
		if (childRow < board.length && childCol > -1) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[childRow][childCol], parentNode));
		}
	}

	public void getBelowNext(char[][] board, int parentRow, int parentCol, TreeNode<Character> parentNode) {
		int childRow = parentRow + 1;
		int childCol = parentCol + 1;
		if (childRow < board.length && childCol < board[0].length) {
			POSSIBLE_WORDS.addChild(parentNode, new TreeNode<Character>((Character) board[childRow][childCol], parentNode));
		}
	}
}
