
/*
  Author: Dan Levy
  Email: dlevy2016@my.fit.edu
  Course: CSE 2010
  Section: 02
  Description:
	* Holds the row and column of a vertex in a grid
 */

public class Position implements Comparable<Position> {
	private int _row;
	private int _col;
	
	/*
	 * Constructs a new position object holding the vertex's row and column in a grid
	 */
	public Position(int row, int col) {
		this._row = row;
		this._col = col;
	}
	
	public int getRow() {
		return this._row;
	}
	public int getCol() {
		return this._col;
	}
	
	public void setRow(int row) {
		this._row = row;
	}
	public void setCol(int col) {
		this._col = col;
	}
	
	/*
	 * Returns an ordered pair containing the row and column of the vertex in a grid
	 */
	@Override
	public String toString() {
		return "(" + this._row + "," + this._col + ")";
	}

	/*
	 * Compares this position with the other given vertex
	 * Compares by multiplying the row by 10 and adding the column onto it (ex. (2,3) = 23 and (3,2) = 32
	 */
	@Override
	public int compareTo(Position other) {
		return ((this._row * 10) + this._col) - ((other._row * 10) + other._col);
	}
	
	@Override
	public boolean equals(Object other) {
		return this.compareTo((Position) other) == 0;
	}
}
