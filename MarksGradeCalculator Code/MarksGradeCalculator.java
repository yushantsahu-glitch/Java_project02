
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class MarksGradeCalculator {
static Scanner sc = new Scanner(System.in);
static int[] marks = new int[5];
static String name = "";
static boolean hasData = false;
static String fileName = "student_data.txt";

    public static void main(String[] args) {
    loadFromFile();
        int choice = 0;
        while (choice != 6) {

            System.out.println("\n===== MARKS & GRADE CALCULATOR =====");
            System.out.println("1. Enter marks");
            System.out.println("2. Show result");
            System.out.println("3. Highest / Lowest");
            System.out.println("4. Sort marks (high to low)");
            System.out.println("5. Search a mark");
            System.out.println("6. Save and exit");

            System.out.print("Enter your choice: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                sc.next();
                choice = 0;
            }
            if (choice == 1) {
                enterMarks();
            } else if (choice >= 2 && choice <= 5) {

                if (!hasData) {
                    System.out.println("Please enter marks first.");
                } else if (choice == 2) {
                    showResult();
                } else if (choice == 3) {
                    showHighestLowest();
                } else if (choice == 4) {
                    sortMarks();
                } else {
                    searchMark();
                }
            } else if (choice == 6) {
                saveToFile();
                System.out.println("Data saved. Goodbye!");
            } else {
                System.out.println("Invalid choice. Enter 1 to 6.");
            }
        }
        sc.close();
    }
    // Enter student name and marks
    static void enterMarks() {
        sc.nextLine();
        System.out.print("Enter student name: ");
        name = sc.nextLine();

        for (int i = 0; i < 5; i++) {
            marks[i] = readMark(
                "Enter marks for Subject " + (i + 1) + ": "
            );
        }
        hasData = true;
        showResult();
    }
    // Validate marks
    static int readMark(String message) {
        while (true) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                int m = sc.nextInt();
                if (m >= 0 && m <= 100) {
                    return m;
                }
            } else {
                sc.next();
            }
            System.out.println(
                "Invalid marks. Enter a value between 0 and 100."
            );
        }
    }
    // Calculate total marks
    static int calculateTotal() {
        int total = 0;
        for (int i = 0; i < 5; i++) {
            total = total + marks[i];
        }
        return total;
    }

    // Calculate percentage
    static double calculatePercentage(int total) {
        return total * 100.0 / 500;
    }
    // Find grade
    static String findGrade(double percentage) {
        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 75) {
            return "A";
        } else if (percentage >= 60) {
            return "B";
        } else if (percentage >= 40) {
            return "C";
        } else {
            return "F";
        }
    }

    // Display result
    static void showResult() {
        int total = calculateTotal();
        double percentage = calculatePercentage(total);
        String grade = findGrade(percentage);
        String extra = "";
        if (grade.equals("C")) {
            extra = " (Pass)";
        } else if (grade.equals("F")) {
            extra = " (Fail)";
        }
        System.out.println("\n----- STUDENT RESULT -----");
        System.out.println("Name: " + name);
        System.out.println("Total: " + total + "/500");
        System.out.printf("Percentage: %.1f%%\n", percentage);
        System.out.println("Grade: " + grade + extra);
    }
    // Find highest and lowest marks
    static void showHighestLowest() {
        int highest = 0;
        int lowest = 0;
        for (int i = 1; i < 5; i++) {

            if (marks[i] > marks[highest]) {
                highest = i;
            }
            if (marks[i] < marks[lowest]) {
                lowest = i;
            }
        }
        System.out.println(
            "Highest: Subject " + (highest + 1)
            + " (" + marks[highest] + ")"
        );
        System.out.println(
            "Lowest: Subject " + (lowest + 1)
            + " (" + marks[lowest] + ")"
        );
    }

    // Sort marks using bubble sort
    static void sortMarks() {
        int[] sorted = new int[5];
        for (int i = 0; i < 5; i++) {
            sorted[i] = marks[i];
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4 - i; j++) {
                if (sorted[j] < sorted[j + 1]) {
                    int temp = sorted[j];
                    sorted[j] = sorted[j + 1];
                    sorted[j + 1] = temp;
                }
            }
        }
        System.out.print("Sorted marks (high to low): ");
        for (int i = 0; i < 5; i++) {
            System.out.print(sorted[i]);
            if (i < 4) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    // Search marks using linear search
    static void searchMark() {
        int target = readMark("Enter mark to search: ");
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            if (marks[i] == target) {
                System.out.println(
                    "Mark " + target + " found in Subject " + (i + 1)
                );
                found = true;
            }
        }
        if (!found) {
            System.out.println("Mark " + target + " not found.");
        }
    }

    // Save student details in a file
    static void saveToFile() {
        if (!hasData) {
            return;
        }
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(name);
            for (int i = 0; i < 5; i++) {
                out.print(marks[i] + " ");
            }
            out.println();
        } catch (Exception e) {

            System.out.println("Could not save the file.");
        }
    }

    // Load saved student details
    static void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            if (!fileScanner.hasNextLine()) {
                return;
            }
            String savedName = fileScanner.nextLine();
            if (!fileScanner.hasNextLine()) {
                return;
            }
            String[] savedMarks = fileScanner.nextLine().trim().split("\\s+");
            if (savedMarks.length != 5) {
                return;
            }
            int[] tempMarks = new int[5];
            for (int i = 0; i < 5; i++) {
                tempMarks[i] = Integer.parseInt(savedMarks[i]);
                if (tempMarks[i] < 0 || tempMarks[i] > 100) {
                    return;
                }
            }
            name = savedName;
            marks = tempMarks;
            hasData = true;
            System.out.println("Previous data loaded for: " + name);
        } catch (Exception e) {
            System.out.println(
                "Saved file could not be read. Starting fresh."
            );
        }
    }
}
