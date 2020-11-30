/**
 * Defines an AirbusSeatMap object and associated methods
 * 
 * @author Theo Brown 20095697
 **/
public class AirBusSeatMap extends SeatMap {

	/**
	 * Constructor
	 * 
	 * @author 20095697
	 */
	public AirBusSeatMap() {
		super();
		this.nRows = 12;
		this.nColumns = 9;
		this.nFirstClassRows = 6;
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
					type = SeatType.MIDDLE;
					break;
				case 2:
					ch = 'C';
					type = SeatType.AISLE;
					break;
				case 3:
					ch = 'D';
					type = SeatType.AISLE;
					break;
				case 4:
					ch = 'E';
					type = SeatType.MIDDLE;
					break;
				case 5:
					ch = 'F';
					type = SeatType.AISLE;
					break;
				case 6:
					ch = 'G';
					type = SeatType.AISLE;
					break;
				case 7:
					ch = 'H';
					type = SeatType.MIDDLE;
					break;
				case 8:
					ch = 'I';
					type = SeatType.WINDOW;
					break;
				default:
					ch = 'Z';
					break;
				}

				// Check for if the seat is in the first class row range
				if (i < getnFirstClassRows())
					firstClass = true;
				else
					firstClass = false;
				SeatPosition pos = new SeatPosition(i + 1, ch);
				Seat tmpSeat = new Seat(pos, type, firstClass);
				this.seats[i][j] = tmpSeat;
			}
		}
	}
}
