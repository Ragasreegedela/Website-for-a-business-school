import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

class CalendarProgramGUI {
    private static boolean isAdmin = false;
    private static HashMap<String, ArrayList<String>> reminders = new HashMap<>();
    private static HashMap<String, ArrayList<String>> leaveApplications = new HashMap<>();
    private static HashMap<String, String> employeeDatabase = new HashMap<>();
    private static String currentEmployee;

    public static void main(String[] args) {
        // Initialize employee database
        employeeDatabase.put("Raaga", "2062");
        employeeDatabase.put("akhil", "1234");
        employeeDatabase.put("vijay", "2345");

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Calendar Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton adminButton = new JButton("Admin Login");
        JButton employeeButton = new JButton("Employee Login");

        panel.add(new JLabel("Welcome to the Calendar Program"));
        panel.add(adminButton);
        panel.add(employeeButton);

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdmin = true;
                frame.getContentPane().removeAll();
                frame.repaint();
                createLoginGUI(frame);
            }
        });

        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdmin = false;
                frame.getContentPane().removeAll();
                frame.repaint();
                createLoginGUI(frame);
            }
        });
    }

    private static void createLoginGUI(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (isAdmin) {
                    if (username.equals("admin") && password.equals("admin123")) {
                        frame.getContentPane().removeAll();
                        frame.repaint();
                        createAdminGUI(frame);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (employeeDatabase.containsKey(username) && employeeDatabase.get(username).equals(password)) {
                        currentEmployee = username;
                        frame.getContentPane().removeAll();
                        frame.repaint();
                        createEmployeeGUI(frame);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private static void createAdminGUI(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton setReminderButton = new JButton("Set Reminder");
        JButton checkLeaveUpdatesButton = new JButton("Check Employee Leave Updates");
        JButton addEmployeeButton = new JButton("Add Employee");
        JButton logoutButton = new JButton("Logout");

        panel.add(setReminderButton);
        panel.add(checkLeaveUpdatesButton);
        panel.add(addEmployeeButton);
        panel.add(logoutButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();

        setReminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setReminder(frame);
            }
        });

        checkLeaveUpdatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLeaveUpdates(frame);
            }
        });

        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee(frame);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                createAndShowGUI();
            }
        });
    }

    private static void createEmployeeGUI(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton viewRemindersButton = new JButton("View Reminders");
        JButton applyForLeaveButton = new JButton("Apply for Leave");
        JButton logoutButton = new JButton("Logout");

        panel.add(viewRemindersButton);
        panel.add(applyForLeaveButton);
        panel.add(logoutButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();

        viewRemindersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewReminders(frame);
            }
        });

        applyForLeaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyForLeave(frame);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                createAndShowGUI();
            }
        });
    }

    private static void addEmployee(JFrame frame) {
        JTextField nameField = new JTextField(10);
        JTextField passwordField = new JTextField(10);
        JTextField emailField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Employee",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            employeeDatabase.put(name, password);

            // Send email to the new employee
            String subject = "Welcome to the Company!";
            String message = "Dear " + name + ",\n\nWelcome to the company! Your login credentials are:\nUsername: " + name + "\nPassword: " + password + "\n\nBest regards,\nThe Management";
            sendEmail(email, subject, message);

            JOptionPane.showMessageDialog(frame, "Employee added successfully! An email has been sent to the new employee.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void setReminder(JFrame frame) {
        JTextField monthField = new JTextField(5);
        JTextField yearField = new JTextField(5);
        JTextField dayField = new JTextField(5);
        JTextField messageField = new JTextField(20);
        JTextField durationField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);
        panel.add(new JLabel("Message:"));
        panel.add(messageField);
        panel.add(new JLabel("Duration (minutes):"));
        panel.add(durationField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Set Reminder",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int month = Integer.parseInt(monthField.getText());
                int year = Integer.parseInt(yearField.getText());
                int day = Integer.parseInt(dayField.getText());

                if (month < 1 || month > 12 || year < 0 || day < 1 || day > 31) {
                    JOptionPane.showMessageDialog(frame, "Invalid date!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String message = messageField.getText();
                int duration = Integer.parseInt(durationField.getText());

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month - 1);
                cal.set(Calendar.DAY_OF_MONTH, day);

                String reminderKey = day + "/" + (month - 1) + "/" + year;
                if (!reminders.containsKey(reminderKey)) {
                    reminders.put(reminderKey, new ArrayList<>());
                }
                reminders.get(reminderKey).add(message);

                JOptionPane.showMessageDialog(frame, "Reminder set for " + day + "/" + month + "/" + year + ": " + message, "Success", JOptionPane.INFORMATION_MESSAGE);

                // Remove reminder after specified duration
                Timer timer = new Timer(duration * 60000, new ActionListener() { // Convert minutes to milliseconds
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reminders.get(reminderKey).remove(message);
                        JOptionPane.showMessageDialog(frame, "Reminder removed for " + day + "/" + month + "/" + year, "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                timer.setRepeats(false); // Set to false to only execute once
                timer.start();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void checkLeaveUpdates(JFrame frame) {
        JTextField monthField = new JTextField(5);
        JTextField yearField = new JTextField(5);
        JTextField dayField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Check Leave Updates",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int month = Integer.parseInt(monthField.getText());
                int year = Integer.parseInt(yearField.getText());
                int day = Integer.parseInt(dayField.getText());

                if (month < 1 || month > 12 || year < 0 || day < 1 || day > 31) {
                    JOptionPane.showMessageDialog(frame, "Invalid date!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String leaveKey = day + "/" + (month - 1) + "/" + year;
                if (leaveApplications.containsKey(leaveKey)) {
                    ArrayList<String> employees = leaveApplications.get(leaveKey);
                    StringBuilder sb = new StringBuilder();
                    for (String employee : employees) {
                        sb.append("Employee ").append(employee).append(" applied for leave.\n");
                    }
                    JOptionPane.showMessageDialog(frame, sb.toString(), "Leave Updates",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No leave updates for this day.",
                            "Leave Updates", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void viewReminders(JFrame frame) {
        JTextField monthField = new JTextField(5);
        JTextField yearField = new JTextField(5);
        JTextField dayField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);

        int result = JOptionPane.showConfirmDialog(null, panel, "View Reminders",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int month = Integer.parseInt(monthField.getText());
                int year = Integer.parseInt(yearField.getText());
                int day = Integer.parseInt(dayField.getText());

                if (month < 1 || month > 12 || year < 0 || day < 1 || day > 31) {
                    JOptionPane.showMessageDialog(frame, "Invalid date!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String reminderKey = day + "/" + (month - 1) + "/" + year;
                if (reminders.containsKey(reminderKey)) {
                    ArrayList<String> reminderMessages = reminders.get(reminderKey);
                    StringBuilder sb = new StringBuilder();
                    for (String reminder : reminderMessages) {
                        sb.append(reminder).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, sb.toString(), "Reminders",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No reminders for this day.",
                            "Reminders", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void applyForLeave(JFrame frame) {
        JTextField monthField = new JTextField(5);
        JTextField yearField = new JTextField(5);
        JTextField dayField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Apply for Leave",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int month = Integer.parseInt(monthField.getText());
                int year = Integer.parseInt(yearField.getText());
                int day = Integer.parseInt(dayField.getText());

                if (month < 1 || month > 12 || year < 0 || day < 1 || day > 31) {
                    JOptionPane.showMessageDialog(frame, "Invalid date!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String leaveKey = day + "/" + (month - 1) + "/" + year;
                if (!leaveApplications.containsKey(leaveKey)) {
                    leaveApplications.put(leaveKey, new ArrayList<>());
                }
                leaveApplications.get(leaveKey).add(currentEmployee);

                JOptionPane.showMessageDialog(frame, "Leave applied for " + currentEmployee + " on " + day + "/" + month + "/" + year, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void sendEmail(String email, String subject, String message) {
        // Placeholder method for sending email
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
}
