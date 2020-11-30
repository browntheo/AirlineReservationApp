/**
 * Defines the methods for the SimpleWay airline booking system.
 * 
 * @author Theo Brown 20095697
 **/
public class SimpleWay extends Airline {
	/**
	 * Constructor
	 * 
	 * @param airlinename
	 * @author 20095697
	 */
	public SimpleWay(String airlineName) {
		super(airlineName);
	}

	/**
	 * Reserves a First class seat
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
		// check for window or middle seat in economy and reserve adjoining seat,
		// return.
		for (Seat[] seatAr : f.getSeating().getSeatsArray()) {
			for (Seat seat : seatAr) {
				if (!seat.isReserved()
						&& (seat.getSeatType() == SeatType.WINDOW || seat.getSeatType() == SeatType.MIDDLE)) {
					// If left seat is free, reserve and return seat
					if (f.getSeating().getLeft(seat) != null) {
						seat.setReserved(true);
						f.getSeating().getLeft(seat).setReserved(true);
						System.out.println("Seat Reservation: Economy Class " + seat.getSeatType() + " seat at: "
								+ seat.getSeatPostion().getRow() + seat.getSeatPostion().getColumn() + " is reserved");
						return seat;
					}
					// if right seat is free, reserve and return seat
					else if (f.getSeating().getRight(seat) != null) {
						seat.setReserved(true);
						f.getSeating().getRight(seat).setReserved(true);
						System.out.println("Seat Reservation: Economy Class " + seat.getSeatType() + " seat at: "
								+ seat.getSeatPostion().getRow() + seat.getSeatPostion().getColumn() + " is reserved");
						return seat;
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
