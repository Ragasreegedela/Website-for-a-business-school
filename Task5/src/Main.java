import java.util.ArrayList;
import java.util.Scanner;

class Bus {
    private String destination;
    private int totalSeats;
    private int availableSeats;

    public Bus(String destination, int totalSeats) {
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookTicket(int numTickets) {
        if (numTickets > availableSeats) {
            System.out.println("Sorry, not enough seats available.");
        } else {
            availableSeats -= numTickets;
            System.out.println("Ticket(s) booked successfully for " + destination + ".");
        }
    }
}

class ReservationSystem {
    private ArrayList<Bus> buses;

    public ReservationSystem() {
        buses = new ArrayList<>();
    }

    public void addBus(Bus bus) {
        buses.add(bus);
    }

    public void showAvailableTickets() {
        System.out.println("Available Tickets:");
        for (Bus bus : buses) {
            System.out.println("Destination: " + bus.getDestination() +
                    ", Available Seats: " + bus.getAvailableSeats());
        }
    }

    public void showDestinations() {
        System.out.println("Available Destinations:");
        for (Bus bus : buses) {
            System.out.println(bus.getDestination());
        }
    }

    public void bookTicket(String destination, int numTickets) {
        for (Bus bus : buses) {
            if (bus.getDestination().equalsIgnoreCase(destination)) {
                bus.bookTicket(numTickets);
                return;
            }
        }
        System.out.println("No bus available for the destination: " + destination);
    }
}

class Main {
    public static void main(String[] args) {
        ReservationSystem reservationSystem = new ReservationSystem();
        reservationSystem.addBus(new Bus("Hyderabad", 50));
        reservationSystem.addBus(new Bus("Kochi", 40));
        reservationSystem.addBus(new Bus("Banglore", 30));

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. Show Available Tickets");
            System.out.println("2. Show Destinations");
            System.out.println("3. Book Ticket");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    reservationSystem.showAvailableTickets();
                    break;
                case 2:
                    reservationSystem.showDestinations();
                    break;
                case 3:
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter number of tickets: ");
                    int numTickets = scanner.nextInt();
                    reservationSystem.bookTicket(destination, numTickets);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
