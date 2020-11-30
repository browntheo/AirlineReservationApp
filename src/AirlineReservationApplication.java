import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides user interface for booking seats on flights. Provides methods for
 * loading, Updating, and saving log files
 * 
 * @author Theo Brown 20095697
 **/
public class AirlineReservationApplication {
	private static ArrayList<Flight> flights = new ArrayList<Flight>();
	private static ArrayList<Airline> airlines = new ArrayList<Airline>();
	private static Scanner keyboard = new Scanner(System.in);

	/**
	 * Runs everything.
	 * 
	 * @author 20095697
	 */
	public static void main(String[] args0) throws NumberFormatException, IOException {
		try {
			setup();
			update();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Writes updated booking data to file.
	 * 
	 * @author 20095697
	 */
	public static void writeData() throws IOException {
		try {
			String text = "";
			text += flights.size() + "\n";
			for (Flight f : flights) {
				text += "@Flight\n@F_info\n";
				text += f.getFlightIdentifier() + "," + f.getOriginCity() + "," + f.getDestCity() + ","
						+ f.getDepartureTime() + "," + f.getAirline() + "\n";
				text += "@F_R_Seats\n";
				for (Seat[] s : f.getSeating().getSeatsArray())
					for (Seat seat : s)
						if (seat.isReserved())
							text += seat.getSeatPostion().getRow() + "_" + seat.getSeatPostion().getColumn() + ",";
				text += "\n@@\n";
			}
			System.out.print(text); // For admin confirmation of day's flight bookings
			// Updates the file with the new seat bookings.
			FileOutputStream fileOut = new FileOutputStream("./src/flights_updated.txt");
			fileOut.write(text.getBytes());
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the flights from file and initialises the various arrays for access.
	 * 
	 * @author 20095697
	 */
	public static void setup() throws NumberFormatException, IOException {
		try {
			Flight f = null;
			File file = new File("./src/flights.txt"); // Loads flight log file
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String data = sc.nextLine();
				String[] values = null;
				if (data.equalsIgnoreCase("@F_info")) {
					String tmp = sc.nextLine();
					values = tmp.split(",");
					for (String s : values)
						s = s.replaceAll(",", "");
					String flightIdentifier = values[0];
					String originCity = values[1];
					String destCity = values[2];
					String departureTime = values[3];
					String airline = values[4];
					SeatMap seating = null;
					// initialise boeing or airbus seating models depending on model
					if (airline.equalsIgnoreCase("Airbus")) {
						seating = new AirBusSeatMap();
						seating.initialiseSeatMap();
					}
					if (airline.equalsIgnoreCase("Boeing")) {
						seating = new BoeingSeatMap();
						seating.initialiseSeatMap();
					}
					// create flight plan and load to array.
					f = new Flight(flightIdentifier, originCity, destCity, departureTime, seating, airline);
					flights.add(f);
				}
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Takes user input and allows them to book seats on available flights.
	 * 
	 * @author 20095697
	 */
	public static void update() throws IOException {
		try {
			while (true) {
				String tmp = "";
				System.out.println("*****Welcome to Airline Reservation Application*****");
				System.out.println(
						"Which airline would you like to travel with? (Select a number or write \"quit\" if you want to quit the application!)");
				System.out.println("1: SimpleWay Airline");
				System.out.println("2: SmartLine Airline");

				tmp = keyboard.next();

				if (tmp.equalsIgnoreCase("1")) {// Simpleway
					SimpleWay ar = new SimpleWay("SimpleWay");
					airlines.add(ar);
					System.out.println("Welcome to the SimpleWay Airline reservation system\r\n"
							+ "Which flight would you like to reserve a seat on?(choose a number)");
					int i = 1;
					for (Flight f : flights) {
						System.out.println(i + ": " + f.toDescription());
						i++;
					}
					tmp = keyboard.next();
					if (tmp.equalsIgnoreCase("1")) {// 1: Flight: AC123 from: Ottawa to: London departing: 17:00h
						if (flights.get(0).getAirline().equalsIgnoreCase("AirBus")) { // is airbus
							SeatMap m = new AirBusSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(0).toDescription());
							System.out.println(flights.get(0).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class \n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");

								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");

								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								System.out.println(flights.get(0).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						} else {
							SeatMap m = new BoeingSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(0).toDescription());
							System.out.println(flights.get(0).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class \n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("1")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								System.out.println(flights.get(0).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						}
					} else if (tmp.equalsIgnoreCase("2")) {
						// 2: Flight: AC789 from: Auckland to: Sydney departing: 08:00h
						if (flights.get(1).getAirline().equalsIgnoreCase("AirBus")) {
							SeatMap m = new AirBusSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(1).toDescription());
							System.out.println("Booking seats for " + flights.get(1).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class\n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								System.out.println(flights.get(1).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						} else {
							SeatMap m = new BoeingSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(1).toDescription());
							System.out.println(flights.get(1).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class \n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SimpleWay way = new SimpleWay(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								// Display seat plan
								System.out.println(flights.get(1).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						}
					} else if (tmp.equalsIgnoreCase("quit")) {
						// Exit booking system.
						System.out.println("Goodbye.");
						writeData();
						System.exit(0);
					}
				} else if (tmp.equalsIgnoreCase("2")) {
					// SmartLine
					SmartLine ar = new SmartLine("SmartLine");
					airlines.add(ar);
					System.out.println("Welcome to the SmartLine Airline reservation system\r\n"
							+ "Which flight would you like to reserve a seat on?(choose a number)");
					int i = 1;
					for (Flight f : flights) {
						System.out.println(i + ": " + f.toDescription());
						i++;
					}
					tmp = keyboard.next();
					if (tmp.equalsIgnoreCase("1")) {// 1: Flight: AC123 from: Ottawa to: London departing: 17:00h
						if (flights.get(0).getAirline().equalsIgnoreCase("AirBus")) {
							SeatMap m = new AirBusSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(0).toDescription());
							System.out.println(flights.get(0).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class \n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("0")) {// Window
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");

								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");

								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								System.out.println(flights.get(0).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						} else {
							SeatMap m = new BoeingSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(0).toDescription());
							System.out.println(flights.get(0).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class \n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveFirstClass(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.WINDOW);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.AISLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(0).getAirline());
									Seat s = way.reserveEconomy(flights.get(0), SeatType.MIDDLE);
									if (s != null)
										flights.get(0).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								System.out.println(flights.get(0).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						}
					} else if (tmp.equalsIgnoreCase("2")) {
//							2: Flight: AC789 from: Auckland to: Sydney departing: 08:00h
						if (flights.get(0).getAirline().equalsIgnoreCase("AirBus")) {
							SeatMap m = new AirBusSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(1).toDescription());
							System.out.println("Booking seats for " + flights.get(1).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class\n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");

								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");

								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								System.out.println(flights.get(1).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						} else {
							SeatMap m = new BoeingSeatMap();
							m.initialiseSeatMap();
							System.out.println("Booking seats for " + flights.get(1).toDescription());
							System.out.println(flights.get(1).getSeating().toString());
							System.out.println(
									"1. Reserve First Class \n2. Reserve Economy Class \n3. Show Seating Map \n4. Quit");
							// if First Class
							tmp = keyboard.next();
							if (tmp.equalsIgnoreCase("1")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveFirstClass(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} // if Economy Class
							else if (tmp.equalsIgnoreCase("2")) {
								System.out.println("Which seat type? \n1. WINDOW\r\n" + "2. AISLE\r\n" + "3. MIDDLE");
								tmp = keyboard.next();
								if (tmp.equalsIgnoreCase("1")) {// Window
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.WINDOW);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("2")) { // Aisle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.AISLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								} else if (tmp.equalsIgnoreCase("3")) {// Middle
									SmartLine way = new SmartLine(flights.get(1).getAirline());
									Seat s = way.reserveEconomy(flights.get(1), SeatType.MIDDLE);
									if (s != null)
										flights.get(1).getSeating().getSeat(s.getSeatPostion().getRow(),
												s.getSeatPostion().getColumn());
									else
										System.out.println(
												"We're sorry but there are no more seats of this type available. Please try again with a different seat type.");
								}
							} else if (tmp.equalsIgnoreCase("3")) {
								// Display seat plan
								System.out.println(flights.get(1).getSeating().toString());
							} else if (tmp.equalsIgnoreCase("4") || tmp.equalsIgnoreCase("quit")) {
								// Exit booking system.
								System.out.println("Goodbye.");
								writeData();
								System.exit(0);
							}
						}
					} else if (tmp.equalsIgnoreCase("quit")) {
						// Exit booking system.
						System.out.println("Goodbye.");
						writeData();
						System.exit(0);
					}

				} else if (tmp.equalsIgnoreCase("quit")) {
					// Exit booking system.
					System.out.println("Goodbye.");
					writeData();
					System.exit(0);
				}
			}
		} catch (

		Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * A short method for printing contents of the Flight listings for system data
	 * verification.
	 * 
	 * @param ArrayList<Flight> first parameter description
	 * @author 20095697
	 */
	public static void printFlights(ArrayList<Flight> f) {
		for (Flight fl : f)
			if (fl != null)
				System.out.println(fl.toString());
	}
}
