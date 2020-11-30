
public abstract class Airline {
	private String airlineName;

	/**
	 * @param airlineName
	 */
	public Airline(String airlineName) {
		super();
		this.airlineName = airlineName;
	}

	@Override
	public String toString() {
		return "Airline [airlineName=" + airlineName + "]";
	}

	/**
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @param airlineName the airlineName to set
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	abstract public Seat reserveFirstClass(Flight f, SeatType s);

	abstract public Seat reserveEconomy(Flight f, SeatType s);
}
