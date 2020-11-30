/**
 * Defines a Seat object and associated methods
 * 
 * @author Theo Brown 20095697
 **/
public class Seat {
	private SeatType seatType;
	private boolean firstClass;
	private boolean reserved;
	private SeatPosition seatPostion;

	/**
	 * Constructor
	 * 
	 * @param seatPostion
	 * @param seatType
	 * @param firstClass
	 * @author 20095697
	 */
	public Seat(SeatPosition seatPostion, SeatType seatType, boolean firstClass) {
		super();
		this.seatPostion = seatPostion;
		this.seatType = seatType;
		this.firstClass = firstClass;
		this.reserved = false;
	}

	/**
	 * @return the reserved
	 * @author 20095697
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * @param reserved the reserved to set
	 * @author 20095697
	 */
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	/**
	 * @return the seatType
	 * @author 20095697
	 */
	public SeatType getSeatType() {
		return seatType;
	}

	/**
	 * @return the firstClass
	 * @author 20095697
	 */
	public boolean isFirstClass() {
		return firstClass;
	}

	/**
	 * @return the seatPostion
	 * @author 20095697
	 */
	public SeatPosition getSeatPostion() {
		return seatPostion;
	}

	/**
	 * Prints description of the Seat
	 * 
	 * @return String
	 * @author 20095697
	 */
	public String toDescription() {
		String sClass = "economy";
		String res = "not reserved.";
		if (this.isFirstClass())
			sClass = "first";
		if (this.isReserved())
			res = "reserved.";
		return "The " + sClass + " class " + this.getSeatType() + " seat at " + this.getSeatPostion().getRow()
				+ this.getSeatPostion().getColumn() + " is " + res;
	}

	/**
	 * Prints Seat details to string
	 * 
	 * @return String
	 * @author 20095697
	 */
	@Override
	public String toString() {
		if (this.seatType == seatType.WINDOW) {
			if (this.isFirstClass()) {
				if (this.isReserved())
					return "[WX]";
				else
					return "[W_]";
			} else {
				if (this.isReserved())
					return "[wX]";
				else
					return "[w_]";
			}
		}
		if (this.seatType == seatType.MIDDLE) {
			if (this.isFirstClass()) {
				if (this.isReserved())
					return "[MX]";
				else
					return "[M_]";
			} else {
				if (this.isReserved())
					return "[mX]";
				else
					return "[m_]";
			}
		}
		if (this.seatType == seatType.AISLE) {
			if (this.isFirstClass()) {
				if (this.isReserved())
					return "[AX]";
				else
					return "[A_]";
			} else {
				if (this.isReserved())
					return "[aX]";
				else
					return "[a_]";
			}
		}
		return "";
	}
}
