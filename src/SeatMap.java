/**
 * Defines a SeatMap object and associated methods
 * 
 * @author Theo Brown 20095697
 **/
public abstract class SeatMap {
	protected Seat[][] seats;
	protected int nRows;
	protected int nColumns;
	protected int nFirstClassRows;

	abstract protected void initialiseSeatMap();

	/**
	 * Returns the last column with a seat.
	 * 
	 * @return char
	 * @author 20095697
	 */
	public char lastSeatColumn() {
		char tmp = 'G';
		for (Seat[] seatAr : this.getSeatsArray())
			for (Seat seat : seatAr)
				if (Character.compare(seat.getSeatPostion().getColumn(), tmp) > 0)
					seat.getSeatPostion().getColumn();
		return tmp;
	}

	/**
	 * Returns the final Row number
	 * 
	 * @return int
	 * @author 20095697
	 */
	public int lastSeatRow() {
		return nRows;
	}

	/**
	 * Returns the Seat object with the given values
	 * 
	 * @param int  row
	 * @param char column
	 * 
	 * @author 20095697
	 */
	public Seat getSeat(int i, char c) {
		for (Seat[] seatAr : this.getSeatsArray())
			for (Seat seat : seatAr)
				if (seat.getSeatPostion().getRow() == i && seat.getSeatPostion().getColumn() == c)
					return seat;
		return null;
	}

	public Seat getLeft(Seat seat) {
		switch (seat.getSeatPostion().getColumn()) {
		case 'A':
			return null;
		case 'B':
			return getSeat(seat.getSeatPostion().getRow(), 'A');
		case 'C':
			return getSeat(seat.getSeatPostion().getRow(), 'B');
		case 'D':
			return getSeat(seat.getSeatPostion().getRow(), 'C');
		case 'E':
			return getSeat(seat.getSeatPostion().getRow(), 'D');
		case 'F':
			return getSeat(seat.getSeatPostion().getRow(), 'E');
		case 'G':
			return getSeat(seat.getSeatPostion().getRow(), 'F');
		case 'H':
			return getSeat(seat.getSeatPostion().getRow(), 'G');
		case 'I':
			return getSeat(seat.getSeatPostion().getRow(), 'H');
		default:
			return null;
		}
	}

	/**
	 * Returns the seat to the right of the current one
	 * 
	 * @param Seat
	 * @return seat
	 * @author 20095697
	 */
	public Seat getRight(Seat seat) {
		switch (seat.getSeatPostion().getColumn()) {
		case 'A':
			return getSeat(seat.getSeatPostion().getRow(), 'B');
		case 'B':
			return getSeat(seat.getSeatPostion().getRow(), 'C');
		case 'C':
			return getSeat(seat.getSeatPostion().getRow(), 'D');
		case 'D':
			return getSeat(seat.getSeatPostion().getRow(), 'E');
		case 'E':
			return getSeat(seat.getSeatPostion().getRow(), 'F');
		case 'F':
			return getSeat(seat.getSeatPostion().getRow(), 'G');
		case 'G':
			return getSeat(seat.getSeatPostion().getRow(), 'H');
		case 'H':
			return getSeat(seat.getSeatPostion().getRow(), 'I');
		case 'I':
			return null;
		default:
			return null;
		}
	}

	/**
	 * Prints the SeatMap to string
	 * 
	 * @return String
	 * @author 20095697
	 */
	@Override
	public String toString() {
		String[] columnNames = null;
		if (this.getnColumns() == 9)
			columnNames = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		if (this.getnColumns() == 7)
			columnNames = new String[] { "A", "B", "C", "D", "E", "F", "G" };
		for (String s : columnNames) {
			System.out.print("   " + s);
		}
		System.out.println();
		for (int i = 0; i < this.getnRows(); i++) {
			System.out.print(i + 1);
			for (int j = 0; j < this.getnColumns(); j++) {
				if (this.getSeat(i + 1, columnNames[j].charAt(0)).toString() != null)
					System.out.print(this.getSeat(i + 1, columnNames[j].charAt(0)).toString());
			}
			System.out.println();
		}
		return "";
	}

	/**
	 * Returns if there is an available economy class seat of a given type
	 * 
	 * @param SeatType
	 * @return Seat
	 * @author 20095697
	 */
	public Seat queryAvailableEconomySeat(SeatType s) {
		for (Seat[] seatAr : this.getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (seat.getSeatType() == s && !seat.isFirstClass() && !seat.isReserved()) {
					seat.setReserved(true);
					return seat;
				}
			}
		}
		return null;
	}

	/**
	 * Returns if there is an available first class seat of a given type
	 * 
	 * @param SeatType
	 * @return Seat
	 * @author 20095697
	 */
	public Seat queryAvailableFirstClassSeat(SeatType s) {
		for (Seat[] seatAr : this.getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (seat.getSeatType() == s && seat.isFirstClass() && !seat.isReserved()) {
					seat.setReserved(true);
					return seat;
				}
			}
		}
		return null;
	}

	/**
	 * @return the seats
	 * @author 20095697
	 */
	public Seat[][] getSeatsArray() {
		return seats;
	}

	/**
	 * @return the nRows
	 * @author 20095697
	 */
	public int getnRows() {
		return nRows;
	}

	/**
	 * @return the nColumns
	 * @author 20095697
	 */
	public int getnColumns() {
		return nColumns;
	}

	/**
	 * @return the nFirstClassRows
	 * @author 20095697
	 */
	public int getnFirstClassRows() {
		return nFirstClassRows;
	}
}
