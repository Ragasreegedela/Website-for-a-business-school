import java.sql.*;
import java.util.Scanner;

class CompanyManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "mlpnko??6789$#";
    private static final String DATABASE_NAME = "company_management";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createDatabase(connection);
            createTables(connection);
            Scanner scanner = new Scanner(System.in);

            int choice;
            do {
                System.out.println("Company Management System");
                System.out.println("1. Admin");
                System.out.println("2. Manager");
                System.out.println("3. Employee");
                System.out.println("4. Exit");
                System.out.println("Choose user type:");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        adminActions(connection, scanner);
                        break;
                    case 2:
                    case 3:
                        userLogin(connection, scanner, choice);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase(Connection connection) throws SQLException {
        String createDbQuery = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createDbQuery);
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String useDbQuery = "USE " + DATABASE_NAME;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(useDbQuery);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(50) NOT NULL," +
                    "role VARCHAR(20)" +
                    ")");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS attendance (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "attendance_time TIMESTAMP NOT NULL" +
                    ")");
        }
    }

    private static void adminActions(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("Choose action:");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                registerAdmin(connection, scanner);
                break;
            case 2:
                if (loginAdmin(connection, scanner)) {
                    System.out.println("Logged in as admin.");
                    System.out.println("Choose action:");
                    System.out.println("1. Add Employee");
                    System.out.println("2. Add Manager");
                    int adminAction = scanner.nextInt();
                    scanner.nextLine();
                    switch (adminAction) {
                        case 1:
                            addEmployee(connection, scanner);
                            break;
                        case 2:
                            addManager(connection, scanner);
                            break;
                        default:
                            System.out.println("Invalid action.");
                    }
                }
                break;
            default:
                System.out.println("Invalid action.");
        }
    }

    private static void registerAdmin(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "admin");
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Admin registered successfully.");
            } else {
                System.out.println("Failed to register admin.");
            }
        }
    }

    private static boolean loginAdmin(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ? AND role = 'admin'")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Admin login successful.");
                return true;
            } else {
                System.out.println("Admin login failed. Invalid username, password, or role.");
                return false;
            }
        }
    }

    private static void addEmployee(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username for employee: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for employee: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "employee");
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Employee added successfully.");
            } else {
                System.out.println("Failed to add employee.");
            }
        }
    }

    private static void addManager(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username for manager: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for manager: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "manager");
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Manager added successfully.");
            } else {
                System.out.println("Failed to add manager.");
            }
        }
    }

    private static void userLogin(Connection connection, Scanner scanner, int userType) throws SQLException {
        String role = (userType == 2) ? "manager" : "employee";
        System.out.println("1. Login");
        System.out.println("2. Forgot Password");
        System.out.print("Choose action: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                loginUser(connection, scanner, role);
                break;
            case 2:
                forgotPassword(connection, scanner, role);
                break;
            default:
                System.out.println("Invalid action.");
        }
    }

    private static void loginUser(Connection connection, Scanner scanner, String role) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println(role + " login successful.");
                if (role.equals("employee")) {
                    employeeActions(connection, scanner, username);
                } else if (role.equals("manager")) {
                    managerActions(scanner);
                }
                return;
            } else {
                System.out.println(role + " login failed. Invalid username, password, or role.");
            }
        }
    }

    private static void forgotPassword(Connection connection, Scanner scanner, String role) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        // Check if the username exists in the database
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND role = ?")) {
            statement.setString(1, username);
            statement.setString(2, role);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Username exists, proceed with password reset
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();

                try (PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE users SET password = ? WHERE username = ? AND role = ?")) {
                    updateStatement.setString(1, newPassword);
                    updateStatement.setString(2, username);
                    updateStatement.setString(3, role);
                    int rowsUpdated = updateStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Password reset successful.");
                    } else {
                        System.out.println("Failed to reset password.");
                    }
                }
            } else {
                System.out.println("Username not found.");
            }
        }
    }

    private static void employeeActions(Connection connection, Scanner scanner, String username) throws SQLException {
        int choice;
        do {
            System.out.println("Employee Actions:");
            System.out.println("1. Mark Attendance");
            System.out.println("2. Home");
            System.out.println("3. About Us");
            System.out.println("4. Contact Us");
            System.out.println("5. Exit and Return to Main Menu");
            System.out.print("Choose action: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    markAttendance(connection, scanner, username);
                    break;
                case 2:
                    displayHome();
                    break;
                case 3:
                    displayAboutUs();
                    break;
                case 4:
                    displayContactUs();
                    break;
                case 5:
                    System.out.println("Exiting to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void markAttendance(Connection connection, Scanner scanner, String username) throws SQLException {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        int hour = date.getHours();

        if (hour >= 9 && hour < 17) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO attendance (username, attendance_time) VALUES (?, ?)")) {
                statement.setString(1, username);
                statement.setTimestamp(2, timestamp);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Attendance marked successfully.");
                } else {
                    System.out.println("Failed to mark attendance.");
                }
            }
        } else {
            System.out.println("Attendance can only be marked between 9:00 AM and 5:00 PM.");
        }
    }

    private static void displayHome() {
        System.out.println("Welcome to the Home Page!");
        System.out.println("Company Name: PQR Corporation");
        System.out.println("Founded in: 2020");
        System.out.println("Location: Delhi, INDIA");
        System.out.println("Industry: Technology");
    }

    private static void displayAboutUs() {
        System.out.println("About Us:");
        System.out.println("PQR Corporation is a leading technology company...");
    }

    private static void displayContactUs() {
        System.out.println("Contact Us:");
        System.out.println("Email: ragas3162@gmail.com");
        System.out.println("Phone: 91-123-456-7890");
        System.out.println("Address: 123 Chruch street, Delhi, INDIA");
    }

    private static void managerActions(Scanner scanner) {
        int choice;
        do {
            System.out.println("Manager Actions:");
            System.out.println("1. Home");
            System.out.println("2. About Us");
            System.out.println("3. Contact Us");
            System.out.println("4. Exit and Return to Main Menu");
            System.out.print("Choose action: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayHome();
                    break;
                case 2:
                    displayAboutUs();
                    break;
                case 3:
                    displayContactUs();
                    break;
                case 4:
                    System.out.println("Exiting to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }
}
