/**
 * Defines seat position within the seating grid.
 * 
 * @author Theo Brown 20095697
 **/
public class SeatPosition {
	private int row;
	private char column;

	/**
	 * @return int row
	 * @author 20095697
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return char  column
	 * @author 20095697
	 */
	public char getColumn() {
		return column;
	}

	/**
	 * @param row
	 * @param column
	 * @author 20095697
	 */
	public SeatPosition(int row, char column) {
		super();
		this.row = row;
		this.column = column;
	}

}
