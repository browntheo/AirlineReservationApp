/**
 * Defines an BoeingSeatMap object and associated methods
 * 
 * @author Theo Brown 20095697
 **/
public class BoeingSeatMap extends SeatMap {

	/**
	 * Constructor
	 * 
	 * @author 20095697
	 */
	public BoeingSeatMap() {
		super();
		this.nRows = 10;
		this.nColumns = 7;
		this.nFirstClassRows = 4;
		this.seats = null;
		initialiseSeatMap();
	}

	/**
	 * Initialises a SeatMap
	 * 
	 * @author 20095697
	 */
	protected void initialiseSeatMap() {
		this.seats = new Seat[getnRows()][getnColumns()];
		for (int i = 0; i < this.getnRows(); i++) {
			for (int j = 0; j < this.getnColumns(); j++) {
				char ch = 0;
				SeatType type = SeatType.AISLE;
				boolean firstClass = false;
				// set seat type and column letter by position
				switch (j) {
				case 0:
					ch = 'A';
					type = SeatType.WINDOW;
					break;
				case 1:
					ch = 'B';
					type = SeatType.AISLE;
					break;
				case 2:
					ch = 'C';
					type = SeatType.AISLE;
					break;
				case 3:
					ch = 'D';
					type = SeatType.MIDDLE;
					break;
				case 4:
					ch = 'E';
					type = SeatType.AISLE;
					break;
				case 5:
					ch = 'F';
					type = SeatType.AISLE;
					break;
				case 6:
					ch = 'G';
					type = SeatType.WINDOW;
					break;
				}
				// Check for if the seat is in the first class row range
				if (i < getnFirstClassRows()) {
					firstClass = true;
				} else
					firstClass = false;
				SeatPosition pos = new SeatPosition(i + 1, ch);
				this.seats[i][j] = new Seat(pos, type, firstClass);
			}
		}
	}
}
