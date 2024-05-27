import java.util.ArrayList;
import java.util.Scanner;

class Member {
    private String name;
    private int age;
    private String phoneNumber;
    private String address;
    private String equipmentPreference;
    private String timeSlotPreference;
    private String subscriptionPlan;
    private String dietPlan;

    public Member(String name, int age, String phoneNumber, String address, String equipmentPreference, String timeSlotPreference, String subscriptionPlan, String dietPlan) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.equipmentPreference = equipmentPreference;
        this.timeSlotPreference = timeSlotPreference;
        this.subscriptionPlan = subscriptionPlan;
        this.dietPlan = dietPlan;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEquipmentPreference() {
        return equipmentPreference;
    }

    public String getTimeSlotPreference() {
        return timeSlotPreference;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public String getDietPlan() {
        return dietPlan;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Phone Number: " + phoneNumber + ", Address: " + address +
                ", Equipment Preference: " + equipmentPreference + ", Time Slot Preference: " + timeSlotPreference +
                ", Subscription Plan: " + subscriptionPlan + ", Diet Plan: " + dietPlan;
    }
}

class Equipment {
    private String name;
    private String description;

    public Equipment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class TimeSlot {
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    public TimeSlot(String dayOfWeek, String startTime, String endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}

class Exercise {
    private String name;
    private String description;

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class SubscriptionPlan {
    private String name;
    private double cost;

    public SubscriptionPlan(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class DietPlan {
    private String name;
    private String description;

    public DietPlan(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class GymManagementSystem {
    private ArrayList<Member> members;
    private ArrayList<Equipment> equipment;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<Exercise> exercises;
    private ArrayList<SubscriptionPlan> subscriptionPlans;
    private ArrayList<DietPlan> dietPlans;

    public GymManagementSystem() {
        members = new ArrayList<>();
        equipment = new ArrayList<>();
        timeSlots = new ArrayList<>();
        exercises = new ArrayList<>();
        subscriptionPlans = new ArrayList<>();
        dietPlans = new ArrayList<>();

        // Initialize equipment, time slots, exercises, subscription plans, and diet plans
        initializeEquipment();
        initializeTimeSlots();
        initializeExercises();
        initializeSubscriptionPlans();
        initializeDietPlans();
    }

    private void initializeEquipment() {
        // Add initial equipment to the list
        equipment.add(new Equipment("Treadmill", "Cardio equipment"));
        equipment.add(new Equipment("Dumbbells", "Free weights"));
        equipment.add(new Equipment("Bench Press", "Weight lifting equipment"));
        // Add more equipment as needed
    }

    private void initializeTimeSlots() {
        // Add initial time slots to the list
        timeSlots.add(new TimeSlot("Monday", "08:00 AM", "09:00 AM"));
        timeSlots.add(new TimeSlot("Tuesday", "06:00 PM", "07:00 PM"));
        timeSlots.add(new TimeSlot("Wednesday", "07:00 AM", "08:00 AM"));
        // Add more time slots as needed, including Thursday, Friday, and Saturday
        timeSlots.add(new TimeSlot("Thursday", "05:00 PM", "06:00 PM"));
        timeSlots.add(new TimeSlot("Friday", "09:00 AM", "10:00 AM"));
        timeSlots.add(new TimeSlot("Saturday", "10:00 AM", "11:00 AM"));
    }

    private void initializeExercises() {
        // Add initial exercises to the list
        exercises.add(new Exercise("Running", "Cardio exercise"));
        exercises.add(new Exercise("Weightlifting", "Strength training"));
        exercises.add(new Exercise("Yoga", "Flexibility and relaxation"));
        // Add more exercises as needed
        exercises.add(new Exercise("Cycling", "Indoor cycling workout"));
        exercises.add(new Exercise("Pilates", "Core strength and flexibility"));
    }

    private void initializeSubscriptionPlans() {
        // Add initial subscription plans
        subscriptionPlans.add(new SubscriptionPlan("Basic Plan", 100.00));
        subscriptionPlans.add(new SubscriptionPlan("Standard Plan", 75.00));
        subscriptionPlans.add(new SubscriptionPlan("Premium Plan", 200.00));
    }

    private void initializeDietPlans() {
        // Add initial diet plans
        dietPlans.add(new DietPlan("Low Carb Diet", "Focuses on reducing carbohydrate intake"));
        dietPlans.add(new DietPlan("Mediterranean Diet", "Emphasizes eating fruits, vegetables, whole grains, and healthy fats"));
        dietPlans.add(new DietPlan("Vegetarian Diet", "Excludes meat and fish, but may include eggs and dairy products"));
        // Add more diet plans as needed
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.println("Membership added successfully!");
    }

    public void viewAllMembers() {
        System.out.println("\nShow Members:");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    public void searchMember(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                System.out.println("Member found:");
                System.out.println(member);
                return;
            }
        }
        System.out.println("Member not found.");
    }

    public void removeMember(String name) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getName().equalsIgnoreCase(name)) {
                members.remove(i);
                System.out.println("Membership Cancelled successfully!");
                return;
            }
        }
        System.out.println("Member not found.");
    }

    public void viewAvailableEquipment() {
        System.out.println("\nAvailable Equipment:");
        for (Equipment equip : equipment) {
            System.out.println(equip.getName() + ": " + equip.getDescription());
        }
    }

    public void viewAvailableTimeSlots() {
        System.out.println("\nAvailable Time Slots:");
        for (TimeSlot slot : timeSlots) {
            System.out.println(slot.getDayOfWeek() + " - " + slot.getStartTime() + " to " + slot.getEndTime());
        }
    }

    public void viewAvailableExercises() {
        System.out.println("\nAvailable Exercises:");
        for (Exercise exercise : exercises) {
            System.out.println(exercise.getName() + ": " + exercise.getDescription());
        }
    }

    public void viewSubscriptionPlans() {
        System.out.println("\nSubscription Plans:");
        for (SubscriptionPlan plan : subscriptionPlans) {
            System.out.println(plan.getName() + ": $" + plan.getCost());
        }
    }

    public void viewDietPlans() {
        System.out.println("\nDiet Plans:");
        for (DietPlan dietPlan : dietPlans) {
            System.out.println(dietPlan.getName() + ": " + dietPlan.getDescription());
        }
    }

    public void contactUs() {
        System.out.println("\nContact Us:");
        System.out.println("Phone: 727538292");
        System.out.println("Email: Cultfit@gym.com");
        System.out.println("Address: 123 Gym Street, Hyderabad, India");
    }

    public static void main(String[] args) {
        GymManagementSystem gym = new GymManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--------*------------*-----------------*-----------------*-------------*---------------*--------------------*----------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------Gym Management System Menu-----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1. Taking Membership");
            System.out.println("2. View Gym Members");
            System.out.println("3. Searching Members");
            System.out.println("4. Cancelling Membership");
            System.out.println("5. View Available Equipment");
            System.out.println("6. View Available Time Slots");
            System.out.println("7. View Available Exercises");
            System.out.println("8. View Subscription Plans");
            System.out.println("9. View Diet Plans");
            System.out.println("10. Contact Us");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter member age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter member phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter member address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter preferred equipment: ");
                    String equipmentPref = scanner.nextLine();
                    System.out.print("Enter preferred time slot: ");
                    String timeSlotPref = scanner.nextLine();
                    System.out.print("Enter subscription plan you would like to take: ");
                    String subscriptionPlan = scanner.nextLine();
                    System.out.print("Enter diet plan you would like: ");
                    String dietPlan = scanner.nextLine();

                    Member newMember = new Member(name, age, phoneNumber, address, equipmentPref, timeSlotPref, subscriptionPlan, dietPlan);
                    gym.addMember(newMember);
                    break;
                case 2:
                    gym.viewAllMembers();
                    break;
                case 3:
                    System.out.print("Enter member name to search: ");
                    String searchName = scanner.nextLine();
                    gym.searchMember(searchName);
                    break;
                case 4:
                    System.out.print("Enter member name to cancel membership: ");
                    String removeName = scanner.nextLine();
                    gym.removeMember(removeName);
                    break;
                case 5:
                    gym.viewAvailableEquipment();
                    break;
                case 6:
                    gym.viewAvailableTimeSlots();
                    break;
                case 7:
                    gym.viewAvailableExercises();
                    break;
                case 8:
                    gym.viewSubscriptionPlans();
                    break;
                case 9:
                    gym.viewDietPlans();
                    break;
                case 10:
                    gym.contactUs();
                    break;
                case 11:
                    System.out.println("Gym Management System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



