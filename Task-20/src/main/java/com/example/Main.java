import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

class SchoolManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "mlpnko??6789$#";
    private static final String DATABASE_NAME = "school_management";
    private static String currentUser;

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createDatabase(connection);
            createTables(connection);
            Scanner scanner = new Scanner(System.in);

            int choice;
            do {
                System.out.println("School Management System");
                System.out.println("1. Admin");
                System.out.println("2. Faculty");
                System.out.println("3. Student");
                System.out.println("4. Parent");
                System.out.println("5. Exit");
                System.out.println("Choose user type:");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        adminActions(connection, scanner);
                        break;
                    case 2:
                        if (login(connection, scanner, "faculty")) {
                            facultyActions(connection, scanner);
                        }
                        break;
                    case 3:
                        if (login(connection, scanner, "student")) {
                            studentActions(connection, scanner);
                        }
                        break;
                    case 4:
                        if (login(connection, scanner, "parent")) {
                            parentActions(connection, scanner);
                        }
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);
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
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS queries (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "query_text TEXT NOT NULL" +
                    ")");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS leave_requests (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "leave_start DATE NOT NULL," +
                    "leave_end DATE NOT NULL" +
                    ")");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS notices (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "notice_text TEXT NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS reviews (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "review_text TEXT NOT NULL," +
                    "rating INT CHECK (rating >= 1 AND rating <= 5)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
        }
    }

    private static boolean login(Connection connection, Scanner scanner, String role) throws SQLException {
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
                currentUser = username;
                return true;
            } else {
                System.out.println("Login failed. Invalid username, password, or role.");
                return false;
            }
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
                    adminMenu(connection, scanner);
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
                return true;
            } else {
                System.out.println("Admin login failed. Invalid username, password, or role.");
                return false;
            }
        }
    }

    private static void adminMenu(Connection connection, Scanner scanner) throws SQLException {
        int choice;
        do {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Faculty");
            System.out.println("2. Add Student");
            System.out.println("3. Add Parent");
            System.out.println("4. View Queries");
            System.out.println("5. View Leave Requests");
            System.out.println("6. Add Notice");
            System.out.println("7. Logout");
            System.out.print("Choose action: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addFaculty(connection, scanner);
                    break;
                case 2:
                    addStudent(connection, scanner);
                    break;
                case 3:
                    addParent(connection, scanner);
                    break;
                case 4:
                    viewQueries(connection);
                    break;
                case 5:
                    viewLeaveRequests(connection);
                    break;
                case 6:
                    addNotice(connection, scanner);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private static void addFaculty(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username for faculty: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for faculty: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "faculty");
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Faculty added successfully.");
            } else {
                System.out.println("Failed to add faculty.");
            }
        }
    }

    private static void addStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username for student: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for student: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "student");
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        }
    }
    private static void addParent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username for parent: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for parent: ");
        String password = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "parent");
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Parent added successfully.");
            } else {
                System.out.println("Failed to add parent.");
            }
        }
    }

    private static void viewQueries(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM queries");
            System.out.println("Queries:");
            while (resultSet.next()) {
                System.out.println("User: " + resultSet.getString("username"));
                System.out.println("Query: " + resultSet.getString("query_text"));
                System.out.println("-----------------------");
            }
        }
    }

    private static void viewLeaveRequests(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM leave_requests");
            System.out.println("Leave Requests:");
            while (resultSet.next()) {
                System.out.println("User: " + resultSet.getString("username"));
                System.out.println("Leave Start Date: " + resultSet.getDate("leave_start"));
                System.out.println("Leave End Date: " + resultSet.getDate("leave_end"));
                System.out.println("-----------------------");
            }
        }
    }
    private static void addNotice(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter notice text: ");
        String noticeText = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO notices (notice_text) VALUES (?)")) {
            statement.setString(1, noticeText);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Notice added successfully.");
            } else {
                System.out.println("Failed to add notice.");
            }
        }
    }

    private static void facultyActions(Connection connection, Scanner scanner) throws SQLException {
        int choice;
        do {
            System.out.println("Faculty Menu:");
            System.out.println("1. Home");
            System.out.println("2. About Us");
            System.out.println("3. Contact Us");
            System.out.println("4. Raise Query");
            System.out.println("5. View Student Queries");
            System.out.println("6. Mark Student Attendance");
            System.out.println("7. Apply Leave");
            System.out.println("8. Look at My Timetable");
            System.out.println("9. Exit to Main Menu");
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
                    raiseQuery(connection, scanner);
                    break;
                case 5:
                    viewStudentQueries(connection);
                    break;
                case 6:
                    markStudentAttendance(connection, scanner);
                    break;
                case 7:
                    applyLeave(connection, scanner);
                    break;
                case 8:
                    lookAtTimetable();
                    break;
                case 9:
                    System.out.println("Exiting to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    private static void viewStudentQueries(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM queries");
            System.out.println("Student Queries:");
            while (resultSet.next()) {
                System.out.println("User: " + resultSet.getString("username"));
                System.out.println("Query: " + resultSet.getString("query_text"));
                System.out.println("-----------------------");
            }
        }
    }

    private static void markStudentAttendance(Connection connection, Scanner scanner) throws SQLException {
        // Display a list of students to choose from
        System.out.println("Select a student to mark attendance:");
        displayStudentList(connection);

        // Prompt for user input to select a student
        System.out.print("Enter the student's username: ");
        String studentUsername = scanner.nextLine();

        // Get the current date and time
        LocalDateTime currentTime = LocalDateTime.now();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO attendance (username, attendance_time) VALUES (?, ?)")) {
            statement.setString(1, studentUsername);
            statement.setTimestamp(2, Timestamp.valueOf(currentTime));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Attendance for student " + studentUsername + " marked successfully.");
            } else {
                System.out.println("Failed to mark attendance for student " + studentUsername + ".");
            }
        }
    }

    // Method to display a list of students
    private static void displayStudentList(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT username FROM users WHERE role = 'student'");
            System.out.println("Students:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        }
    }


    private static void raiseQuery(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter your query: ");
        String queryText = scanner.nextLine();
        System.out.println("Query Raised: " + queryText);

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO queries (username, query_text) VALUES (?, ?)")) {
            statement.setString(1, getUsername());
            statement.setString(2, queryText);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Query submitted successfully.");
            } else {
                System.out.println("Failed to submit query.");
            }
        }
    }

    private static void applyLeave(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter leave start date (YYYY-MM-DD): ");
        String leaveStart = scanner.nextLine();
        System.out.print("Enter leave end date (YYYY-MM-DD): ");
        String leaveEnd = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO leave_requests (username, leave_start, leave_end) VALUES (?, ?, ?)")) {
            statement.setString(1, getUsername());
            statement.setString(2, leaveStart);
            statement.setString(3, leaveEnd);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Leave request submitted successfully.");
            } else {
                System.out.println("Failed to submit leave request.");
            }
        }
    }

    private static String getUsername() {
        return currentUser;
    }

    private static void displayHome() {
        System.out.println("Welcome to the Home Page!");
        System.out.println("School Name: Numetry School");
        System.out.println("Founded in: 2000");
        System.out.println("Location: Delhi, INDIA");
        System.out.println("Type: Private");
    }

    private static void displayContactUs() {
        // Display text in the console
        System.out.println("Contact Us:");
        System.out.println("Email: numetry_school@example.com");
        System.out.println("Phone: 91-123-456-7890");
      System.out.println("Address: Church Street, Delhi, INDIA");

        // Display an image using Swing
        showImage();
    }


    private static void showImage() {
        // Load the image
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Users\\pc\\OneDrive\\Desktop\\download1.jpg")); // Adjust the path accordingly
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Scale the image to fit the dialog box
        int dialogWidth = 400; // Set the desired width of the dialog
        int dialogHeight = 300; // Set the desired height of the dialog
        Image scaledImg = img.getScaledInstance(dialogWidth, dialogHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImg);

        // Display the scaled image in a JOptionPane
        JLabel imageLabel = new JLabel(imageIcon);
        JOptionPane.showMessageDialog(null, imageLabel, "Contact Us - Image", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void displayAboutUs() {
        // Display text in the console
       System.out.println("About Us:");
        System.out.println("Numetry School is a premier educational institution...");

        // Display an image using Swing
        showImagea();
    }


    private static void showImagea() {
        // Load the image
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Users\\pc\\OneDrive\\Desktop\\download3.jpg")); // Adjust the path accordingly
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Scale the image to fit the dialog box
        int dialogWidth = 400; // Set the desired width of the dialog
        int dialogHeight = 300; // Set the desired height of the dialog
        Image scaledImg = img.getScaledInstance(dialogWidth, dialogHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImg);

        // Display the scaled image in a JOptionPane
        JLabel imageLabel = new JLabel(imageIcon);
        JOptionPane.showMessageDialog(null, imageLabel, "About Us - Image", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void lookAtTimetable() {
        System.out.println("Timetable for user: faculty_user");

        System.out.println("Monday - 9:00-10:00: Mathematics");
        System.out.println("Monday - 10:00-11:00: Physics");
        System.out.println("Tuesday - 9:00-10:00: Chemistry");
        System.out.println("Tuesday - 10:00-11:00: Biology");
        System.out.println("Wednesday - 9:00-10:00: English");
        System.out.println("Wednesday - 10:00-11:00: History");
    }

    private static void studentActions(Connection connection, Scanner scanner) throws SQLException {
        int choice;
        do {
            System.out.println("Student Menu:");
            System.out.println("1. Mark Attendance");
            System.out.println("2. Look at My Fee Structure");
            System.out.println("3. Raise a Query");
            System.out.println("4. Home");
            System.out.println("5. About Us");
            System.out.println("6. Contact Us");
            System.out.println("7. Exit to Main Menu");
            System.out.print("Choose action: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    markAttendance(connection);
                    break;
                case 2:
                    displayFeeStructure();
                    break;
                case 3:
                    raiseQuery(connection, scanner);
                    break;
                case 4:
                    displayHome();
                    break;
                case 5:
                    displayAboutUs();
                    break;
                case 6:
                    displayContactUs();
                    break;
                case 7:
                    System.out.println("Exiting to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private static void markAttendance(Connection connection) throws SQLException {
        // Get the current date and time
        LocalDateTime currentTime = LocalDateTime.now();

        // Get the current student's username
        String username = getUsername();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO attendance (username, attendance_time) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setTimestamp(2, Timestamp.valueOf(currentTime));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Attendance marked successfully.");
            } else {
                System.out.println("Failed to mark attendance.");
            }
        }
    }

    private static void displayFeeStructure() {
        System.out.println("Select your course to view the fee structure:");
        System.out.println("1. MBA");
        System.out.println("2. B.Tech");
        System.out.println("3. MCA");
        System.out.println("4. BCA");
        System.out.println("5. M.Tech");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Fee Structure for MBA:");
                System.out.println("Tuition: Rs 150000");
                System.out.println("Books: Rs 300");
                System.out.println("Total: Rs 180000");
                break;
            case 2:
                System.out.println("Fee Structure for B.Tech:");
                System.out.println("Tuition: Rs 200000");
                System.out.println("Books: Rs 400");
                System.out.println("Total: Rs 240000");
                break;
            case 3:
                System.out.println("Fee Structure for MCA:");
                System.out.println("Tuition: Rs 180000");
                System.out.println("Books: Rs 350");
                System.out.println("Total: Rs 215000");
                break;
            case 4:
                System.out.println("Fee Structure for BCA:");
                System.out.println("Tuition: Rs 180000");
                System.out.println("Books: Rs 350");
                System.out.println("Total: Rs 215000");
                break;
            case 5:
                System.out.println("Fee Structure for M.Tech:");
                System.out.println("Tuition: Rs 22000");
                System.out.println("Books: Rs 4000");
                System.out.println("Total: Rs 26000");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
    private static void parentActions(Connection connection, Scanner scanner) throws SQLException {
        int choice;
        do {
            System.out.println("Parent Menu:");
            System.out.println("1. Home");
            System.out.println("2. About Us");
            System.out.println("3. Contact Us");
            System.out.println("4. Faculty Details");
            System.out.println("5. Notices");
            System.out.println("6. Awards & Certifications");
            System.out.println("7. Careers");
            System.out.println("8. Support");
            System.out.println("9. Rating & review");
            System.out.println("10. Logout");
            System.out.print("Choose action: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    System.out.println("Welcome to the Home Page!");
                    System.out.println("School Name: Numetry School");
                    System.out.println("Founded in: 2000");
                    System.out.println("Location: Delhi, INDIA");
                    System.out.println("Type: Private");
                    break;
                case 2:

                    System.out.println("About Us:");
                    System.out.println("Numetry School is a premier educational institution...");
                    break;
                case 3:

                    System.out.println("Contact Us:");
                    System.out.println("Email: numetry_school@example.com");
                    System.out.println("Phone: 91-123-456-7890");
                    System.out.println("Address: chruch Street, Delhi, INDIA");
                    break;
                case 4:
                    viewFacultyDetails(connection);
                    break;
                case 5:
                    viewNotices(connection);
                    break;
                case 6:

                    System.out.println("Achievements and Awards:");
                    System.out.println("1. Awarded 'Best Emerging Engineering College in India' - 2023");
                    System.out.println("2. Ranked in the top 10 for 'Best MBA Programs' by EduWorld Rankings - 2022");
                    System.out.println("3. Received 'Excellence in Innovation in Higher Education' from the Global Education Summit - 2021");
                    System.out.println("4. Recognized for 'Outstanding Contribution to IT Education' by TechAcademia - 2020");
                    System.out.println("5. Winner of the 'Green Campus Initiative' award by the Environmental Education Association - 2019");
                    System.out.println("6. 'Best Placement Record' in the region, with a 95% placement rate for the class of 2023");
                    System.out.println("7. Honored with the 'Academic Excellence Award' by the National Education Board - 2022");
                    System.out.println("8. Secured 'Top Research Institution' status by the International Science Association - 2021");
                    System.out.println("9. 'Best Infrastructure Development' award from the Indian Construction and Design Council - 2020");
                    System.out.println("10. Winner of the 'Innovative Curriculum Design' award at the Global Education Conference - 2019");
                    System.out.println("11. Awarded 'Excellence in Online Education' by the Digital Learning Forum - 2022");
                    System.out.println("12. Recognized as a 'Leading Institution for Industry Collaboration' by the Corporate Connect Awards - 2021");
                    System.out.println("13. 'Best Student Support Services' award from the Higher Education Services Association - 2020");

                    break;
                case 7:

                    System.out.println("Career Opportunities:");
                    System.out.println("At Numetry College, we believe in fostering talent and providing opportunities for growth and development. Our institution is a great place to build a rewarding career in academia and administration.");
                    System.out.println();
                    System.out.println("1. Faculty Positions:");
                    System.out.println("   - Professors, Associate Professors, and Assistant Professors in MBA, MTech, BTech, BCA, MCA");
                    System.out.println("   - Responsibilities include teaching, research, and mentoring students");
                    System.out.println("   - Requirements: Ph.D. or equivalent in the relevant field, with a strong publication record");
                    System.out.println();
                    System.out.println("2. Administrative Roles:");
                    System.out.println("   - Positions in Admissions, Student Affairs, Human Resources, Finance, and more");
                    System.out.println("   - Responsibilities include managing departmental functions and supporting student and faculty needs");
                    System.out.println("   - Requirements: Relevant experience and qualifications as per the specific role");
                    System.out.println();
                    System.out.println("3. Research Opportunities:");
                    System.out.println("   - Post-Doctoral Fellows and Research Assistants in various departments");
                    System.out.println("   - Responsibilities include conducting research, publishing papers, and collaborating on projects");
                    System.out.println("   - Requirements: Ph.D. or Master’s degree in a relevant field, with research experience");
                    System.out.println();
                    System.out.println("Why Work at Numetry College?");
                    System.out.println("   - Collaborative and inclusive work environment");
                    System.out.println("   - Opportunities for professional development and advancement");
                    System.out.println("   - State-of-the-art facilities and resources");
                    System.out.println("   - Competitive salaries and benefits packages");
                    System.out.println();
                    System.out.println("How to Apply:");
                    System.out.println("   - Visit our careers page on the official Numetry College website");
                    System.out.println("   - Submit your application, including your resume and cover letter, via email to careers@numetry_school.com");
                    System.out.println("   - For faculty positions, include a statement of teaching philosophy and research interests");
                    System.out.println();
                    System.out.println("Join us at Numetry College and contribute to shaping the future of education!");
                    break;
                case 8:
                    System.out.println("Support Services:");
                    System.out.println("Numetry College is committed to providing comprehensive support services to ensure the success and well-being of our students, faculty, and staff.");
                    System.out.println();
                    System.out.println("1. Academic Support:");
                    System.out.println("   - Tutoring and mentoring programs for students");
                    System.out.println("   - Access to extensive online and offline resources, including libraries and research databases");
                    System.out.println("   - Academic advising and career counseling services");
                    System.out.println();
                    System.out.println("2. IT Support:");
                    System.out.println("   - 24/7 technical support for all campus systems and software");
                    System.out.println("   - Assistance with online learning platforms and digital resources");
                    System.out.println("   - Workshops and training sessions for faculty and students on new technologies");
                    System.out.println();
                    System.out.println("3. Health and Wellness:");
                    System.out.println("   - On-campus health center with medical and counseling services");
                    System.out.println("   - Wellness programs and fitness facilities to promote a healthy lifestyle");
                    System.out.println("   - Mental health support, including stress management workshops and peer support groups");
                    System.out.println();
                    System.out.println("4. Financial Aid and Scholarships:");
                    System.out.println("   - Guidance on applying for financial aid, scholarships, and grants");
                    System.out.println("   - Financial planning and budgeting assistance for students and families");
                    System.out.println("   - Information sessions and workshops on managing student loans and finances");
                    System.out.println();
                    System.out.println("5. Accessibility Services:");
                    System.out.println("   - Dedicated services for students with disabilities to ensure equal access to all programs and facilities");
                    System.out.println("   - Accommodation planning and support for academic and campus life");
                    System.out.println("   - Resources and training for faculty and staff on inclusive practices");
                    break;
                case 9:
                    submitReview(connection, scanner);
                    break;
                case 10:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 10);
    }
    private static void submitReview(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter your review: ");
        String reviewText = scanner.nextLine();
        System.out.print("Enter your rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reviews (username, review_text, rating) VALUES (?, ?, ?)")) {
            statement.setString(1, getUsername());
            statement.setString(2, reviewText);
            statement.setInt(3, rating);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Review and rating submitted successfully.");
            } else {
                System.out.println("Failed to submit review and rating.");
            }
        }
    }
    private static void viewNotices(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM notices ORDER BY created_at DESC");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String noticeText = resultSet.getString("notice_text");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                System.out.println("Notice ID: " + id + ", Notice: " + noticeText + ", Created At: " + createdAt);
            }
        }
    }
    private static void viewFacultyDetails(Connection scanner) {
        System.out.println("B.Tech Faculty:");
        System.out.println("Name: Dr. John Doe, Subject: Computer Science, Designation: Professor");
        System.out.println("Name: Dr. Jane Smith, Subject: Electrical Engineering, Designation: Associate Professor");
        System.out.println("Name: Dr. Emily Davis, Subject: Mechanical Engineering, Designation: Assistant Professor");
        System.out.println("MBA Faculty:");
        System.out.println("Name: Dr. Robert Brown, Subject: Finance, Designation: Professor");
        System.out.println("Name: Dr. Linda Wilson, Subject: Marketing, Designation: Associate Professor");
        System.out.println("Name: Dr. Michael Johnson, Subject: Human Resources, Designation: Assistant Professor");
        System.out.println("MCA Faculty:");
        System.out.println("Name: Dr. Michael Brown, Subject: Computer Applications, Designation: Professor");
        System.out.println("Name: Dr. Sarah Johnson, Subject: Database Management, Designation: Associate Professor");
        System.out.println("Name: Dr. Emily Wilson, Subject: Web Development, Designation: Assistant Professor");
        System.out.println("BCA Faculty:");
        System.out.println("Name: Dr. Robert Clark, Subject: Computer Fundamentals, Designation: Professor");
        System.out.println("Name: Dr. Jennifer Lee, Subject: Programming in Java, Designation: Associate Professor");
        System.out.println("Name: Dr. James Taylor, Subject: Software Engineering, Designation: Assistant Professor");
        System.out.println("M.Tech Faculty:");
        System.out.println("Name: Dr. Richard Green, Subject: Artificial Intelligence, Designation: Professor");
        System.out.println("Name: Dr. Mary White, Subject: Data Science, Designation: Associate Professor");
        System.out.println("Name: Dr. David Johnson, Subject: Cybersecurity, Designation: Assistant Professor");

    }



}