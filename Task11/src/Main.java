import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NotesManager notesManager = new NotesManager();

        // Setting Password
        System.out.print("Setting Password: ");
        String password = scanner.nextLine();
        notesManager.setPassword(password);

        while (true) {
            System.out.println("========================================================================================================================================================================================================================");
            System.out.println("Notes Manager:");
            System.out.println("1. Adding Note");
            System.out.println("2. Displaying Note");
            System.out.println("3. Updating Note");
            System.out.println("4. Deleting Note");
            System.out.println("5. Reseting your Password");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addNote(scanner, notesManager);
                    break;
                case 2:
                    displayNotes(scanner, notesManager);
                    break;
                case 3:
                    updateNote(scanner, notesManager);
                    break;
                case 4:
                    deleteNote(scanner, notesManager);
                    break;
                case 5:
                    resetPassword(scanner, notesManager);
                    break;
                case 6:
                    System.out.println("Exiting program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNote(Scanner scanner, NotesManager notesManager) {
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();
        if (notesManager.validatePassword(inputPassword)) {
            System.out.print("Enter title for your Note: ");
            String title = scanner.nextLine();
            System.out.print("Enter content for your Note: ");
            String content = scanner.nextLine();
            notesManager.addNote(new Note(title, content));
        } else {
            System.out.println("Incorrect password. Note cannot be added.");
        }
    }

    private static void displayNotes(Scanner scanner, NotesManager notesManager) {
        System.out.print("Enter password: ");
        String displayPassword = scanner.nextLine();
        notesManager.displayNotes(displayPassword);
    }

    private static void updateNote(Scanner scanner, NotesManager notesManager) {
        System.out.print("Enter password: ");
        String updatePassword = scanner.nextLine();
        if (notesManager.validatePassword(updatePassword)) {
            System.out.print("Enter the title of the note to update: ");
            String title = scanner.nextLine();
            System.out.print("Enter new content to add to Note: ");
            String content = scanner.nextLine();
            notesManager.updateNote(title, content);
        } else {
            System.out.println("Incorrect password. Note cannot be updated.");
        }
    }

    private static void deleteNote(Scanner scanner, NotesManager notesManager) {
        System.out.print("Enter password: ");
        String deletePassword = scanner.nextLine();
        if (notesManager.validatePassword(deletePassword)) {
            System.out.print("Enter the title of the note to delete: ");
            String title = scanner.nextLine();
            notesManager.deleteNote(title);
        } else {
            System.out.println("Incorrect password. Note cannot be deleted.");
        }
    }

    private static void resetPassword(Scanner scanner, NotesManager notesManager) {
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        notesManager.resetPassword(currentPassword, newPassword);
    }
}

class Note {
    private String title;
    private String content;

    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class PasswordManager {
    private String password;

    public PasswordManager(String password) {
        this.password = password;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void resetPassword(String newPassword) {
        this.password = newPassword;
    }
}

class NotesManager {
    private PasswordManager passwordManager;
    private List<Note> notes;

    public NotesManager() {
        this.passwordManager = null; // Password will be set later
        this.notes = new ArrayList<>();
    }

    public void setPassword(String password) {
        this.passwordManager = new PasswordManager(password);
    }

    public boolean validatePassword(String inputPassword) {
        return passwordManager.validatePassword(inputPassword);
    }

    public void addNote(Note note) {
        notes.add(note);
        System.out.println("Note added successfully.");
    }

    public void displayNotes(String inputPassword) {
        if (passwordManager.validatePassword(inputPassword)) {
            if (notes.isEmpty()) {
                System.out.println("No notes to display.");
            } else {
                System.out.println("Notes:");
                for (Note note : notes) {
                    System.out.println("Title: " + note.getTitle());
                    System.out.println("Content: " + note.getContent());
                    System.out.println("-------------------------");
                }
            }
        } else {
            System.out.println("Incorrect password. Please try again.");
        }
    }

    public void updateNote(String title, String newContent) {
        for (Note note : notes) {
            if (note.getTitle().equals(title)) {
                note.setContent(newContent);
                System.out.println("Note updated successfully.");
                return;
            }
        }
        System.out.println("Note with title '" + title + "' not found.");
    }

    public void deleteNote(String title) {
        for (Note note : notes) {
            if (note.getTitle().equals(title)) {
                notes.remove(note);
                System.out.println("Note deleted successfully.");
                return;
            }
        }
        System.out.println("Note with title '" + title + "' not found.");
    }

    public void resetPassword(String currentPassword, String newPassword) {
        if (passwordManager.validatePassword(currentPassword)) {
            passwordManager.resetPassword(newPassword);
            System.out.println("Password reset successfully.");
        } else {
            System.out.println("Incorrect current password. Cannot reset password.");
        }
    }
}
