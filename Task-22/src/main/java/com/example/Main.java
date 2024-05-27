package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

 class LondonBusinessSchool extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea chatArea;
    private JTextField userInput;
    private Map<String, String> responses;

    public LondonBusinessSchool() {
        setTitle("London Business School");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize chatbot responses
        responses = new HashMap<>();
        responses.put("hello", "Hello! How can I assist you today?");
        responses.put("what courses do you offer", "We offer a variety of courses in business and management, including MBA, EMBA, and MSc programs.");
        responses.put("how can i apply", "You can apply through our online application portal on our website.");
        responses.put("what is the contact number", "You can reach us at +44 20 7000 7000.");
        responses.put("bye", "Goodbye! Have a nice day!");
        responses.put("where is the campus located", "Our campus is located in Regent's Park, London.");
        responses.put("what are the admission requirements", "The admission requirements vary by program, but generally include a completed application form, GMAT/GRE scores, a personal statement, and references.");
        responses.put("do you offer scholarships", "Yes, we offer a range of scholarships based on merit and financial need.");
        responses.put("what is the duration of the mba program", "The MBA program typically lasts 15-21 months, depending on the chosen format.");
        responses.put("what are the tuition fees", "Tuition fees vary by program. For detailed information, please visit our official website or contact the admissions office.");
        responses.put("what career services do you offer", "We offer comprehensive career services including career coaching, networking events, and recruitment opportunities.");
        responses.put("what is the class size", "The class size varies by program. For our full-time MBA, the typical class size is around 400 students.");
        responses.put("what is the faculty to student ratio", "We maintain a low faculty-to-student ratio to ensure personalized attention and support.");

        // Hardcoded paths for images
        String backgroundPath = "C:\\Users\\pc\\OneDrive\\Desktop\\Certifications & Internship\\Internship\\Numetry\\LBS.jpg" ;
        String technologyPath = "C:\\Users\\pc\\OneDrive\\Desktop\\Certifications & Internship\\Internship\\Numetry\\LBS  courses.png";

        // Welcome Page
        JPanel welcomePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ImageIcon bgImage = new ImageIcon(backgroundPath);
                g2d.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        welcomePanel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to London Business School");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(200, 100, 700, 100);
        welcomePanel.add(welcomeLabel);

        JButton homeButton = new JButton("Home");
        homeButton.setBounds(200, 200, 150, 50);
        welcomePanel.add(homeButton);

        JButton techButton = new JButton("Trending courses");
        techButton.setBounds(200, 300, 200, 50);
        welcomePanel.add(techButton);

        JButton contactButton = new JButton("Contact Us");
        contactButton.setBounds(200, 400, 150, 50);
        welcomePanel.add(contactButton);

        // Home Panel
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.add(new JLabel("<html><h1>About London Business School</h1><p>London Business School is a world-renowned institution offering top-tier education in business and management.\n" +
                "London Business School (LBS), one of the world's leading business schools, is renowned for its rigorous academic programs and vibrant, diverse community. Situated in the heart of London, LBS offers an array of programs, including MBA, Executive MBA, Masters in Finance, and various executive education courses, attracting students and professionals from around the globe. The school's faculty comprises distinguished scholars and industry experts, providing cutting-edge research and practical insights. LBS fosters a collaborative environment with a strong emphasis on leadership and innovation, preparing its graduates to excel in the dynamic global business landscape. The school's strategic location in London, a global financial hub, offers unparalleled opportunities for networking and career advancement.</p></html>"), BorderLayout.CENTER);

        JButton backToWelcomeButtonHome = new JButton("Back to Welcome");
        backToWelcomeButtonHome.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        homePanel.add(backToWelcomeButtonHome, BorderLayout.SOUTH);

        // Trending Technologies Panel
        JPanel techPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ImageIcon techImage = new ImageIcon(technologyPath);
                g2d.drawImage(techImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        techPanel.setLayout(new BorderLayout());

        JButton backToWelcomeButtonTech = new JButton("Back to Welcome");
        backToWelcomeButtonTech.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        techPanel.add(backToWelcomeButtonTech, BorderLayout.SOUTH);

        // Contact Us Panel
        JPanel contactPanel = new JPanel(new GridLayout(1, 2));

        JPanel contactInfoPanel = new JPanel();
        contactInfoPanel.add(new JLabel("<html><h1>Contact Us</h1><p>Address:<br>" +
                "London Business School<br>" +
                "Regent's Park<br>" +
                "London<br>" +
                "NW1 4SA<br>" +
                "United Kingdom<br><br>" +
                "Phone: +44 (0)20 7000 7000<br><br>" +
                "Email: info@london.edu<br><br>" +
                "Website: <a href='http://www.london.edu'>www.london.edu</a></p></html>"));


        // Chatbot Panel
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);

        userInput = new JTextField();
        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText().toLowerCase();
                chatArea.append("You: " + input + "\n");
                userInput.setText("");

                String response = responses.getOrDefault(input, "I'm sorry, I don't understand that question.");
                chatArea.append("Bot: " + response + "\n");
            }
        });
        chatPanel.add(userInput, BorderLayout.SOUTH);

        contactPanel.add(contactInfoPanel);
        contactPanel.add(chatPanel);

        JButton backToWelcomeButtonContact = new JButton("Back to Welcome");
        backToWelcomeButtonContact.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        JPanel contactAndBackPanel = new JPanel(new BorderLayout());
        contactAndBackPanel.add(contactPanel, BorderLayout.CENTER);
        contactAndBackPanel.add(backToWelcomeButtonContact, BorderLayout.SOUTH);

        // Add panels to main panel with CardLayout
        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(homePanel, "Home");
        mainPanel.add(techPanel, "Technologies");
        mainPanel.add(contactAndBackPanel, "Contact");

        add(mainPanel);

        // Add action listeners to buttons
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        techButton.addActionListener(e -> cardLayout.show(mainPanel, "Technologies"));
        contactButton.addActionListener(e -> cardLayout.show(mainPanel, "Contact"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LondonBusinessSchool frame = new LondonBusinessSchool();
            frame.setVisible(true);
        });
    }
}
