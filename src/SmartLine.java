/**
 * Defines the methods for the SmartLine airline booking system.
 * 
 * @author Theo Brown 20095697
 **/
public class SmartLine extends Airline {
	/**
	 * Constructor
	 * 
	 * @param airlinename
	 * @author 20095697
	 */
	public SmartLine(String airlineName) {
		super(airlineName);
	}

	/**
	 * Reserves an economy class seat
	 * 
	 * @param Flight        f
	 * @param firstSeatType s
	 * @author 20095697
	 */
	@Override
	public Seat reserveFirstClass(Flight f, SeatType s) {
		// Check for appropriate seat in First class, reserve, and return.
		for (Seat[] seatAr : f.getSeating().getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (!seat.isReserved() && seat.getSeatType() == s && seat.isFirstClass()) {
					seat.setReserved(true);
					System.out.println("Seat Reservation: First Class " + seat.getSeatType() + " seat at: "
							+ seat.getSeatPostion().getRow() + seat.getSeatPostion().getColumn() + " is reserved");
					return seat;
				}
			}
		}
		// Check for any seat in first class, reserve, and return.
		for (Seat[] seatAr : f.getSeating().getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (!seat.isReserved() && seat.isFirstClass()) {
					seat.setReserved(true);
					System.out.println("Seat Reservation: First Class " + seat.getSeatType() + " seat at: "
							+ seat.getSeatPostion().getRow() + seat.getSeatPostion().getColumn() + " is reserved");
					return seat;
				}
			}
		}
		// check for middle seat in economy and reserve adjoining seats,
		// return.
		for (Seat[] seatAr : f.getSeating().getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (!seat.isReserved() && seat.getSeatType() == SeatType.MIDDLE) {
					// If left and right seats are free, reserve and return seat
					if (f.getSeating().getLeft(seat) != null && f.getSeating().getRight(seat) != null) {
						if (!f.getSeating().getLeft(seat).isReserved() && !f.getSeating().getRight(seat).isReserved()) {
							seat.setReserved(true);
							f.getSeating().getLeft(seat).setReserved(true);
							f.getSeating().getRight(seat).setReserved(true);
							System.out.println("Seat Reservation: Economy Class " + seat.getSeatType() + " seats at: "
									+ seat.getSeatPostion().getRow() + seat.getSeatPostion().getColumn() + ", "
									+ f.getSeating().getLeft(seat).getSeatPostion().getRow()
									+ f.getSeating().getLeft(seat).getSeatPostion().getColumn() + ", and "
									+ f.getSeating().getRight(seat).getSeatPostion().getRow()
									+ f.getSeating().getRight(seat).getSeatPostion().getColumn()
									+ " have been reserved");
							return seat;
						}
					}
				}
			}
		}
		// If none of the above work, cannot reserve a seat so return null
		return null;
	}

	/**
	 * Reserves an economy class seat
	 * 
	 * @param Flight        f
	 * @param firstSeatType s
	 * @author 20095697
	 */
	@Override
	public Seat reserveEconomy(Flight f, SeatType s) {
		// Check for appropriate seat in Economy class, reserve, and return.
		for (Seat[] seatAr : f.getSeating().getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (!seat.isReserved() && seat.getSeatType() == s && !seat.isFirstClass()) {
					seat.setReserved(true);
					System.out.println("Seat Reservation: Economy Class " + seat.getSeatType() + " seat at: "
							+ seat.getSeatPostion().getRow() + seat.getSeatPostion().getColumn() + " is reserved");
					return seat;
				}
			}
		}
		// Check for any seat in economy class, reserve, and return.
		for (Seat[] seatAr : f.getSeating().getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (!seat.isReserved() && !seat.isFirstClass()) {
					seat.setReserved(true);
					System.out.println("Seat Reservation: Economy Class " + seat.getSeatType() + " seat at: "
							+ seat.getSeatPostion().getRow() + seat.getSeatPostion().getColumn() + " is reserved");
					return seat;
				}
			}
		}
		// If none of the above work, cannot reserve a seat so return null
		return null;
	}
}
