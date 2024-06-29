import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getAvailableSlots() {
        return capacity - registeredStudents;
    }

    public void registerStudent() {
        if (registeredStudents < capacity) {
            registeredStudents++;
        }
    }

    public void deregisterStudent() {
        if (registeredStudents > 0) {
            registeredStudents--;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n%s\nCapacity: %d\nSchedule: %s\nAvailable Slots: %d\n",
                courseCode, title, description, capacity, schedule, getAvailableSlots());
    }
}

class CourseDatabase {
    private HashMap<String, Course> courses;

    public CourseDatabase() {
        courses = new HashMap<>();
        // Adding courses
        addCourse(new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science", 30, "MWF 9-10AM"));
        addCourse(new Course("MATH101", "Calculus I", "Introduction to calculus", 25, "TTh 10-11:30AM"));
        addCourse(new Course("ENG101", "English Literature", "Study of English literature", 20, "MW 1-2:30PM"));
        addCourse(new Course("PHYS101", "Physics I", "Introduction to physics", 28, "MWF 11-12:30PM"));
        addCourse(new Course("CHEM101", "Chemistry I", "Introduction to chemistry", 22, "TTh 1-2:30PM"));
        addCourse(new Course("HIST101", "World History", "Overview of world history", 30, "MW 9-10:30AM"));
    }

    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public void displayCourses() {
        System.out.println("*****************************************");
        System.out.println("*        Available Courses              *");
        System.out.println("*****************************************");
        for (Course course : courses.values()) {
            System.out.println(course);
        }
        System.out.println("*****************************************");
    }
}

class Student {
    private String studentId;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public boolean dropCourse(Course course) {
        return registeredCourses.remove(course);
    }

    public void displayRegisteredCourses() {
        System.out.println("*****************************************");
        System.out.println("*       Registered Courses              *");
        System.out.println("*****************************************");
        for (Course course : registeredCourses) {
            System.out.println(course);
        }
        System.out.println("*****************************************");
    }
}

class StudentDatabase {
    private HashMap<String, Student> students;

    public StudentDatabase() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }
}

public class CourseManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseDatabase courseDatabase = new CourseDatabase();
        StudentDatabase studentDatabase = new StudentDatabase();

        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    courseDatabase.displayCourses();
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();
                    studentDatabase.addStudent(new Student(studentId, studentName));
                    System.out.println("Student registered successfully.");
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextLine();
                    Student student = studentDatabase.getStudent(studentId);
                    if (student != null) {
                        courseDatabase.displayCourses();
                        System.out.print("Enter course code to register: ");
                        String courseCode = scanner.nextLine();
                        Course course = courseDatabase.getCourse(courseCode);
                        if (course != null && course.getAvailableSlots() > 0) {
                            student.registerCourse(course);
                            course.registerStudent();
                            System.out.println("Course registered successfully.");
                        } else {
                            System.out.println("Course not found or full.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextLine();
                    student = studentDatabase.getStudent(studentId);
                    if (student != null) {
                        student.displayRegisteredCourses();
                        System.out.print("Enter course code to drop: ");
                        String courseCode = scanner.nextLine();
                        Course course = courseDatabase.getCourse(courseCode);
                        if (course != null && student.dropCourse(course)) {
                            course.deregisterStudent();
                            System.out.println("Course dropped successfully.");
                        } else {
                            System.out.println("Course not found in registered courses.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
        System.out.println("Thank you for using the Course Management System. Goodbye!");
    }

    public static void displayMenu() {
        System.out.println("*****************************************");
        System.out.println("*       Course Management System        *");
        System.out.println("*****************************************");
        System.out.println("*   1. List Available Courses           *");
        System.out.println("*   2. Register Student                 *");
        System.out.println("*   3. Register for a Course            *");
        System.out.println("*   4. Drop a Course                    *");
        System.out.println("*   5. Exit                             *");
        System.out.println("*****************************************");
    }
}
