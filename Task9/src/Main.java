import java.util.Scanner;

class ParkingLot {
    private int capacity;
    private boolean[] slots;
    private String[] timings;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.slots = new boolean[capacity];
        this.timings = new String[capacity];
    }

    public void bookSlot(int slot, String timing) {
        if (slot < 0 || slot >= capacity) {
            System.out.println("Invalid slot number.");
            return;
        }

        if (slots[slot]) {
            System.out.println("Slot " + slot + " is already booked.");
        } else {
            slots[slot] = true;
            timings[slot] = timing;
            System.out.println("Slot " + slot + " booked successfully at time: " + timing);
        }
    }

    public void showVacantSlots() {
        System.out.println("Vacant Slots:");
        for (int i = 0; i < capacity; i++) {
            if (!slots[i]) {
                System.out.println("Slot " + i);
            }
        }
    }

    public void showBookedSlots() {
        System.out.println("Booked Slots:");
        for (int i = 0; i < capacity; i++) {
            if (slots[i]) {
                System.out.println("Slot " + i + " booked at time: " + timings[i]);
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Mention the capacity of the parking lot: ");
        int capacity = scanner.nextInt();

        ParkingLot parkingLot = new ParkingLot(capacity);

        while (true) {
            System.out.println("\n1. Booking a slot");
            System.out.println("2. Show vacant slots");
            System.out.println("3. Show booked slots");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter slot number: ");
                    int slot = scanner.nextInt();
                    System.out.print("Enter timing: ");
                    scanner.nextLine(); // Consume newline character
                    String timing = scanner.nextLine();
                    parkingLot.bookSlot(slot, timing);
                    break;
                case 2:
                    parkingLot.showVacantSlots();
                    break;
                case 3:
                    parkingLot.showBookedSlots();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}