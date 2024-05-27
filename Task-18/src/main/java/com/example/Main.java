import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.text.DateFormat;


class TicketBookingSystem {
    private static final Map<String, String> registeredUsers = new HashMap<>();
    private static final Map<String, String> userTickets = new HashMap<>();
    private static final String EMAIL_FROM = "rahitya2062@gmail.com"; // Your Gmail address
    private static final String EMAIL_PASSWORD = "fsgx waec xksm wkzi"; // Your Gmail password

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicketBookingSystem::createAndShowWelcomeGUI);
    }

    private static void createAndShowWelcomeGUI() {
        JFrame frame = new JFrame("Ticket Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Increase size of the frame

        JPanel panel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Ticket Booking System");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            frame.dispose();
            createAndShowRegistrationGUI();
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            frame.dispose();
            createAndShowLoginGUI();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createAndShowRegistrationGUI() {
        JFrame frame = new JFrame("Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Increase size of the frame

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            String enteredPassword = new String(password);
            String email = emailField.getText();

            registeredUsers.put(username, enteredPassword);
            // Optionally, you can save the email in a map if you want to use it later
            // userEmails.put(username, email);

            JOptionPane.showMessageDialog(frame, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            createAndShowWelcomeGUI();
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel());
        panel.add(registerButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createAndShowLoginGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Increase size of the frame

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            String enteredPassword = new String(password);

            if (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(enteredPassword)) {
                JOptionPane.showMessageDialog(frame, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                createAndShowOptionsGUI(username);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createAndShowOptionsGUI(String username) {
        JFrame frame = new JFrame("Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Increase size of the frame

        JPanel panel = new JPanel(new GridLayout(1, 2));

        JButton bookTicketsButton = new JButton("Book Tickets");
        bookTicketsButton.addActionListener(e -> {
            frame.dispose();
            createAndShowTransportSelectionGUI(username);
        });

        panel.add(bookTicketsButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // When calling createAndShowTransportSelectionGUI method, make sure to pass the selected transport mode
    private static void createAndShowTransportSelectionGUI(String username) {
        JFrame frame = new JFrame("Transport Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Increase size of the frame

        JPanel panel = new JPanel(new BorderLayout());

        JLabel selectLabel = new JLabel("Select Mode of Transport:");
        JComboBox<String> transportComboBox = new JComboBox<>(new String[]{"Bus", "Train", "Airplane"});

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            String selectedTransport = (String) transportComboBox.getSelectedItem();
            frame.dispose();
            if ("Bus".equals(selectedTransport)) {
                createAndShowAvailableSeatsGUI(username, "Bus"); // Pass "Bus" as the transport mode
            } else if ("Train".equals(selectedTransport)) {
                createAndShowAvailableSeatsGUI(username, "Train"); // Pass "Train" as the transport mode
            } else if ("Airplane".equals(selectedTransport)) {
                createAndShowAvailableSeatsGUI(username, "Airplane"); // Pass "Airplane" as the transport mode
            }
        });

        panel.add(selectLabel, BorderLayout.NORTH);
        panel.add(transportComboBox, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static void createAndShowBusSelectionGUI(String username) {
        JFrame frame = new JFrame("Bus Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Increase size of the frame

        JPanel panel = new JPanel(new BorderLayout());

        JLabel selectLabel = new JLabel("Select Bus:");
        JComboBox<String> busComboBox = new JComboBox<>(new String[]{"Hyd - Ranchi 17:30 PM", "Banglore - kochi 15:45 AM", "Thiruvananthapuram - Delhi 22:00 AM"});

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            String selectedBus = (String) busComboBox.getSelectedItem();
            frame.dispose();
            createAndShowAvailableSeatsGUI(username, selectedBus);
        });

        panel.add(selectLabel, BorderLayout.NORTH);
        panel.add(busComboBox, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static void createAndShowTrainSelectionGUI(String username) {
        JFrame frame = new JFrame("Train Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Increase size of the frame

        JPanel panel = new JPanel(new BorderLayout());

        JLabel selectLabel = new JLabel("Select Train:");
        JComboBox<String> trainComboBox = new JComboBox<>(new String[]{"Hyd - Kochi  7:00 AM", "Delhi - Banglore 9:00 PM", "Kolkata - Pune 12:40 AM"});

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            String selectedTrain = (String) trainComboBox.getSelectedItem();
            frame.dispose();
            createAndShowAvailableSeatsGUI(username, selectedTrain);
        });

        panel.add(selectLabel, BorderLayout.NORTH);
        panel.add(trainComboBox, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createAndShowAirplaneSelectionGUI(String username) {
        JFrame frame = new JFrame("Airplane Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Increase size of the frame

        JPanel panel = new JPanel(new BorderLayout());

        JLabel selectLabel = new JLabel("Select Airplane:");
        JComboBox<String> airplaneComboBox = new JComboBox<>(new String[]{"Ranchi - Aurangabad 3:30 AM", "Jaipur - Chennai 5:00 PM", "Hyderabad - Kochi 6:00 PM"});

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            String selectedAirplane = (String) airplaneComboBox.getSelectedItem();
            frame.dispose();
            createAndShowAvailableSeatsGUI(username, selectedAirplane);
        });

        panel.add(selectLabel, BorderLayout.NORTH);
        panel.add(airplaneComboBox, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createAndShowAvailableSeatsGUI(String username, String transport) {
        JFrame frame = new JFrame(transport + " - Available Seats");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Increase size of the frame

        JPanel panel = new JPanel(new GridLayout(6, 6));

        JLabel dateLabel = new JLabel("Enter Date (YYYY-MM-DD): ");
        JTextField dateField = new JTextField();

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> {
            String[] bookedSeats = getUserTickets(username);
            int totalPrice = calculateTotalPrice(transport, bookedSeats.length);
            int result = JOptionPane.showConfirmDialog(frame, "Total Price: Rs." + totalPrice + "\nDo you want to proceed with the payment?", "Payment Confirmation", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Payment successful. Thank you for your purchase!");
                int rating = (int) JOptionPane.showInputDialog(frame, "Rate Your Experience (1-5 Stars):", "Rating", JOptionPane.PLAIN_MESSAGE, null, new Integer[]{1, 2, 3, 4, 5}, 3);
                if (rating > 0) {
                    String ticketInfo = transport + " - " + String.join(", ", bookedSeats) + " on " + dateField.getText();
                    sendEmail(username, ticketInfo); // Pass ticketInfo to sendEmail method
                    JOptionPane.showMessageDialog(frame, "Thank you for rating us!");
                    showBookingConfirmation(username, transport, bookedSeats);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please provide a rating.", "Rating Required", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        for (int i = 0; i < 30; i++) {
            JButton seatButton = new JButton("Seat " + (i + 1));
            seatButton.setBackground(Color.GRAY);
            int seatNo = i + 1;
            seatButton.addActionListener(e -> {
                if (isSeatAlreadyBooked(username, transport + " - Seat " + seatNo)) {
                    JOptionPane.showMessageDialog(frame, "Seat " + seatNo + " is already booked.", "Seat Already Booked", JOptionPane.WARNING_MESSAGE);
                } else {
                    seatButton.setBackground(Color.GREEN);
                    seatButton.setEnabled(false);
                }
            });
            panel.add(seatButton);
        }

        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(payButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static int calculateTotalPrice(String transport, int numberOfTickets) {
        int pricePerTicket;
        switch (transport) {
            case "Bus":
                pricePerTicket = 600;
                break;
            case "Train":
                pricePerTicket = 400;
                break;
            case "Airplane":
                pricePerTicket = 4000;
                break;
            default:
                pricePerTicket = 0; // Default price if transport type is unknown
        }
        return numberOfTickets * pricePerTicket;
    }


    private static String[] getUserTickets(String username) {
        String bookedSeats = userTickets.getOrDefault(username, "");
        return bookedSeats.split(",");
    }


    private static void showRatingPageAndProceed(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel ratingLabel = new JLabel("Rate Your Experience (1-5 Stars):");
        JComboBox<Integer> ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // You can process the rating here
            JOptionPane.showMessageDialog(frame, "Thank you for your rating!");
            frame.dispose();
            createAndShowLoginGUI();
        });

        panel.add(ratingLabel, BorderLayout.NORTH);
        panel.add(ratingComboBox, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.revalidate();
    }

    private static void showBookingConfirmation(String username, String transport, String[] bookedSeats) {
        String ticketInfo = String.join(", ", bookedSeats);
        sendEmail(username, ticketInfo);
        JOptionPane.showMessageDialog(null, "Tickets booked for: " + ticketInfo, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }


    private static void sendEmail(String username, String ticketInfo) {
        // Get recipient email from user registration details
        String recipientEmail = getUserEmail(username);

        // Set up properties for sending email via SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Get Session object with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Ticket Booking Confirmation");
            message.setText("Dear " + username + ",\n\nYour ticket has been successfully booked.\n\nTicket Details: " + ticketInfo + "\n\nThank you for using our Ticket Booking System.");

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private static String getUserEmail(String username) {
        // For simplicity, assuming that the email is the same as the username + "@example.com"
        return username + "@example.com";
    }

    private static boolean isSeatAlreadyBooked(String username, String seatInfo) {
        String bookedSeats = userTickets.getOrDefault(username, "");
        return bookedSeats.contains(seatInfo);
    }
}