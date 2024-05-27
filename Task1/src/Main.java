import java.util.*;

class Student {
    private int id;
    private String name;
    private int age;
    private String address;
    private String className;
    private String contactDetails;

    public Student(int id, String name, int age, String address, String className, String contactDetails) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.className = className;
        this.contactDetails = contactDetails;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }
}

class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
    }

    // Create operation
    public void addStudent(int id, String name, int age, String address, String className, String contactDetails) {
        Student student = new Student(id, name, age, address, className, contactDetails);
        students.add(student);
        System.out.println("Student added successfully.");
    }

    // Read operation
    public void displayStudents() {
        for (Student student : students) {
            System.out.println("ID: " + student.getId() +
                    ", Name: " + student.getName() +
                    ", Age: " + student.getAge() +
                    ", Address: " + student.getAddress() +
                    ", Class: " + student.getClassName() +
                    ", Contact Details: " + student.getContactDetails());
        }
    }

    // Update operation
    public void updateStudent(int id, String name, int age, String address, String className, String contactDetails) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(name);
                student.setAge(age);
                student.setAddress(address);
                student.setClassName(className);
                student.setContactDetails(contactDetails);
                System.out.println("Student details updated successfully.");
                return;
            }
        }
        System.out.println("Student not found with ID: " + id);
    }

    // Delete operation
    public void deleteStudent(int id) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId() == id) {
                iterator.remove();
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found with ID: " + id);
    }
}

class Main {
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();

        // Create
        system.addStudent(1, "Adi", 20, "Main St", "Class A", "adi@example.com");

        // Read
        system.displayStudents();

        // Update
        system.updateStudent(1, "Raaga", 21, " vst St", "Class B", "Raaga@example.com");
        system.displayStudents();

        // Delete
        system.deleteStudent(1);
        system.displayStudents();
    }
}