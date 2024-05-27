package com.example;

import java.sql.*;
import java.util.Scanner;

class TrainReservationSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "mlpnko??6789$#";
    private static final String DATABASE_NAME = "train_reservation";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createDatabase(connection);
            createTables(connection);
            System.out.println("Database and tables created successfully.");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Train Reservation System");

            int choice;

            do {
                System.out.println("\n1. Mention User");
                System.out.println("2. Mention Train");
                System.out.println("3. Booking Train");
                System.out.println("4. Check Tickets");
                System.out.println("5. Display Available Trains");
                System.out.println("6. Exit");
                System.out.println("Enter your choice:");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        insertUser(connection, scanner);
                        break;
                    case 2:
                        insertTrain(connection, scanner);
                        break;
                    case 3:
                        bookTrain(connection, scanner);
                        break;
                    case 4:
                        checkTicket(connection, scanner);
                        break;
                    case 5:
                        displayAvailableTrains(connection);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 6);

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
    private static void insertUser(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        String insertQuery = "INSERT INTO users (username, password, email, full_name) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, fullName);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User inserted successfully.");
            } else {
                System.out.println("Failed to insert user.");
            }
        }
    }

    private static void insertTrain(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter train name:");
        String trainName = scanner.nextLine();

        System.out.println("Enter train number:");
        String trainNumber = scanner.nextLine();

        System.out.println("Enter starting station:");
        String startStation = scanner.nextLine();

        System.out.println("Enter destination:");
        String destination = scanner.nextLine();

        String insertQuery = "INSERT INTO trains (train_name, train_number, start_station, destination_station, total_seats) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, trainName);
            statement.setString(2, trainNumber);
            statement.setString(3, startStation);
            statement.setString(4, destination);
            statement.setInt(5, 100); // Assuming total seats for all trains is 100 initially
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Train inserted successfully.");
            } else {
                System.out.println("Failed to insert train.");
            }
        }
    }

    private static void displayAvailableTrains(Connection connection) throws SQLException {
        String query = "SELECT * FROM trains";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("Available Trains:");
            System.out.println("ID\tTrain Name\tTrain Number\tStart Station\tDestination\tTotal Seats");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String trainName = resultSet.getString("train_name");
                String trainNumber = resultSet.getString("train_number");
                String startStation = resultSet.getString("start_station");
                String destination = resultSet.getString("destination_station");
                int totalSeats = resultSet.getInt("total_seats");
                System.out.println(id + "\t" + trainName + "\t" + trainNumber + "\t" + startStation + "\t" + destination + "\t" + totalSeats);
            }
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String useDbQuery = "USE " + DATABASE_NAME;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(useDbQuery);

            String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(50) NOT NULL," +
                    "email VARCHAR(100)," +
                    "full_name VARCHAR(100)" +
                    ")";
            statement.executeUpdate(createUsersTableQuery);

            String createTrainsTableQuery = "CREATE TABLE IF NOT EXISTS trains (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "train_number VARCHAR(20) NOT NULL," +
                    "train_name VARCHAR(100) NOT NULL," +
                    "start_station VARCHAR(100) NOT NULL," +
                    "destination_station VARCHAR(100) NOT NULL," +
                    "total_seats INT NOT NULL" +
                    ")";
            statement.executeUpdate(createTrainsTableQuery);


            String createBookingsTableQuery = "CREATE TABLE IF NOT EXISTS bookings (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "train_id INT," +
                    "booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "payment_status BOOLEAN," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (train_id) REFERENCES trains(id)" +
                    ")";
            statement.executeUpdate(createBookingsTableQuery);
        }
    }

    private static boolean authenticateUser(Connection connection, String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    private static boolean checkTrainAvailability(Connection connection, String trainName) throws SQLException {
        String query = "SELECT * FROM trains WHERE train_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, trainName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    private static boolean bookTicket(Connection connection, String username, String trainName) throws SQLException {
        String insertQuery = "INSERT INTO bookings (user_id, train_id, payment_status) " +
                "SELECT u.id, t.id, ? FROM users u, trains t WHERE u.username = ? AND t.train_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setBoolean(1, true);
            statement.setString(2, username);
            statement.setString(3, trainName);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
    }
    private static void bookTrain(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        boolean isAuthenticated = authenticateUser(connection, username);
        if (!isAuthenticated) {
            System.out.println("User authentication failed. Booking aborted.");
            return;
        }

        System.out.println("Enter train name:");
        String trainName = scanner.nextLine();

        boolean isTrainAvailable = checkTrainAvailability(connection, trainName);
        if (!isTrainAvailable) {
            System.out.println("Train not available. Booking aborted.");
            return;
        }

        System.out.println("Enter payment amount:");
        double amount = scanner.nextDouble();

        Payment payment = new Payment() {
            @Override
            public boolean processPayment(double amount) {
                System.out.println("Payment processed successfully: INR" + amount);
                return true;
            }
        };

        if (payment.processPayment(amount)) {
            boolean isBookingSuccessful = bookTicket(connection, username, trainName);
            if (isBookingSuccessful) {
                System.out.println("Booking successful!");
            } else {
                System.out.println("Booking failed. Please try again.");
            }
        } else {
            System.out.println("Payment failed. Please try again.");
        }
    }

    private static void checkTicket(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        boolean isAuthenticated = authenticateUser(connection, username);
        if (!isAuthenticated) {
            System.out.println("User authentication failed. Ticket check aborted.");
            return;
        }

        System.out.println("Enter train name:");
        String trainName = scanner.nextLine();

        String query = "SELECT * FROM bookings WHERE user_id = (SELECT id FROM users WHERE username = ?) " +
                "AND train_id = (SELECT id FROM trains WHERE train_name = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, trainName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Ticket found for user " + username + " on train " + trainName);
            } else {
                System.out.println("No ticket found for user " + username + " on train " + trainName);
            }
        }
    }


    interface Payment {
        boolean processPayment(double amount);
    }
}
