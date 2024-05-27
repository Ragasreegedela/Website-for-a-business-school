import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
class Guest {
    private String name;
    private String entryTime;
    private String exitTime;

    public Guest(String name, String entryTime, String exitTime) {
        this.name = name;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public String getName() {
        return name;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }
}
class Hotel {
    private Set<Guest> quests = new HashSet<>();
    private Set<Integer> deskAllocation = new HashSet<>();
    private int[] mealCounter = new int[3]; // 0: breakfast, 1: lunch, 2: dinner

    public void addGuest(Guest quest) {
        quests.add(quest);

        System.out.println("Guest: added successfully");
    }

    public void allocateDesk(int deskNumber) {

        if (deskAllocation.size() < 20) {
            deskAllocation.add(deskNumber);
            System.out.println("Desk +deskNumber + allocated successfully");
        } else {
            System.out.println("All desks are currently occupied");
        }
    }

    public void displayGuestsVisitedForEating() {

        System.out.println("Guests visited for eating:");
        System.out.println("Breakfast:" + mealCounter[0]);
        System.out.println("Lunch: " + mealCounter[1]);
        System.out.println("Dinner:" + mealCounter[2]);
    }

    public void visitForEating(int mealType) {
        if (mealType >= 0 && mealType < 3) {
            mealCounter[mealType]++;
            System.out.println("Guest visited for meal successfully");
        } else {
            System.out.println("Invalid meal type");
        }
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("\nHotel Management System Menu:");
            System.out.println("1. Add Guest");
            System.out.println("2. Allocate Desk");
            System.out.println("3. Guests Visited for Eating");
            System.out.println("4. Visit for Eating");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    System.out.print("Enter quest name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter entry time: ");
                    String entryTime = scanner.nextLine();
                    System.out.print("Enter exit time: ");
                    String exitTime = scanner.nextLine();
                    hotel.addGuest(new Guest(name, entryTime, exitTime));
                    break;
                case 2:
                    System.out.print("Enter desk number: ");
                    int deskNumber = scanner.nextInt();
                    hotel.allocateDesk(deskNumber);
                    break;
                case 3:
                    hotel.displayGuestsVisitedForEating();
                    break;
                case 4:
                    System.out.print("Enter meal type (0: Breakfast, 1: Lunch, 2: Dinner): ");
                    int mealType = scanner.nextInt();
                    hotel.visitForEating(mealType);
                    break;

                case 5:

                    System.out.println("Exiting Hotel Management System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}