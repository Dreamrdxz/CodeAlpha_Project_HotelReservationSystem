import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Room class
class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

// Reservation class
class Reservation {
    private Room room;
    private String guestName;
    private String paymentStatus;

    public Reservation(Room room, String guestName, String paymentStatus) {
        this.room = room;
        this.guestName = guestName;
        this.paymentStatus = paymentStatus;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "room=" + room +
                ", guestName='" + guestName + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}

// Hotel class
class Hotel {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Reservation makeReservation(Room room, String guestName) {
        room.setAvailable(false);
        Reservation reservation = new Reservation(room, guestName, "Pending");
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> viewReservations() {
        return reservations;
    }
}

// Payment class
class Payment {
    public static boolean processPayment(double amount) {
        // In a real system, this would interact with a payment gateway
        System.out.println("Processing payment of $" + amount);
        return true;
    }
}

// Main class
public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.addRoom(new Room(101, "Single"));
        hotel.addRoom(new Room(102, "Double"));
        hotel.addRoom(new Room(103, "Suite"));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View reservations");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room category (Single/Double/Suite): ");
                    String category = scanner.nextLine();
                    List<Room> availableRooms = hotel.searchAvailableRooms(category);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms in this category.");
                    } else {
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Room room = null;
                    for (Room r : hotel.searchAvailableRooms("")) {
                        if (r.getRoomNumber() == roomNumber) {
                            room = r;
                            break;
                        }
                    }
                    if (room == null) {
                        System.out.println("Room not found or not available.");
                    } else {
                        System.out.print("Enter guest name: ");
                        String guestName = scanner.nextLine();
                        System.out.print("Enter payment amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline

                        if (Payment.processPayment(amount)) {
                            Reservation reservation = hotel.makeReservation(room, guestName);
                            System.out.println("Reservation made: " + reservation);
                        } else {
                            System.out.println("Payment failed.");
                        }
                    }
                    break;
                case 3:
                    List<Reservation> reservations = hotel.viewReservations();
                    for (Reservation res : reservations) {
                        System.out.println(res);
                    }
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}