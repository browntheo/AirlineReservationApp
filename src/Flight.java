/**
 * Creates a Flight object and associated methods
 * 
 * @author Theo Brown
 */
public class Flight {
	private String flightIdentifier;
	private String originCity;
	private String destCity;
	private String departureTime;
	private SeatMap seating;
	private String airline;

	/**
	 * Constructor
	 * 
	 * @param flightIdentifier
	 * @param originCity
	 * @param destCity
	 * @param departureTime
	 * @param seating
	 * @author 20095697
	 */
	public Flight(String flightIdentifier, String originCity, String destCity, String departureTime, SeatMap seating,
			String airline) {
		super();
		this.setFlightIdentifier(flightIdentifier);
		this.setOriginCity(originCity);
		this.setDestCity(destCity);
		this.setDepartureTime(departureTime);
		this.setSeating(seating);
		this.setAirline(airline);
	}

	/**
	 * @return the flightIdentifier
	 * @author 20095697
	 */
	public String getFlightIdentifier() {
		return flightIdentifier;
	}

	/**
	 * @param flightIdentifier the flightIdentifier to set
	 * @author 20095697
	 */
	public void setFlightIdentifier(String flightIdentifier) {
		this.flightIdentifier = flightIdentifier;
	}

	/**
	 * @return the originCity
	 * @author 20095697
	 */
	public String getOriginCity() {
		return originCity;
	}

	/**
	 * @param originCity the originCity to set
	 * @author 20095697
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	/**
	 * @return the destCity
	 * @author 20095697
	 */
	public String getDestCity() {
		return destCity;
	}

	/**
	 * @param destCity the destCity to set
	 * @author 20095697
	 */
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	/**
	 * @return the departureTime
	 * @author 20095697
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime the departureTime to set
	 * @author 20095697
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * @return SeatMap seating
	 * @author 20095697
	 */
	public SeatMap getSeating() {
		return seating;
	}

	/**
	 * @param seating the seating to set
	 * @author 20095697
	 */
	public void setSeating(SeatMap seating) {
		this.seating = seating;
	}

	/**
	 * @return the airline
	 * @author 20095697
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline the airline to set
	 * @author 20095697
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * Prints Seat details to string
	 * 
	 * @return String
	 * @author 20095697
	 */
	@Override
	public String toString() {
		return "Flight [" + (flightIdentifier != null ? "flightIdentifier=" + flightIdentifier + ", " : "")
				+ (originCity != null ? "originCity=" + originCity + ", " : "")
				+ (destCity != null ? "destCity=" + destCity + ", " : "")
				+ (departureTime != null ? "departureTime=" + departureTime + ", " : "")
				+ (airline != null ? "airline=" + airline : "") + "]";
	}

	public String toDescription() {
		return "Flight: " + this.getFlightIdentifier() + " from: " + this.getOriginCity() + " to: " + this.getDestCity()
				+ " departing: " + this.getDepartureTime();
	}
}
